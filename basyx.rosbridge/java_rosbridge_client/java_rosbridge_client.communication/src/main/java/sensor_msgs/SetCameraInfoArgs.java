package sensor_msgs;

import java_rosbridge_client.core.utility.RosServiceArgs;

public class SetCameraInfoArgs extends RosServiceArgs{
	
	private CameraInfo camera_info;
	
	public SetCameraInfoArgs() {
		
	}

	public SetCameraInfoArgs(CameraInfo camera_info) {
		this.camera_info = camera_info;
	}

	public CameraInfo getCamera_info() {
		return camera_info;
	}

	public void setCamera_info(CameraInfo camera_info) {
		this.camera_info = camera_info;
	}
	
	

}
