package sensor_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class LaserEcho extends RosMessage{
	
	private Float[] echoes;
	
	public LaserEcho() {
		
	}

	public LaserEcho(Float[] echoes) {
		this.echoes = echoes;
	}

	public Float[] getEchoes() {
		return echoes;
	}

	public void setEchoes(Float[] echoes) {
		this.echoes = echoes;
	}
	
	

}
