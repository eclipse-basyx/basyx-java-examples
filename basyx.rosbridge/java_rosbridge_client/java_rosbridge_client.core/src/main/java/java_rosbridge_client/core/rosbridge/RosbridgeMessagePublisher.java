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
