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
