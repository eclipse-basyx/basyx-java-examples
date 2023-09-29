package std_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class Int32 extends RosMessage {
	
	private Integer data;
	
	public Int32() {
		
	}
	
	public Int32(Integer data) {
		this.setData(data);
	}

	public Integer getData() {
		return data;
	}

	public void setData(Integer data) {
		this.data = data;
	}

}
