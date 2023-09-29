package geometry_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class Pose extends RosMessage{
	
	private Point position;
	private Quaternion orientation;
	
	public Pose() {
		
	}
	
	public Pose (Point position, Quaternion orientation) {
		this.setPosition(position);
		this.setOrientation(orientation);
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public Quaternion getOrientation() {
		return orientation;
	}

	public void setOrientation(Quaternion orientation) {
		this.orientation = orientation;
	}

}
