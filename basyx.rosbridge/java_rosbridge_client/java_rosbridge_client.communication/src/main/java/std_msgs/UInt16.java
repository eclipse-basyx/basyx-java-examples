package std_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class UInt16 extends RosMessage{
	private Integer data;
	
	public UInt16() {
		
	}
	
	public UInt16(Integer data) {
		this.setData(data);
	}

	public Integer getData() {
		return data;
	}

	public void setData(Integer data) {
		this.data = data;
	}
	
	
}
