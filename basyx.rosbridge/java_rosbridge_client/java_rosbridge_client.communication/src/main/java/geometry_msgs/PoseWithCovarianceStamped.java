package geometry_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class PoseWithCovarianceStamped extends RosMessage{
	private Header header;
	private PoseWithCovariance pose;
	
	public PoseWithCovarianceStamped() {
		
	}
	
	public PoseWithCovarianceStamped(Header header, PoseWithCovariance pose) {
		this.setHeader(header);
		this.setPose(pose);
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public PoseWithCovariance getPose() {
		return pose;
	}

	public void setPose(PoseWithCovariance pose) {
		this.pose = pose;
	}

}
