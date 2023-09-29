package diagnostic_msgs;

import java_rosbridge_client.core.utility.RosService;
import std_srvs.SetBoolArgs;
import std_srvs.SetBoolResp;

public class SelfTest extends RosService<SelfTestArgs, SelfTestResp>{
	
	public SelfTest() {
		this.setArgClass(SelfTestArgs.class);
		this.setRespClass(SelfTestResp.class);
	}

}
