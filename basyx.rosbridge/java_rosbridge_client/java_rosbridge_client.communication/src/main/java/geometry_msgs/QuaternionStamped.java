package geometry_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class QuaternionStamped extends RosMessage{
	
	private Header header;
	private Quaternion quaternion;
	
	public QuaternionStamped () {
		
	}
	public QuaternionStamped(Header header, Quaternion quaternion) {
		this.setHeader(header);
		this.setQuaternion(quaternion);
	}
	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}
	public Quaternion getQuaternion() {
		return quaternion;
	}
	public void setQuaternion(Quaternion quaternion) {
		this.quaternion = quaternion;
	}
	

}
