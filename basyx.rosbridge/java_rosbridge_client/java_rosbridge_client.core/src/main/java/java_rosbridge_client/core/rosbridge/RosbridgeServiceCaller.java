package java_rosbridge_client.core.rosbridge;

import java.io.IOException;

import java_rosbridge_client.core.utility.RosService;
import java_rosbridge_client.core.utility.RosServiceArgs;
import java_rosbridge_client.core.utility.RosServiceResp;


public class RosbridgeServiceCaller<T extends RosServiceArgs, V extends RosServiceResp> {


	
	private RosService<T,V> srv;
	private RosbridgeMessageHandler handler;
	private String srvTopic;
	private String uniqueId;
	private V lastResponse;


	
	public RosbridgeServiceCaller(RosbridgeMessageHandler handler, String srvTopic, RosService<T,V> srv, String uniqueId) throws IOException {
		this.setHandler(handler);
		this.setUniqueId(uniqueId);
		this.setSrv(srv);
		this.setSrvTopic(srvTopic);
	}
	
	public synchronized void notifyResponse(RosServiceResp response, boolean success) {
		if (success) {
			this.lastResponse = (V) response;
		}
		else {
			this.lastResponse = null;
		}
		notify();
	}

	public synchronized V callSrv(T req) {
		try {
			handler.callService(req, srvTopic, this, uniqueId);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return this.lastResponse;
	}
	
	public RosService<T,V> getSrv() {
		return srv;
	}

	public void setSrv(RosService<T,V> srv) {
		this.srv = srv;
	}

	public String getSrvTopic() {
		return srvTopic;
	}

	public void setSrvTopic(String srvTopic) {
		this.srvTopic = srvTopic;
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





}
