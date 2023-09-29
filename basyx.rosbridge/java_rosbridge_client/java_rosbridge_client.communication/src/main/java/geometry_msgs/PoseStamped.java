package geometry_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class PoseStamped extends RosMessage{
    private Header header;
    private Pose pose;
    
    public PoseStamped() {
    	
    }
    
    public PoseStamped(Header header, Pose pose) {
    	this.setHeader(header);
    	this.setPose(pose);
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
}
