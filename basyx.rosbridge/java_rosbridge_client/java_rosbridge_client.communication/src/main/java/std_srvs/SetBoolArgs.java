package std_srvs;

import java_rosbridge_client.core.utility.RosServiceArgs;



public class SetBoolArgs extends RosServiceArgs{
	
	private Boolean data;
	
	public SetBoolArgs() {
		
	}
	
	public SetBoolArgs(Boolean data) {
		this.data = data;
	}

	public Boolean isData() {
		return data;
	}

	public void setData(Boolean data) {
		this.data = data;
	}

}
