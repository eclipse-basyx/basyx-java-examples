package std_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class Time extends RosMessage{
	private msg_elements.Time data;
	
	public Time() {
		
	}
	
    public Time(msg_elements.Time data) {
		this.setData(data);
	}

	public msg_elements.Time getData() {
		return data;
	}

	public void setData(msg_elements.Time data) {
		this.data = data;
	}
}
