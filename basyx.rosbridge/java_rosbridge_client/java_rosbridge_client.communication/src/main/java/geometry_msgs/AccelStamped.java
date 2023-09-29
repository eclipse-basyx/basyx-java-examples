package geometry_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class AccelStamped extends RosMessage {
	private Header header;
	private Accel accel;
	
	public AccelStamped() {
		
	}
	
	public AccelStamped(Header header, Accel accel) {
		this.setHeader(header);
		this.setAccel(accel);
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Accel getAccel() {
		return accel;
	}

	public void setAccel(Accel accel) {
		this.accel = accel;
	}
}
