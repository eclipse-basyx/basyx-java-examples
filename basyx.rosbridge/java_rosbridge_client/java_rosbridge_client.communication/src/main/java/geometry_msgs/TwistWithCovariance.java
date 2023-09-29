package geometry_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class TwistWithCovariance extends RosMessage{
	
	private Twist twist;
	private Double[] covariance;
	
	public TwistWithCovariance() {
		
	}
	
	public TwistWithCovariance(Twist twist, Double[] covariance) {
		this.setTwist(twist);
		this.setCovariance(covariance);
	}

	public Twist getTwist() {
		return twist;
	}

	public void setTwist(Twist twist) {
		this.twist = twist;
	}

	public Double[] getCovariance() {
		return covariance;
	}

	public void setCovariance(Double[] covariance) {
		this.covariance = covariance;
	}
	
	

}
