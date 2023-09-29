package std_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class MultiArrayDimension extends RosMessage{
	private java.lang.String label;
	private Long size;
	private Long stride;
	
	public MultiArrayDimension() {
		
	}
	
	public MultiArrayDimension(java.lang.String label, Long size, Long stride) {
		this.setLabel(label);
		this.setSize(size);
		this.setStride(stride);
	}

	public java.lang.String getLabel() {
		return label;
	}

	public void setLabel(java.lang.String label) {
		this.label = label;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Long getStride() {
		return stride;
	}

	public void setStride(Long stride) {
		this.stride = stride;
	}

}
