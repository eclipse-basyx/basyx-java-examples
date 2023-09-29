package geometry_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class WrenchStamped extends RosMessage{
	
	private Header header;
	private Wrench wrench;
	
	public WrenchStamped() {
		
	}
	
	public WrenchStamped(Header header, Wrench wrench) {
		this.setHeader(header);
		this.setWrench(wrench);
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Wrench getWrench() {
		return wrench;
	}

	public void setWrench(Wrench wrench) {
		this.wrench = wrench;
	}

}
