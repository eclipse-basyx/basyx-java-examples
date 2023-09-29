package geometry_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class TwistWithCovarianceStamped extends RosMessage{
	private Header header;
	private TwistWithCovariance twist;
	
	public TwistWithCovarianceStamped() {
		
	}
	
	public TwistWithCovarianceStamped(Header header, TwistWithCovariance twist) {
		this.setHeader(header);
		this.setTwist(twist);
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public TwistWithCovariance getTwist() {
		return twist;
	}

	public void setTwist(TwistWithCovariance twist) {
		this.twist = twist;
	}
}


