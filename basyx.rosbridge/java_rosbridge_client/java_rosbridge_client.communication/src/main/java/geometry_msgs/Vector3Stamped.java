package geometry_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class Vector3Stamped extends RosMessage{

	private Header header;
	private Vector3 vector;
	
	public Vector3Stamped() {
		
	}
	public Vector3Stamped(Header header, Vector3 vector) {
		this.setHeader(header);
		this.setVector(vector);
	}
	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}
	public Vector3 getVector() {
		return vector;
	}
	public void setVector(Vector3 vector) {
		this.vector = vector;
	}
}
