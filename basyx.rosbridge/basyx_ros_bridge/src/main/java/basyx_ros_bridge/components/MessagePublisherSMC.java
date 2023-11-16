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
import java_rosbridge_client.core.utility.RosMessage;





public class MessagePublisherSMC<T extends RosMessage> extends SubmodelElementCollection{
	
	
	private RosbridgeMessageHandler handler;
	private String topic;
	private String uniqueId;
	
	public MessagePublisherSMC(Class<T> msgClass, String topic, String id, RosbridgeMessageHandler handler, String uniqueId) throws IOException {
		super();
		this.setIdShort(id);
		this.setHandler(handler);
		this.setIdShort(id);
		this.setUniqueId(uniqueId);
		this.setTopic(topic);
		RosToSubmodelConverter.msgPublisherToSmc(this, msgClass);
		handler.advertise(msgClass, topic, uniqueId);
	}

	public void publish(T elem) throws IOException {
		handler.publish(elem, this.getTopic(), this.getUniqueId());
	}


	public RosbridgeMessageHandler getHandler() {
		return handler;
	}

	public void setHandler(RosbridgeMessageHandler handler) {
		this.handler = handler;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
}
