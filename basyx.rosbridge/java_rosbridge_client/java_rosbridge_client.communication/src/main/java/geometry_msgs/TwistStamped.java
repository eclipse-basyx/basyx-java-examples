package geometry_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class TwistStamped extends RosMessage{
	
	private Header header;
	private Twist twist;
	
	public TwistStamped() {
		
	}
	public TwistStamped(Header header, Twist twist) {
		this.setHeader(header);
		this.setTwist(twist);
	}
	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}
	public Twist getTwist() {
		return twist;
	}
	public void setTwist(Twist twist) {
		this.twist = twist;
	}

}
