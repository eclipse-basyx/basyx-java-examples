package java_rosbridge_client.core.utility;

public class RosServiceCallResponseAndResult<T extends RosServiceResp> {
	private T resp;
	private boolean result;
	
	
	public RosServiceCallResponseAndResult(T resp, boolean result) {
		this.setResp(resp);
		this.setResult(result);
	}
	
	public boolean getResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public T getResp() {
		return resp;
	}
	public void setResp(T resp) {
		this.resp = resp;
	}
}
