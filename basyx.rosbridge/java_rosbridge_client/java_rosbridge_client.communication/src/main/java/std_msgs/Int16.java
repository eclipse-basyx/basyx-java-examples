package std_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class Int16 extends RosMessage {
	private Short data;
	
	public Int16() {
		
	}
	
	public Int16(Short data) {
		this.setData(data);	}

	public Short getData() {
		return data;
	}

	public void setData(Short data) {
		this.data = data;
	}
}
