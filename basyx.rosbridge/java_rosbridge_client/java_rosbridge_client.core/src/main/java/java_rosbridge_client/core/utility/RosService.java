package java_rosbridge_client.core.utility;

public class RosService<T extends RosServiceArgs, V extends RosServiceResp> {
	
	private Class<T> argClass;
	private Class<V> respClass;
	
	
	public Class<T> getArgClass() {
		return argClass;
	}
	public void setArgClass(Class<T> argClass) {
		this.argClass = argClass;
	}
	public Class<V> getRespClass() {
		return respClass;
	}
	public void setRespClass(Class<V> respClass) {
		this.respClass = respClass;
	}

}
