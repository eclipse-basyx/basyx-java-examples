package std_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class Duration extends RosMessage{
	private msg_elements.Duration data;
	
	public Duration() {
		
	}
	
	public Duration(msg_elements.Duration data) {
		this.setData(data);
	}

	public msg_elements.Duration getData() {
		return data;
	}

	public void setData(msg_elements.Duration data) {
		this.data = data;
	}
}
