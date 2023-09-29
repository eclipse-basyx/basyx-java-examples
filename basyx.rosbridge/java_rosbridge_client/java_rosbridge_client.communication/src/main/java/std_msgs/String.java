package std_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class String extends RosMessage{
	private java.lang.String data;

	public java.lang.String getData() {
		return data;
	}

	public void setData(java.lang.String data) {
		this.data = data;
	}
}
