package trajectory_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class MultiDOFJointTrajectory extends RosMessage{
	
	private Header header;
	private String[] joint_names;
	private MultiDOFJointTrajectoryPoint[] points;
	
	public MultiDOFJointTrajectory() {
		
	}
	
	public MultiDOFJointTrajectory(Header header, String[] joint_names, MultiDOFJointTrajectoryPoint[] points) {
		this.setHeader(header);
		this.setJoint_names(joint_names);
		this.setPoints(points);
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

	public MultiDOFJointTrajectoryPoint[] getPoints() {
		return points;
	}

	public void setPoints(MultiDOFJointTrajectoryPoint[] points) {
		this.points = points;
	}

}