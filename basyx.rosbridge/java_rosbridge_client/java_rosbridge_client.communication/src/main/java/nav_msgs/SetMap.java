package nav_msgs;

import java_rosbridge_client.core.utility.RosService;

public class SetMap extends RosService<SetMapArgs,SetMapResp>{
	
	public SetMap() {
		this.setArgClass(SetMapArgs.class);
		this.setRespClass(SetMapResp.class);
	}

}
