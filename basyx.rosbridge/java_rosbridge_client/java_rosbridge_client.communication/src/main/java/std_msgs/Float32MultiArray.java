package std_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class Float32MultiArray extends RosMessage{
	private MultiArrayLayout layout;
	private Float[] data;
	
	public Float32MultiArray() {
		
	}
	
	public Float32MultiArray(MultiArrayLayout layout, Float[] data) {
		this.setLayout(layout);
		this.setData(data);
	}

	public MultiArrayLayout getLayout() {
		return layout;
	}

	public void setLayout(MultiArrayLayout layout) {
		this.layout = layout;
	}

	public Float[] getData() {
		return data;
	}

	public void setData(Float[] data) {
		this.data = data;
	}

}
