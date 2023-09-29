package diagnostic_msgs;

import java_rosbridge_client.core.utility.RosService;

public class AddDiagnostics extends RosService<AddDiagnosticsArgs, AddDiagnosticsResp>{
	
	public AddDiagnostics() {
		this.setArgClass(AddDiagnosticsArgs.class);
		this.setRespClass(AddDiagnosticsResp.class);
	}

}
