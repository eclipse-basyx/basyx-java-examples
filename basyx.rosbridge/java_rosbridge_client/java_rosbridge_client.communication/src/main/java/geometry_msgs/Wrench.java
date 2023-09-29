package geometry_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class Wrench extends RosMessage{

	private Vector3 force;
	private Vector3 torque;
	
	public Wrench() {
		
	}

	public Wrench(Vector3 force, Vector3 torque) {
		this.force = force;
		this.torque = torque;
	}

	public Vector3 getForce() {
		return force;
	}

	public void setForce(Vector3 force) {
		this.force = force;
	}

	public Vector3 getTorque() {
		return torque;
	}

	public void setTorque(Vector3 torque) {
		this.torque = torque;
	}
	
	
}
