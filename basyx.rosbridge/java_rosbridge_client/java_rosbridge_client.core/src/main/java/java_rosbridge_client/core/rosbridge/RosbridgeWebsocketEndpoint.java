package java_rosbridge_client.core.rosbridge;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

public class RosbridgeWebsocketEndpoint extends Endpoint {
	private RosbridgeMessageHandler handler;
	
	public RosbridgeWebsocketEndpoint(RosbridgeMessageHandler handler) {
		this.handler = handler;
	}
	
	@Override
	public void onOpen(Session session, EndpointConfig config) {
		this.handler.setSession(session);
		session.addMessageHandler(handler);
	}

}
