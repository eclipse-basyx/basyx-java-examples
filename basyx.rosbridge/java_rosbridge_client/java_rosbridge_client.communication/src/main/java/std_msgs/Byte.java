package std_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class Byte extends RosMessage{
	private java.lang.Byte data;
	
	public Byte() {
		
	}
	
	public Byte(java.lang.Byte data) {
		this.setData(data);
	}

	public java.lang.Byte getData() {
		return data;
	}

	public void setData(java.lang.Byte data) {
		this.data = data;
	}
}
