package geometry_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class Quaternion extends RosMessage {

	private Double x;
	private Double y;
	private Double z;
	private Double w;
	
	public Quaternion() {
		
	}

	public Quaternion(Double x, Double y, Double z, Double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
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

	public Double getZ() {
		return z;
	}

	public void setZ(Double z) {
		this.z = z;
	}

	public Double getW() {
		return w;
	}

	public void setW(Double w) {
		this.w = w;
	}
	
	
}
