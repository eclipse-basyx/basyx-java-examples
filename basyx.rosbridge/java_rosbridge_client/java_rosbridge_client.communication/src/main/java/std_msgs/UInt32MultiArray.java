package std_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class UInt32MultiArray extends RosMessage{
	
	private MultiArrayLayout layout;
	private Long[] data;
	
	public UInt32MultiArray() {
		
	}
	
	public UInt32MultiArray(MultiArrayLayout layout, Long[] data) {
		this.setLayout(layout);
		this.setData(data);
	}

	public MultiArrayLayout getLayout() {
		return layout;
	}

	public void setLayout(MultiArrayLayout layout) {
		this.layout = layout;
	}

	public Long[] getData() {
		return data;
	}

	public void setData(Long[] data) {
		this.data = data;
	}

}
