package sensor_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class FluidPressure extends RosMessage {

	private Header header;
	private Double fluid_pressure;
	private Double variance;
	
	public FluidPressure() {
		
	}

	public FluidPressure(Header header, Double fluid_pressure, Double variance) {
		this.header = header;
		this.fluid_pressure = fluid_pressure;
		this.variance = variance;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Double getFluid_pressure() {
		return fluid_pressure;
	}

	public void setFluid_pressure(Double fluid_pressure) {
		this.fluid_pressure = fluid_pressure;
	}

	public Double getVariance() {
		return variance;
	}

	public void setVariance(Double variance) {
		this.variance = variance;
	}
	
	
}
