package java_rosbridge_client.core.rosbridge;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.websocket.DeploymentException;

import org.glassfish.tyrus.client.ClientManager;

public class RosbridgeClient {
	private ClientManager websocketClient;
	private RosbridgeMessageHandler msgHandler;
	
	
	public RosbridgeClient() {
		this.websocketClient = ClientManager.createClient();
		this.msgHandler = new RosbridgeMessageHandler();
	}
	
	public void start(String websocketAdress) throws URISyntaxException, DeploymentException, IOException {
		RosbridgeWebsocketEndpoint endpoint = new RosbridgeWebsocketEndpoint(this.msgHandler);
		URI uri = new URI(websocketAdress);
		websocketClient.connectToServer(endpoint, uri);
	}

	public ClientManager getWebsocketClient() {
		return websocketClient;
	}

	public void setWebsocketClient(ClientManager websocketClient) {
		this.websocketClient = websocketClient;
	}

	public RosbridgeMessageHandler getMsgHandler() {
		return msgHandler;
	}

	public void setMsgHandler(RosbridgeMessageHandler msgHandler) {
		this.msgHandler = msgHandler;
	}
	
}
