package sensor_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class RelativeHumidity extends RosMessage{
	
	private Header header;
	private Double relative_humidity;
	private Double variance;
	
	public RelativeHumidity() {
		
	}

	public RelativeHumidity(Header header, Double relative_humidity, Double variance) {
		this.header = header;
		this.relative_humidity = relative_humidity;
		this.variance = variance;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Double getRelative_humidity() {
		return relative_humidity;
	}

	public void setRelative_humidity(Double relative_humidity) {
		this.relative_humidity = relative_humidity;
	}

	public Double getVariance() {
		return variance;
	}

	public void setVariance(Double variance) {
		this.variance = variance;
	}
	
	

}
