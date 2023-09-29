package std_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class ByteMultiArray extends RosMessage{
	private MultiArrayLayout layout;
	private java.lang.Byte[] data;
	
	public ByteMultiArray() {
		
	}
	
	public ByteMultiArray(MultiArrayLayout layout, java.lang.Byte[] data) {
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
