package std_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class Char extends RosMessage{
	private Short data;
	
	public Char() {
		
	}
	
	public Char(Short data) {
		this.setData(data);
	}

	public Short getData() {
		return data;
	}

	public void setData(Short data) {
		this.data = data;
	}
}
