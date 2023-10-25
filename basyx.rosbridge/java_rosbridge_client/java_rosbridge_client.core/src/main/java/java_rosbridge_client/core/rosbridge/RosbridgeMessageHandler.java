/*******************************************************************************
 * Copyright (C) 2023 the Eclipse BaSyx Authors
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * SPDX-License-Identifier: MIT
 ******************************************************************************/

package java_rosbridge_client.core.rosbridge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.MessageHandler;
import javax.websocket.Session;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java_rosbridge_client.core.utility.RosMessage;
import java_rosbridge_client.core.utility.RosService;
import java_rosbridge_client.core.utility.RosServiceArgs;
import java_rosbridge_client.core.utility.RosServiceCallResponseAndResult;
import java_rosbridge_client.core.utility.RosServiceResp;

public class RosbridgeMessageHandler implements MessageHandler.Whole<String> {

	private Session session;
	private Gson gson;
	HashMap<String, ArrayList<RosbridgeMessageListener<? extends RosMessage>>> subscribers;
	HashMap<String, Class<? extends RosMessage>> topicMessageClasses;
	
	//First String is topic, Second string is id
	HashMap<String, HashMap<String, RosbridgeServiceCaller<? extends RosServiceArgs, ? extends RosServiceResp>>> waitingForService;
	HashMap<String, RosbridgeServicePublisher> servicePublishers;
	
	private final Object syncSubscribers = new Object();
	private final Object syncWaitingForService = new Object();
	private final Object syncServicePublishers = new Object();
	
	public RosbridgeMessageHandler() {
		this.gson = new Gson();
		this.subscribers = new HashMap<String, ArrayList<RosbridgeMessageListener<? extends RosMessage>>>();
		this.topicMessageClasses = new HashMap<String, Class<? extends RosMessage>>();
		this.waitingForService = new HashMap<String, HashMap<String, RosbridgeServiceCaller<? extends RosServiceArgs, ? extends RosServiceResp>>>();
		this.servicePublishers = new HashMap<String, RosbridgeServicePublisher>();
	}
	
	public void onMessage(String message) {
		JsonObject msg = gson.fromJson(message, JsonObject.class);
		String opCode = msg.get("op").getAsString();
		if (opCode.contentEquals("publish")) {
			this.handlePublishedhMsg(msg);
		}
		else if (opCode.contentEquals("call_service")) {
			try {
				this.handleServiceCall(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if (opCode.contentEquals("service_response")) {
			this.handleServiceResponse(msg);
		}
	}
	
	
	private void handlePublishedhMsg(JsonObject msg) {
		String topicName = msg.get("topic").getAsString();
		
		//Needs to be synchronized to prevent concurrency issues like: 
		//curSubscribers is stored locally --> unsubscribe is called from one of the subscribers and finished successfully --> notify on subscriber is called after unsubscribing
		synchronized(syncSubscribers) {

			ArrayList<RosbridgeMessageListener<? extends RosMessage>> curSubscribers = subscribers.get(topicName);
	
			Class<? extends RosMessage> msgClass = topicMessageClasses.get(topicName);
			
			RosMessage javaMsg = null;
			try {
				javaMsg = gson.fromJson(msg.get("msg"), msgClass);
			} catch (JsonSyntaxException e){
				System.out.println(e.getMessage());
			}
			
			for (RosbridgeMessageListener<? extends RosMessage> curSubscriber: curSubscribers) {
				curSubscriber.notify(javaMsg);
			}
			
		}
		
	}
	

	private void handleServiceResponse(JsonObject msg) {
		String callerId = msg.get("id").getAsString();
		String serviceName = msg.get("service").getAsString();
		
		//Service calls should be implemented as blocking (see RosbridgeServiceCaller), hence concurrency issues should not occure here, 
		//as the service can not unsubsribe while being on the "waiting" list
		HashMap<String, RosbridgeServiceCaller<? extends RosServiceArgs, ? extends RosServiceResp>> serviceWaiters = this.waitingForService.get(serviceName);
		RosbridgeServiceCaller<? extends RosServiceArgs, ? extends RosServiceResp> targetCaller = serviceWaiters.get(callerId);
		
		boolean success = msg.get("result").getAsBoolean();
		
		if (success) {
		
		    //Handling of the optional return values of the service
		    String responseValues = "{}";
		    JsonElement responseObj = msg.get("values").getAsJsonObject();
		    if (responseObj != null) {
			    responseValues = responseObj.getAsJsonObject().toString();
		    }
			
		
		    RosServiceResp resp = gson.fromJson(responseValues, targetCaller.getSrv().getRespClass());
		
		    targetCaller.notifyResponse(resp, success);
		}
		else {
			String errString =  msg.get("values").getAsString();
			System.out.println("ERROR: " + errString);
			System.out.println("ID: " + callerId);
			
			targetCaller.notifyResponse(null, success);
		}
	}
	


	private void handleServiceCall(JsonObject msg) throws IOException {
		String callerId = msg.get("id").getAsString();
		String serviceName = msg.get("service").getAsString();
		
		//Potential issue despite synchronized block: An unsubscribe call is sent to RosBridge, while a 
		//service call is sent in. Hence the Publisher could already be removed from this.servicePublishers
		//when the synchronized block is entered. 
		synchronized(syncServicePublishers) {

			RosbridgeServicePublisher pub = this.servicePublishers.get(serviceName);
			
			JsonObject respMsg = new JsonObject();
			respMsg.addProperty("op", "service_response");
			respMsg.addProperty("id", callerId);
			respMsg.addProperty("service", serviceName);
			
			//Handling the special case described above (unsubscribe call sent out parallel to incoming service call)
			if (pub == null) {
				respMsg.addProperty("result", false);
			}
			else {
				Class<RosServiceArgs> argClass = pub.getSrv().getArgClass();
				
				
				String argsStr = msg.get("args").toString();
				
				RosServiceCallResponseAndResult<RosServiceResp> resp = pub.callService(gson.fromJson(argsStr, argClass));
				
				if (resp.getResult()) {
				    JsonElement jsonElement = gson.toJsonTree(resp.getResp());
				    JsonObject jsonObject = (JsonObject) jsonElement;
					
					
					
					
				    respMsg.add("values", jsonObject);
				    //TODO: When error cases are handled return false here in case of error
				    respMsg.addProperty("result", resp.getResult());
				}
				else {
					Map<String, String> noRes = new HashMap<String, String>();
					respMsg.add("values", (JsonObject) gson.toJsonTree(noRes));
					respMsg.addProperty("result", resp.getResult());
				}
			this.session.getBasicRemote().sendText(gson.toJson(respMsg));
			}
		}
	}

	
	public Session getSession() {
		return this.session;
	}



	synchronized public void setSession(Session session) {
		this.session = session;
	}
	
	
	public <T extends RosMessage> void subscribe(String topic, Class<T> msg_class, String publisher_id, RosbridgeMessageListener<T> listener) throws IOException {
		
		String msgClassString = msg_class.getName().replace(".", "/");
		JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty("op", "subscribe");
		jsonObj.addProperty("id", publisher_id);
		jsonObj.addProperty("topic", topic);
		//jsonObj.addProperty("type", msgClassString);
		
		synchronized(syncSubscribers) {
			if (subscribers.containsKey(topic)) {
				this.subscribers.get(topic).add(listener);
			}
			else {
				this.subscribers.put(topic, new ArrayList<RosbridgeMessageListener<? extends RosMessage>>());
				this.subscribers.get(topic).add(listener);
				topicMessageClasses.put(topic, msg_class);
			}
		}	
		this.session.getBasicRemote().sendText(gson.toJson(jsonObj));
		
	}
	
	public <T extends RosMessage> void unsubscribe(String topic, Class<T> msg_class, String publisher_id, RosbridgeMessageListener<T> listener) throws IOException {
		JsonObject json_obj = new JsonObject();
		json_obj.addProperty("op", "subscribe");
		json_obj.addProperty("id", publisher_id);
		json_obj.addProperty("topic", topic);
		
		synchronized(syncSubscribers) {
			subscribers.get(topic).remove(listener);
			
			
			if (subscribers.get(topic).isEmpty()) {
				subscribers.remove(topic);
				topicMessageClasses.remove(topic);
			}
		}
		this.session.getBasicRemote().sendText(gson.toJson(json_obj));
			
	}
	
	public <T extends RosMessage> void advertise(Class<T> msgClass, String topic, String publisherId) throws IOException {
		String msgClassString = msgClass.getName().replace(".", "/");
		JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty("op", "advertise");
		jsonObj.addProperty("id", publisherId);
		jsonObj.addProperty("topic", topic);
		jsonObj.addProperty("type", msgClassString);
		this.session.getBasicRemote().sendText(gson.toJson(jsonObj));
	}

	public void unadvertise(String topic, String publisherId) throws IOException {
		JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty("op", "unadvertise");
		jsonObj.addProperty("id", publisherId);
		jsonObj.addProperty("topic", topic);
		this.session.getBasicRemote().sendText(gson.toJson(jsonObj));
	}
	
	public <T extends RosMessage> void publish(T msg, String topic, String publisherId) throws IOException {
		JsonElement jsonElement = gson.toJsonTree(msg);
		JsonObject jsonObject = (JsonObject) jsonElement;
		
		JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty("op", "publish");
		jsonObj.addProperty("id", publisherId);
		jsonObj.addProperty("topic", topic);
		jsonObj.add("msg", jsonObject);

		this.session.getBasicRemote().sendText(gson.toJson(jsonObj));
	}
	

	public <T extends RosServiceArgs, V extends RosServiceResp> void callService(T args, String srv, RosbridgeServiceCaller<T,V> caller, String id) throws IOException {
		
		synchronized(syncWaitingForService) {
			if (!(this.waitingForService.containsKey(srv))) {
				HashMap<String, RosbridgeServiceCaller<? extends RosServiceArgs, ? extends RosServiceResp>> newHm = new HashMap<String, RosbridgeServiceCaller<? extends RosServiceArgs, ? extends RosServiceResp>>();
				this.waitingForService.put(srv, newHm);
			}	
			
			//Simply overwrites last entry if one exists. Reusing the same id is not allowed!
			//TODO: Better solution needed! Maybe add way to reject new service call while one is executed
			//Note: Solved in the RosbridgeServiceCaller class using a blocking function call for invoking service
			this.waitingForService.get(srv).put(id, caller);
		}
		
		
		
		
		JsonElement jsonElement = gson.toJsonTree(args);
		JsonObject jsonObject = (JsonObject) jsonElement;
		
		JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty("op", "call_service");
		jsonObj.addProperty("id", id);
		jsonObj.addProperty("service", srv);
		jsonObj.add("args", jsonObject);
		this.session.getBasicRemote().sendText(gson.toJson(jsonObj));
	}
	
	

	public <T extends RosService> void advertiseService(String srv, RosbridgeServicePublisher pub) throws IOException {
		
		String srvClassName = pub.getSrv().getClass().getName();
		
		//Synchronized block necessary?
		synchronized(syncServicePublishers) {
			servicePublishers.put(srv, pub);
		}
		
		String srvClassString = srvClassName.replace(".", "/");
		
		JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty("op", "advertise_service");
		jsonObj.addProperty("type", srvClassString);
		jsonObj.addProperty("service", srv);
		this.session.getBasicRemote().sendText(gson.toJson(jsonObj));
	}
	
	//TODO: Test this
	public void unadvertiseService(String srv, RosbridgeServicePublisher pub) throws IOException {
		
		servicePublishers.remove(srv);
		
		JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty("op", "unadvertise_service");
		jsonObj.addProperty("service", srv);
		this.session.getBasicRemote().sendText(gson.toJson(jsonObj));
	}

	
	

}
