package sensor_msgs;

import java_rosbridge_client.core.utility.RosServiceResp;

public class SetCameraInfoResp extends RosServiceResp{
	
	private Boolean success;
	private String status_message;
	
	public SetCameraInfoResp() {
		
	}

	public SetCameraInfoResp(Boolean success, String status_message) {
		this.success = success;
		this.status_message = status_message;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getStatus_message() {
		return status_message;
	}

	public void setStatus_message(String status_message) {
		this.status_message = status_message;
	}
	
	

}
