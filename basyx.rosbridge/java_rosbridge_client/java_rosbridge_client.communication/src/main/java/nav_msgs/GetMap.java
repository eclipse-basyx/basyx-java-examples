package nav_msgs;


import java_rosbridge_client.core.utility.RosService;

public class GetMap extends RosService<GetMapArgs, GetMapResp>{

	public GetMap() {
		this.setArgClass(GetMapArgs.class);
		this.setRespClass(GetMapResp.class);
	}
}
