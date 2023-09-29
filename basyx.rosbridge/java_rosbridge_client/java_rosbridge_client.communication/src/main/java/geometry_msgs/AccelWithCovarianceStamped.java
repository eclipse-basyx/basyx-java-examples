package geometry_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class AccelWithCovarianceStamped extends RosMessage{
	private Header header;
	private AccelWithCovariance accel;
	
	public AccelWithCovarianceStamped() {
		
	}
	
	public AccelWithCovarianceStamped(Header header, AccelWithCovariance accel) {
		this.setHeader(header);
		this.setAccel(accel);
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public AccelWithCovariance getAccel() {
		return accel;
	}

	public void setAccel(AccelWithCovariance accel) {
		this.accel = accel;
	}
}
