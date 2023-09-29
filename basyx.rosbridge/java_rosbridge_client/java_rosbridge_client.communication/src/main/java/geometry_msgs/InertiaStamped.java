package geometry_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class InertiaStamped extends RosMessage {
	private Header header;
	private Inertia inertia;
	
	public InertiaStamped() {
		
	}
	
	public InertiaStamped(Header header, Inertia inertia) {
		this.setHeader(header);
		this.setInertia(inertia);
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Inertia getInertia() {
		return inertia;
	}

	public void setInertia(Inertia inertia) {
		this.inertia = inertia;
	}
	

}
