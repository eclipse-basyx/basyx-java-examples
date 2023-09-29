package sensor_msgs;

import java_rosbridge_client.core.utility.RosService;

public class SetCameraInfo extends RosService<SetCameraInfoArgs, SetCameraInfoResp> {
	
	public SetCameraInfo() {
		this.setArgClass(SetCameraInfoArgs.class);
		this.setRespClass(SetCameraInfoResp.class);
	}

}
