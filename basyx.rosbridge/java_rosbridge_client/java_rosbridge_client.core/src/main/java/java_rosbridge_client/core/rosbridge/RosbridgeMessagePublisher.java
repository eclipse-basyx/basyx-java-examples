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

import javax.websocket.Session;

import java_rosbridge_client.core.utility.RosMessage;
import java_rosbridge_client.core.utility.RosServiceArgs;
import java_rosbridge_client.core.utility.RosServiceResp;

public class RosbridgeMessagePublisher<T extends RosMessage> {
	
	private String uniqueId;
	private RosbridgeMessageHandler handler;
	private String topic;
	private Class<T> messageClass;
	
	public RosbridgeMessagePublisher(RosbridgeMessageHandler handler, String topic, Class<T> messageClass, String uniqueId) throws IOException {
		this.setHandler(handler);
		this.setUniqueId(uniqueId);
		this.setMessageClass(messageClass);
		this.setTopic(topic);
		handler.advertise(messageClass, topic, uniqueId);
		
	}

	public void publish(T msg) throws IOException {
		this.handler.publish(msg, this.getTopic(), this.getUniqueId());
	}
	
	public RosbridgeMessageHandler getHandler() {
		return handler;
	}

	public void setHandler(RosbridgeMessageHandler handler) {
		this.handler = handler;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Class<T> getMessageClass() {
		return messageClass;
	}

	public void setMessageClass(Class<T> messageClass) {
		this.messageClass = messageClass;
	}
	


}
