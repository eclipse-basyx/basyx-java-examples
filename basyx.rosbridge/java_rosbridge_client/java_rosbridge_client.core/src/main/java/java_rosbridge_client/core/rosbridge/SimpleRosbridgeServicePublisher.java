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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java_rosbridge_client.core.utility.RosService;
import java_rosbridge_client.core.utility.RosServiceArgs;
import java_rosbridge_client.core.utility.RosServiceCallResponseAndResult;
import java_rosbridge_client.core.utility.RosServiceResp;

public class SimpleRosbridgeServicePublisher<T extends RosServiceArgs, V extends RosServiceResp> extends RosbridgeServicePublisher<T,V> {
	
	private Object processObj;
	private Method processFunc;
	
	public SimpleRosbridgeServicePublisher(RosbridgeMessageHandler handler, String srvTopic, RosService<T,V> srv, String uniqueId, Method processFunction, Object processObject) throws IOException {
		super(handler, srvTopic, srv, uniqueId);
		this.processObj = processObject;
		this.processFunc = processFunction;
	}

	@Override
	public RosServiceCallResponseAndResult<V> callService(RosServiceArgs resp) {
		try {
			V srvResponse = (V) this.processFunc.invoke(processObj, resp);
			
			if (srvResponse == null) {
				RosServiceCallResponseAndResult<V> result = new RosServiceCallResponseAndResult<V>(null, false);
				return result;
			}
			else {
				RosServiceCallResponseAndResult<V> result = new RosServiceCallResponseAndResult<V>(srvResponse, true);
				return result;
			}
			
	
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

}
