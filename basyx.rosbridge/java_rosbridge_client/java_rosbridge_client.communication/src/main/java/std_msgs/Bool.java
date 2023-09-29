package std_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class Bool extends RosMessage{

	private Boolean data;
	
	public Bool() {
		
	}
	
	public Bool(Boolean data) {
		this.setData(data);
	}

	public Boolean getData() {
		return data;
	}

	public void setData(Boolean data) {
		this.data = data;
	}
}
