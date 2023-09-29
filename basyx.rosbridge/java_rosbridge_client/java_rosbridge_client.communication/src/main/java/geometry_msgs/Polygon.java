package geometry_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class Polygon extends RosMessage{
	private Point32[] points;
	
	public Polygon() {
		
	}
	
	public Polygon(Point32[] points) {
		this.setPoints(points);
	}

	public Point32[] getPoints() {
		return points;
	}

	public void setPoints(Point32[] points) {
		this.points = points;
	}
}
