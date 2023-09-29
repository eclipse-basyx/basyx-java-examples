package geometry_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class Accel extends RosMessage{
	
	private Vector3 linear;
	private Vector3 angular;
	
	public Accel() {
		
	}
	
    public Accel(Vector3 linear, Vector3 angular) {
		this.setLinear(linear);
		this.setAngular(angular);
	}

	public Vector3 getLinear() {
		return linear;
	}

	public void setLinear(Vector3 linear) {
		this.linear = linear;
	}

	public Vector3 getAngular() {
		return angular;
	}

	public void setAngular(Vector3 angular) {
		this.angular = angular;
	}

}
