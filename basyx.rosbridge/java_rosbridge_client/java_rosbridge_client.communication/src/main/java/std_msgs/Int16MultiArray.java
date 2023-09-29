package std_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class Int16MultiArray extends RosMessage{
	
	private MultiArrayLayout layout;
	private Integer[] data;
	
	public Int16MultiArray() {
		
	}
	
    public Int16MultiArray(MultiArrayLayout layout, Integer[] data) {
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
