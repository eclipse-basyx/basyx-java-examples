package std_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class Float32 extends RosMessage{
	
	private Float data;
	
	public Float32() {
		
	}
	
	public Float32(Float data) {
		this.setData(data);
	}

	public Float getData() {
		return data;
	}

	public void setData(Float data) {
		this.data = data;
	}
}
