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

package basyx_ros_bridge.components;
import java.io.IOException;

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;

//From JavaRosbridgeClient
import java_rosbridge_client.core.rosbridge.RosbridgeMessageHandler;
import java_rosbridge_client.core.rosbridge.SimpleRosbridgeMessageListener;
import java_rosbridge_client.core.utility.RosMessage;


public class MessageSubscriptionSMC<T extends RosMessage> extends SubmodelElementCollection{
	private T msg;
	private RosbridgeMessageHandler handler;
	private SimpleRosbridgeMessageListener<T> listener;
	
	public MessageSubscriptionSMC(Class<T> msgClass, String topic, String id, RosbridgeMessageHandler handler, String uniqueId) throws SecurityException, IOException {
		super();
		this.setIdShort(id);
		this.setHandler(handler);
		RosToSubmodelConverter.msgToSmc(this, msgClass);
		try {
			this.setListener(new SimpleRosbridgeMessageListener<T>(handler, topic, msgClass, uniqueId, this.getClass().getMethod("setMsg", RosMessage.class), (Object) this));
		} catch (NoSuchMethodException e) {
			//Can not occure by design of this class
			System.out.println("Class design incorrect, setMsg-Method not found");
		}
		try {
			this.getClass().getMethod("setHandler", RosbridgeMessageHandler.class);
		} catch (Exception e) {
			System.out.println("Method broke");
		}
	}



	public T getMsg() {
		return msg;
	}

	public void setMsg(T msg) {
		this.msg = msg;
		try {
			RosToSubmodelConverter.updateSmc(this, this.msg);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public RosbridgeMessageHandler getHandler() {
		return handler;
	}

	public void setHandler(RosbridgeMessageHandler handler) {
		this.handler = handler;
	}

	public SimpleRosbridgeMessageListener<T> getListener() {
		return listener;
	}

	public void setListener(SimpleRosbridgeMessageListener<T> listener) {
		this.listener = listener;
	}

}
