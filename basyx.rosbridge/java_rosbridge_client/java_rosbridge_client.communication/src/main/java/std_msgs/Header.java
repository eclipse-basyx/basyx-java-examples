package std_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import msg_elements.Time;

public class Header extends RosMessage {
	private Long seq;
	private msg_elements.Time stamp;
	private java.lang.String frame_id;
	
	public Header() {
		
	}
	
	public Header(Long seq, Time stamp, java.lang.String frame_id) {
		this.setSeq(seq);
		this.setStamp(stamp);
		this.setFrame_id(frame_id);
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public java.lang.String getFrame_id() {
		return frame_id;
	}

	public void setFrame_id(java.lang.String frame_id) {
		this.frame_id = frame_id;
	}

	public Time getStamp() {
		return stamp;
	}

	public void setStamp(Time stamp) {
		this.stamp = stamp;
	}
	
}
