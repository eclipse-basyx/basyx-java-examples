package actionlib_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import msg_elements.Time;

public class GoalID extends RosMessage{
	
	private Time stamp;
	private String id;
	
	public GoalID() {
		
	}
	
	public GoalID(Time stamp, String id) {
		setStamp(stamp);
		setId(id);
	}
	public Time getStamp() {
		return stamp;
	}
	public void setStamp(Time stamp) {
		this.stamp = stamp;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
