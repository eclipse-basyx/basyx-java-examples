package geometry_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class PoseWithCovariance extends RosMessage{
	
	private Pose pose;
	private Double[] covariance;
	
	public PoseWithCovariance() {
		
	}
	
	public PoseWithCovariance(Pose pose, Double[] covariance) {
		this.setPose(pose);
		this.setCovariance(covariance);
	}

	public Pose getPose() {
		return pose;
	}

	public void setPose(Pose pose) {
		this.pose = pose;
	}

	public Double[] getCovariance() {
		return covariance;
	}

	public void setCovariance(Double[] covariance) {
		this.covariance = covariance;
	}
}
