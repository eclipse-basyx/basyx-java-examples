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

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;

import java_rosbridge_client.core.rosbridge.RosbridgeMessageHandler;
import java_rosbridge_client.core.rosbridge.RosbridgeServiceCaller;
import java_rosbridge_client.core.utility.RosService;
import java_rosbridge_client.core.utility.RosServiceArgs;
import java_rosbridge_client.core.utility.RosServiceResp;

public class ServiceCallerSMC<T extends RosService, U extends RosServiceArgs, V extends RosServiceResp> extends SubmodelElementCollection {
	
	private RosbridgeMessageHandler handler;
	private String topic;
	private String uniqueId;
	private RosbridgeServiceCaller<U,V> caller;
	
	public ServiceCallerSMC(Class<T> srvClass, String topic, String id, RosbridgeMessageHandler handler, String uniqueId) throws IOException {
		super();
		this.setIdShort(id);
		this.setHandler(handler);
		this.setIdShort(id);
		this.setUniqueId(uniqueId);
		this.setTopic(topic);
		
		//Each service has an empty constructor
		try {
			RosService<U,V> srv;
			srv = srvClass.getConstructor().newInstance();
			RosToSubmodelConverter.srvCallerToSmc(this, srvClass, srv.getArgClass(), srv.getRespClass());
			this.caller = new RosbridgeServiceCaller<U,V>(handler, topic, srv, uniqueId);
			
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		
	}

	public V callSrv(U rosSrvArgs) {
		return this.caller.callSrv(rosSrvArgs);
		
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
