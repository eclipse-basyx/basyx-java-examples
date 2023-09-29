package basyx_ros_bridge.components;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;

import java_rosbridge_client.core.rosbridge.RosbridgeMessageHandler;
import java_rosbridge_client.core.rosbridge.RosbridgeServiceCaller;
import java_rosbridge_client.core.utility.RosService;
import java_rosbridge_client.core.utility.RosServiceArgs;
import java_rosbridge_client.core.utility.RosServiceResp;

public class ServiceCallerSMC<T extends RosService, U extends RosServiceArgs, V extends RosServiceResp> extends SubmodelElementCollection {
	
	private RosbridgeMessageHandler handler;
	private String topic;
	private String uniqueId;
	private RosbridgeServiceCaller<U,V> caller;
	
	public ServiceCallerSMC(Class<T> srvClass, String topic, String id, RosbridgeMessageHandler handler, String uniqueId) throws IOException {
		super();
		this.setIdShort(id);
		this.setHandler(handler);
		this.setIdShort(id);
		this.setUniqueId(uniqueId);
		this.setTopic(topic);
		
		//Each service has an empty constructor
		try {
			RosService<U,V> srv;
			srv = srvClass.getConstructor().newInstance();
			RosToSubmodelConverter.srvCallerToSmc(this, srvClass, srv.getArgClass(), srv.getRespClass());
			this.caller = new RosbridgeServiceCaller<U,V>(handler, topic, srv, uniqueId);
			
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		
	}

	public V callSrv(U rosSrvArgs) {
		return this.caller.callSrv(rosSrvArgs);
		
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
