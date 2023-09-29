package sensor_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class Illuminance extends RosMessage {

	private Header header;
	private Double illuminance;
	private Double variance;
	
	public Illuminance() {
		
	}

	public Illuminance(Header header, Double illuminance, Double variance) {
		this.header = header;
		this.illuminance = illuminance;
		this.variance = variance;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Double getIlluminance() {
		return illuminance;
	}

	public void setIlluminance(Double illuminance) {
		this.illuminance = illuminance;
	}

	public Double getVariance() {
		return variance;
	}

	public void setVariance(Double variance) {
		this.variance = variance;
	}
	
	
	
}
