package diagnostic_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class DiagnosticArray extends RosMessage{
	private Header header;
	private DiagnosticStatus[] status;
	
	private DiagnosticArray() {
		
	}
	
	private DiagnosticArray(Header header, DiagnosticStatus[] status) {
		this.setHeader(header);
		this.setStatus(status);
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public DiagnosticStatus[] getStatus() {
		return status;
	}

	public void setStatus(DiagnosticStatus[] status) {
		this.status = status;
	}
}
