package geometry_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class Pose2D extends RosMessage{
	private Double x;
	private Double y;
	private Double theta;
	
	public Pose2D() {
		
	}
	
    public Pose2D(Double x, Double y, Double theta) {
    	
		
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public Double getTheta() {
		return theta;
	}

	public void setTheta(Double theta) {
		this.theta = theta;
	}

}
