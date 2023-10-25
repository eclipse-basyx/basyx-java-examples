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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;

import java_rosbridge_client.core.rosbridge.RosbridgeMessageHandler;
import java_rosbridge_client.core.rosbridge.RosbridgeServicePublisher;
import java_rosbridge_client.core.rosbridge.SimpleRosbridgeServicePublisher;
import java_rosbridge_client.core.utility.RosService;
import java_rosbridge_client.core.utility.RosServiceArgs;
import java_rosbridge_client.core.utility.RosServiceCallResponseAndResult;
import java_rosbridge_client.core.utility.RosServiceResp;

public class ServicePublisherSMC<T extends RosService,U extends RosServiceArgs, V extends RosServiceResp> extends SubmodelElementCollection {
	
	private RosbridgeMessageHandler handler;
	private String topic;
	private String uniqueId;
	private RosbridgeServicePublisher<U, V> pub;
	
	public ServicePublisherSMC(Class<T> srvClass, String topic, String id, RosbridgeMessageHandler handler, String uniqueId, RosbridgeServicePublisher<U, V> pub) {
		super();
		this.setIdShort(id);
		this.setHandler(handler);
		this.setTopic(topic);
		this.setUniqueId(uniqueId);
		this.setPub(pub);
		RosService<U,V> srv;
		try {
			srv = srvClass.getConstructor().newInstance();
			RosToSubmodelConverter.srvPublisherToSmc(this, srvClass, srv.getArgClass(), srv.getRespClass());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public ServicePublisherSMC(Class<T> srvClass, String topic, String id, RosbridgeMessageHandler handler, String uniqueId, Method processFunction, Object processObject) throws IOException {
		super();
		this.setIdShort(id);
		this.setHandler(handler);
		this.setTopic(topic);
		this.setUniqueId(uniqueId);
		
		RosService<U,V> srv;
		try {
			srv = srvClass.getConstructor().newInstance();
			RosToSubmodelConverter.srvPublisherToSmc(this, srvClass, srv.getArgClass(), srv.getRespClass());
			this.setPub(new SimpleRosbridgeServicePublisher<U, V>(handler, topic, srv, uniqueId, processFunction, processObject));
			
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		
	}
	
	public V respondSrv(U rosSrvArgs) {

		RosServiceCallResponseAndResult<V> fullResp = this.pub.callService(rosSrvArgs);
		return fullResp.getResp();
		
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

	public RosbridgeServicePublisher<U, V> getPub() {
		return pub;
	}

	public void setPub(RosbridgeServicePublisher<U, V> pub) {
		this.pub = pub;
	}
	
	
	
}
