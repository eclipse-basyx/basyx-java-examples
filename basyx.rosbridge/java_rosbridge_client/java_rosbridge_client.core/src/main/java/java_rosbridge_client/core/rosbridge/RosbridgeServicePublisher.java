package java_rosbridge_client.core.rosbridge;

import java.io.IOException;

import java_rosbridge_client.core.utility.RosService;
import java_rosbridge_client.core.utility.RosServiceArgs;
import java_rosbridge_client.core.utility.RosServiceCallResponseAndResult;
import java_rosbridge_client.core.utility.RosServiceResp;

public abstract class RosbridgeServicePublisher<T extends RosServiceArgs, V extends RosServiceResp> {
	

	private RosService<T,V> srv;
	private RosbridgeMessageHandler handler;
	private String srvTopic;
	private String uniqueId;
	
	
	public RosbridgeServicePublisher(RosbridgeMessageHandler handler, String srvTopic, RosService<T,V> srv, String uniqueId) throws IOException {
		this.setHandler(handler);
		this.setUniqueId(uniqueId);
		this.setSrv(srv);
		this.setSrvTopic(srvTopic);
		handler.advertiseService(srvTopic, this);
	}
	
	
	public abstract RosServiceCallResponseAndResult<V> callService(T resp);

	public RosService<T,V> getSrv() {
		return srv;
	}

	public void setSrv(RosService<T,V> srv) {
		this.srv = srv;
	}


	public RosbridgeMessageHandler getHandler() {
		return handler;
	}


	public void setHandler(RosbridgeMessageHandler handler) {
		this.handler = handler;
	}


	public String getSrvTopic() {
		return srvTopic;
	}


	public void setSrvTopic(String srvTopic) {
		this.srvTopic = srvTopic;
	}


	public String getUniqueId() {
		return uniqueId;
	}


	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}





}
