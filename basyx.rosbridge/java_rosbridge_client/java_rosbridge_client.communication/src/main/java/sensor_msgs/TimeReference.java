package sensor_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import msg_elements.Time;
import std_msgs.Header;

public class TimeReference extends RosMessage {
	
	private Header header;
	private Time time_ref;
	private String source;
	
	public TimeReference() {
		
	}

	public TimeReference(Header header, Time time_ref, String source) {
		this.header = header;
		this.time_ref = time_ref;
		this.source = source;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Time getTime_ref() {
		return time_ref;
	}

	public void setTime_ref(Time time_ref) {
		this.time_ref = time_ref;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	
	

}
