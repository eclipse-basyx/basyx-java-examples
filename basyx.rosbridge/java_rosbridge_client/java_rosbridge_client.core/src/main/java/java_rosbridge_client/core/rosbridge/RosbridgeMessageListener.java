package java_rosbridge_client.core.rosbridge;

import java.io.IOException;

import java_rosbridge_client.core.utility.RosMessage;

public abstract class RosbridgeMessageListener<T extends RosMessage> {
	
	private RosbridgeMessageHandler handler;
	private String uniqueId;
	private String topic;
	private Class<T> messageClass;
	
	
	public RosbridgeMessageListener(RosbridgeMessageHandler handler, String topic, Class<T> messageClass, String uniqueId) throws IOException {
		this.setHandler(handler);
		this.setUniqueId(uniqueId);
		this.setTopic(topic);
		this.setMessageClass(messageClass);
		this.handler.subscribe(topic, messageClass, uniqueId, this);
	}
	
	public abstract void notify(RosMessage msg);
	
	public void unsubscribe() throws IOException {
		handler.unsubscribe(this.getTopic(), this.getMessageClass(), this.getUniqueId(), this);
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