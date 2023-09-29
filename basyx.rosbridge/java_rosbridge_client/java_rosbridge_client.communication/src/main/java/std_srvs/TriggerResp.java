package std_srvs;

import java_rosbridge_client.core.utility.RosServiceResp;

public class TriggerResp extends RosServiceResp{

	private Boolean success;
	private String message;
	
	public TriggerResp() {
		
	}
	
	public TriggerResp(Boolean success, String message) {
		this.setSuccess(success);
		this.setMessage(message);
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
}
