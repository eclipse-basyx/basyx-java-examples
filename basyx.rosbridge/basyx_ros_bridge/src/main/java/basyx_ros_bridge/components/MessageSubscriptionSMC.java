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
