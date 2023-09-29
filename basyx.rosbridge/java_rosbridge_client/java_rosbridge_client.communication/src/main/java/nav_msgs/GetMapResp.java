package nav_msgs;

import java_rosbridge_client.core.utility.RosServiceResp;

public class GetMapResp extends RosServiceResp{
	private OccupancyGrid map;
	
	public GetMapResp() {
		
	}
	
	public GetMapResp(OccupancyGrid map) {
		this.setMap(map);
	}

	public OccupancyGrid getMap() {
		return map;
	}

	public void setMap(OccupancyGrid map) {
		this.map = map;
	}
}
