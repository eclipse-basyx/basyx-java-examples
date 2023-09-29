package sensor_msgs;

import geometry_msgs.Transform;
import geometry_msgs.Twist;
import geometry_msgs.Wrench;
import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class MultiDOFJointState extends RosMessage {
	private Header header;
	
	private String[] joint_names;
	private Transform[] transforms;
	private Twist[] twist;
	private Wrench[] wrench;
	
	public MultiDOFJointState() {
		
	}

	public MultiDOFJointState(Header header, String[] joint_names, Transform[] transforms, Twist[] twist,
			Wrench[] wrench) {
		this.header = header;
		this.joint_names = joint_names;
		this.transforms = transforms;
		this.twist = twist;
		this.wrench = wrench;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public String[] getJoint_names() {
		return joint_names;
	}

	public void setJoint_names(String[] joint_names) {
		this.joint_names = joint_names;
	}

	public Transform[] getTransforms() {
		return transforms;
	}

	public void setTransforms(Transform[] transforms) {
		this.transforms = transforms;
	}

	public Twist[] getTwist() {
		return twist;
	}

	public void setTwist(Twist[] twist) {
		this.twist = twist;
	}

	public Wrench[] getWrench() {
		return wrench;
	}

	public void setWrench(Wrench[] wrench) {
		this.wrench = wrench;
	}
	
	

}
