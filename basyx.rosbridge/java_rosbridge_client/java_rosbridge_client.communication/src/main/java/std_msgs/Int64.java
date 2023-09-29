package std_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class Int64 extends RosMessage{
	
	private Long data;
	
	public Int64() {
		
	}
	
	public Int64(Long data) {
		this.setData(data);
	}

	public Long getData() {
		return data;
	}

	public void setData(Long data) {
		this.data = data;
	}

}
