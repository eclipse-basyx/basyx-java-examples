package std_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class UInt8 extends RosMessage{
	private Short data;
	
	private UInt8() {
		
	}
	
	private UInt8(Short data) {
		this.setData(data);
	}

	public short getData() {
		return data;
	}

	public void setData(Short data) {
		this.data = data;
	}
	
	
	
	
}
