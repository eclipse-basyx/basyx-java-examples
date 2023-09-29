package nav_msgs;

import geometry_msgs.PoseWithCovariance;
import geometry_msgs.TwistWithCovariance;
import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class Odometry extends RosMessage{
	private Header header;
	private String child_frame_id;
	private PoseWithCovariance pose;
	private TwistWithCovariance twist;
	
	public Odometry() {
		
	}
	
	public Odometry(Header header, String child_frame_id, PoseWithCovariance pose, TwistWithCovariance twist) {
		this.setHeader(header);
		this.setChild_frame_id(child_frame_id);
		this.setPose(pose);
		this.setTwist(twist);
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public String getChild_frame_id() {
		return child_frame_id;
	}

	public void setChild_frame_id(String child_frame_id) {
		this.child_frame_id = child_frame_id;
	}

	public PoseWithCovariance getPose() {
		return pose;
	}

	public void setPose(PoseWithCovariance pose) {
		this.pose = pose;
	}

	public TwistWithCovariance getTwist() {
		return twist;
	}

	public void setTwist(TwistWithCovariance twist) {
		this.twist = twist;
	}

}
