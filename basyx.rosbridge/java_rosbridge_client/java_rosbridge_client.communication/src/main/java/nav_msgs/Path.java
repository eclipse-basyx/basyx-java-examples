package nav_msgs;

import geometry_msgs.PoseStamped;
import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class Path extends RosMessage{
	private Header header;
	private PoseStamped[] poses;
	
	public Path() {
		
	}
	
	public Path(Header header, PoseStamped[] poses) {
		this.setHeader(header);
		this.setPoses(poses);
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public PoseStamped[] getPoses() {
		return poses;
	}

	public void setPoses(PoseStamped[] poses) {
		this.poses = poses;
	}
	
}
