package std_srvs;

import java_rosbridge_client.core.utility.RosService;

public class Empty extends RosService<EmptyArgs, EmptyResp>{

	public Empty() {
		this.setArgClass(EmptyArgs.class);
		this.setRespClass(EmptyResp.class);
	}

}
