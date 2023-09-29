package std_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class UInt16MultiArray extends RosMessage {
	
	private MultiArrayLayout layout;
	private Integer[] data;
	
	public UInt16MultiArray() {
		
	}
	
	public UInt16MultiArray(MultiArrayLayout layout, Integer[] data) {
		this.setLayout(layout);
		this.setData(data);
	}

	public MultiArrayLayout getLayout() {
		return layout;
	}

	public void setLayout(MultiArrayLayout layout) {
		this.layout = layout;
	}

	public Integer[] getData() {
		return data;
	}

	public void setData(Integer[] data) {
		this.data = data;
	}
}
