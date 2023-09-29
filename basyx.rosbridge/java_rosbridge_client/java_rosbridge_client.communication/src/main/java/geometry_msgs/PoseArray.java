package geometry_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class PoseArray extends RosMessage{
	private Header header;
	private Pose[] poses;
	
	public PoseArray() {
		
	}
	
    public PoseArray(Header header, Pose[] poses) {
		this.setHeader(header);
		this.setPoses(poses);
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Pose[] getPoses() {
		return poses;
	}

	public void setPoses(Pose[] poses) {
		this.poses = poses;
	}

}
