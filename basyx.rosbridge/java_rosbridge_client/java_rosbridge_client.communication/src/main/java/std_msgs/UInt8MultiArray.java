package std_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class UInt8MultiArray extends RosMessage{
	
	private MultiArrayLayout layout;
	private Short[] data;
	
	public UInt8MultiArray() {
		
	}
	
	public UInt8MultiArray(MultiArrayLayout layout, Short[] data) {
		this.setLayout(layout);
		this.setData(data);
	}

	public MultiArrayLayout getLayout() {
		return layout;
	}

	public void setLayout(MultiArrayLayout layout) {
		this.layout = layout;
	}

	public Short[] getData() {
		return data;
	}

	public void setData(Short[] data) {
		this.data = data;
	}

}
