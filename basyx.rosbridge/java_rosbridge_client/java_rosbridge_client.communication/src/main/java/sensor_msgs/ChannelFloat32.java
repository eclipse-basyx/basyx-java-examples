package sensor_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class ChannelFloat32 extends RosMessage{

	private String name;
	private Float[] values;
	
	public ChannelFloat32() {
		
	}

	public ChannelFloat32(String name, Float[] values) {
		this.name = name;
		this.values = values;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float[] getValues() {
		return values;
	}

	public void setValues(Float[] values) {
		this.values = values;
	}
	
	
}
