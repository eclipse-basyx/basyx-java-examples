package std_srvs;

import java_rosbridge_client.core.utility.RosService;

public class SetBool extends RosService<SetBoolArgs, SetBoolResp>{

	public SetBool() {
		this.setArgClass(SetBoolArgs.class);
		this.setRespClass(SetBoolResp.class);
	}
	
}
