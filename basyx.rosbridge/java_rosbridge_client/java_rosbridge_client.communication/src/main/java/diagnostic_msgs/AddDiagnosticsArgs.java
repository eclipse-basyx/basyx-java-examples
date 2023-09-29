package diagnostic_msgs;

import java_rosbridge_client.core.utility.RosServiceArgs;

public class AddDiagnosticsArgs extends RosServiceArgs{

	private String load_namespace;
	
	public AddDiagnosticsArgs() {
		
	}
	
    public AddDiagnosticsArgs(String load_namespace) {
		this.setLoad_namespace(load_namespace);
	}

	public String getLoad_namespace() {
		return load_namespace;
	}

	public void setLoad_namespace(String load_namespace) {
		this.load_namespace = load_namespace;
	}
	
}
