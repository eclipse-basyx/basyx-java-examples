package nav_msgs;

import java_rosbridge_client.core.utility.RosService;

public class LoadMap extends RosService<LoadMapArgs, LoadMapResp>{
	
	public LoadMap() {
		this.setArgClass(LoadMapArgs.class);
		this.setRespClass(LoadMapResp.class);
	}

}
