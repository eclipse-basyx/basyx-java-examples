package sensor_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class Temperature extends RosMessage {
	private Header header;
	private Double temperature;
	private Double variance;
	
	public Temperature() {
		
	}

	public Temperature(Header header, Double temperature, Double variance) {
		this.header = header;
		this.temperature = temperature;
		this.variance = variance;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Double getVariance() {
		return variance;
	}

	public void setVariance(Double variance) {
		this.variance = variance;
	}
	
	
}
