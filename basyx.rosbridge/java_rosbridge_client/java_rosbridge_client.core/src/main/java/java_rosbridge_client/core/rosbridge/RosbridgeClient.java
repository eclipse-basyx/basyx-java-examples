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
