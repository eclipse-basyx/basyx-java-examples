package visualization_msgs;

import geometry_msgs.Pose;
import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class InteractiveMarkerPose extends RosMessage{
	
	private Header header;
	private Pose pose;
	private String name;
	
	public InteractiveMarkerPose() {
		
	}
	
	public InteractiveMarkerPose(Header header, Pose pose, String name) {
		this.setHeader(header);
		this.setPose(pose);
		this.setName(name);
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Pose getPose() {
		return pose;
	}

	public void setPose(Pose pose) {
		this.pose = pose;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	

}
