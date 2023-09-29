package geometry_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class Point32 extends RosMessage {
	private Float x;
	private Float y;
	private Float z;
	
	public Point32() {
		
	}

	public Point32(Float x, Float y, Float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Float getX() {
		return x;
	}

	public void setX(Float x) {
		this.x = x;
	}

	public Float getY() {
		return y;
	}

	public void setY(Float y) {
		this.y = y;
	}

	public Float getZ() {
		return z;
	}

	public void setZ(Float z) {
		this.z = z;
	}
	
	

}
