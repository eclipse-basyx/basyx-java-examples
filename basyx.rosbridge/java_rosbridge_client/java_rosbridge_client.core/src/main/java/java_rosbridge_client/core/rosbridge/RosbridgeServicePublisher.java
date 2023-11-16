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
