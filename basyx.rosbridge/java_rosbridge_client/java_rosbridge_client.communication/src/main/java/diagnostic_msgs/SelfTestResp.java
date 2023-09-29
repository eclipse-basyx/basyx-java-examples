package diagnostic_msgs;

import java_rosbridge_client.core.utility.RosServiceResp;

public class SelfTestResp extends RosServiceResp{
	
	private String id;
	private Integer passed;
	private DiagnosticStatus[] status;
	
	public SelfTestResp() {
		
	}

    public SelfTestResp(String id, Integer passed, DiagnosticStatus[] status) {
		this.setId(id);
		this.setPassed(passed);
		this.setStatus(status);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getPassed() {
		return passed;
	}

	public void setPassed(Integer passed) {
		this.passed = passed;
	}

	public DiagnosticStatus[] getStatus() {
		return status;
	}

	public void setStatus(DiagnosticStatus[] status) {
		this.status = status;
	}
}
