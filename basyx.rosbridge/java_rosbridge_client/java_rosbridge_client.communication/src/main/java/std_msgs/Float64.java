package std_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class Float64 extends RosMessage{
	private Double data;
	
	public Float64() {
		
	}
	
    public Float64(Double data) {
		this.setData(data);
	}

	public Double getData() {
		return data;
	}

	public void setData(Double data) {
		this.data = data;
	}

}
