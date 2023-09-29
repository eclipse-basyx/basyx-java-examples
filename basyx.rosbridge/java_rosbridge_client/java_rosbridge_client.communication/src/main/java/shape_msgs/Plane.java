package shape_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class Plane extends RosMessage{
	private Double[] coef;
	
	public Plane() {
		
	}
	
	public Plane(Double[] coef) {
		this.setCoef(coef);
	}

	public Double[] getCoef() {
		return coef;
	}

	public void setCoef(Double[] coef) {
		this.coef = coef;
	}
}
