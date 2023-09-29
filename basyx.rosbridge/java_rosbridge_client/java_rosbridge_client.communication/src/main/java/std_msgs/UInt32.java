package std_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class UInt32 extends RosMessage{
	
	private Long data;
	
	public UInt32() {
		
	}
	
	public UInt32(Long data) {
		this.setData(data);
	}

	public Long getData() {
		return data;
	}

	public void setData(Long data) {
		this.data = data;
	}

}
