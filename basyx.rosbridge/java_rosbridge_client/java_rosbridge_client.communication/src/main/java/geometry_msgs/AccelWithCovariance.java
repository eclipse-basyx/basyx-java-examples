package geometry_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class AccelWithCovariance extends RosMessage{

	private Accel accel;
	private Double[] covariance;
	
	private AccelWithCovariance() {
		
	}
    
	private AccelWithCovariance(Accel accel, Double[] covariance) {
		this.setAccel(accel);
		this.setCovariance(covariance);
	}

	public Accel getAccel() {
		return accel;
	}

	public void setAccel(Accel accel) {
		this.accel = accel;
	}

	public Double[] getCovariance() {
		return covariance;
	}

	public void setCovariance(Double[] covariance) {
		this.covariance = covariance;
	}
}
