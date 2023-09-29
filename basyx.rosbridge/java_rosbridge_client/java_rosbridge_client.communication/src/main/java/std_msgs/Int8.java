package std_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class Int8 extends RosMessage{
	private java.lang.Byte data;
	
	public Int8() {
		
	}
	
    public Int8(java.lang.Byte data) {
		this.setData(data);
	}

	public java.lang.Byte getData() {
		return data;
	}

	public void setData(java.lang.Byte data) {
		this.data = data;
	}
    
    
}
