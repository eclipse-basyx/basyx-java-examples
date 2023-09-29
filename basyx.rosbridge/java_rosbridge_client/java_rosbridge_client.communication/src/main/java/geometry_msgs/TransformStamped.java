package geometry_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class TransformStamped extends RosMessage{

	private Header header;
	private String child_frame_id;
	private Transform transform;

	public TransformStamped() {
		
	}
	
	public TransformStamped( Header header, String child_frame_id, Transform transform) {
		this.setHeader(header);
		this.setChild_frame_id(child_frame_id);
		this.setTransform(transform);
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Transform getTransform() {
		return transform;
	}

	public void setTransform(Transform transform) {
		this.transform = transform;
	}

	public String getChild_frame_id() {
		return child_frame_id;
	}

	public void setChild_frame_id(String child_frame_id) {
		this.child_frame_id = child_frame_id;
	}
	

}

