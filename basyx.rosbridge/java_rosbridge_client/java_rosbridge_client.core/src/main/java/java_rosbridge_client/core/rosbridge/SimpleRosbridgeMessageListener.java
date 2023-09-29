package java_rosbridge_client.core.rosbridge;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java_rosbridge_client.core.utility.RosMessage;

public class SimpleRosbridgeMessageListener<T extends RosMessage> extends RosbridgeMessageListener<T> {

	
	private Object callbackObject;
	private Method callbackFunction;
	
	public SimpleRosbridgeMessageListener(RosbridgeMessageHandler handler, String topic, Class<T> messageClass, String uniqueId, Method callbackFunction, Object callbackObject) throws IOException {
		super(handler, topic, messageClass, uniqueId);
		this.callbackObject = callbackObject;
		this.callbackFunction = callbackFunction;
	}
	
	@Override
	public void notify(RosMessage msg) {
		try {
			this.callbackFunction.invoke(callbackObject, msg);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
