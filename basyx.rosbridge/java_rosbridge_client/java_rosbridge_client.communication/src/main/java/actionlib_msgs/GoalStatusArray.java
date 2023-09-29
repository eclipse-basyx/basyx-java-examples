package actionlib_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class GoalStatusArray extends RosMessage {
	private Header header;
	private GoalStatus[] status_list;
	
	public GoalStatusArray() {
		
	}
	
	public GoalStatusArray(Header header, GoalStatus[] status_list) {
		this.setHeader(header);
		this.setStatus_list(status_list);
	}
	
	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}
	public GoalStatus[] getStatus_list() {
		return status_list;
	}
	public void setStatus_list(GoalStatus[] status_list) {
		this.status_list = status_list;
	}
	
}
