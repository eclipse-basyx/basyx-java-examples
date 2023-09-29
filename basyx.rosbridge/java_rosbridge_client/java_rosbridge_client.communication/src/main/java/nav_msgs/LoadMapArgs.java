package nav_msgs;

import java_rosbridge_client.core.utility.RosServiceArgs;

public class LoadMapArgs extends RosServiceArgs{
	
	private String map_url;
	
	public LoadMapArgs() {
		
	}
	
    public LoadMapArgs(String map_url) {
		this.setMap_url(map_url);
	}

	public String getMap_url() {
		return map_url;
	}

	public void setMap_url(String map_url) {
		this.map_url = map_url;
	}

}
