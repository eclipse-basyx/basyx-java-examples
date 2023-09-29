package sensor_msgs;

import geometry_msgs.Vector3;
import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class MagneticField extends RosMessage {
	
	private Header header;
	private Vector3 magnetic_field;
	private Double[] magnetic_field_covariance;
	
	public MagneticField() {
		
	}

	public MagneticField(Header header, Vector3 magnetic_field, Double[] magnetic_field_covariance) {
		this.header = header;
		this.magnetic_field = magnetic_field;
		this.magnetic_field_covariance = magnetic_field_covariance;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Vector3 getMagnetic_field() {
		return magnetic_field;
	}

	public void setMagnetic_field(Vector3 magnetic_field) {
		this.magnetic_field = magnetic_field;
	}

	public Double[] getMagnetic_field_covariance() {
		return magnetic_field_covariance;
	}

	public void setMagnetic_field_covariance(Double[] magnetic_field_covariance) {
		this.magnetic_field_covariance = magnetic_field_covariance;
	}
	
	

}
