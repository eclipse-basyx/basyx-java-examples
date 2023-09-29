package std_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class MultiArrayLayout extends RosMessage{
	private MultiArrayDimension[] dim;
	private Long data_offset;
	
	public MultiArrayLayout() {
		
	}
	
	public MultiArrayLayout(MultiArrayDimension[] dim, Long data_offset) {
		this.setDim(dim);
		this.setData_offset(data_offset);
	}

	public MultiArrayDimension[] getDim() {
		return dim;
	}

	public void setDim(MultiArrayDimension[] dim) {
		this.dim = dim;
	}

	public Long getData_offset() {
		return data_offset;
	}

	public void setData_offset(Long data_offset) {
		this.data_offset = data_offset;
	}

}
