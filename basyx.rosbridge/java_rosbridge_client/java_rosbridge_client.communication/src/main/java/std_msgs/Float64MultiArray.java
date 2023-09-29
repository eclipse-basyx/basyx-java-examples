package std_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class Float64MultiArray extends RosMessage{
	private MultiArrayLayout layout;
	private Double[] data;
	
	public Float64MultiArray() {
		
	}
	
	public Float64MultiArray(MultiArrayLayout layout, Double[] data) {
		this.setLayout(layout);
		this.setData(data);
	}

	public MultiArrayLayout getLayout() {
		return layout;
	}

	public void setLayout(MultiArrayLayout layout) {
		this.layout = layout;
	}

	public Double[] getData() {
		return data;
	}

	public void setData(Double[] data) {
		this.data = data;
	}

}
