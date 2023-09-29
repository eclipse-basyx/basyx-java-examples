package msg_elements;

import java_rosbridge_client.core.utility.RosMessageElement;

public class Time extends RosMessageElement{
	
	private Long secs;
	private Long nsecs;
	
	public Time() {
		
	}
	
	public Time(Long secs, Long nsecs) {
		this.setSecs(secs);
		this.setNsecs(nsecs);
	}

	public Long getNsecs() {
		return nsecs;
	}

	public void setNsecs(Long nsecs) {
		this.nsecs = nsecs;
	}

	public Long getSecs() {
		return secs;
	}

	public void setSecs(Long secs) {
		this.secs = secs;
	}

}
