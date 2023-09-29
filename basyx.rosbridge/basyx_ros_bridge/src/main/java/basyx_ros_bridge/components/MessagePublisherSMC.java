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
