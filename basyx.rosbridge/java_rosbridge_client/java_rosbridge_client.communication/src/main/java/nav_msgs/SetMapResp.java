package nav_msgs;

import java_rosbridge_client.core.utility.RosServiceResp;

public class SetMapResp extends RosServiceResp{
	
	private Boolean success;
	
	public SetMapResp() {
		
	}
	
	public SetMapResp(Boolean success) {
		this.setSuccess(success);
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}
}
