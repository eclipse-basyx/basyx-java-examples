package diagnostic_msgs;

import java_rosbridge_client.core.utility.RosServiceResp;

public class AddDiagnosticsResp extends RosServiceResp {
	private Boolean success;
	private String message;
	
	public AddDiagnosticsResp() {
		
	}
	
	public AddDiagnosticsResp(Boolean success, String message) {
		this.setSuccess(success);
		this.setMessage(message);
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
