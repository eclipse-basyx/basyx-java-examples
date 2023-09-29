package std_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class Int8MultiArray extends RosMessage{
	
	private MultiArrayLayout layout;
	private java.lang.Byte[] data;
	
	public Int8MultiArray() {
		
	}
	
	public Int8MultiArray(MultiArrayLayout layout, java.lang.Byte[] data) {
		this.setLayout(layout);
		this.setData(data);
	}
	
	public MultiArrayLayout getLayout() {
		return layout;
	}
	public void setLayout(MultiArrayLayout layout) {
		this.layout = layout;
	}
	public java.lang.Byte[] getData() {
		return data;
	}
	public void setData(java.lang.Byte[] data) {
		this.data = data;
	}

}
