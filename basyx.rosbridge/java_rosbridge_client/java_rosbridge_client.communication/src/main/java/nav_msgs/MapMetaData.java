package nav_msgs;

import geometry_msgs.Pose;
import java_rosbridge_client.core.utility.RosMessage;
import msg_elements.Time;

public class MapMetaData extends RosMessage{
	private Time map_load_time;
	private Float resolution;
	private Long width;
	private Long height;
	private Pose origin;
	
	public MapMetaData() {
		
	}
	
    public MapMetaData(Time map_load_time, Float resolution, Long width, Long height, Pose origin) {
		this.setMap_load_time(map_load_time);
		this.setResolution(resolution);
		this.setWidth(width);
		this.setHeight(height);
		this.setOrigin(origin);
	}

	public Time getMap_load_time() {
		return map_load_time;
	}

	public void setMap_load_time(Time map_load_time) {
		this.map_load_time = map_load_time;
	}

	public Float getResolution() {
		return resolution;
	}

	public void setResolution(Float resolution) {
		this.resolution = resolution;
	}

	public Long getWidth() {
		return width;
	}

	public void setWidth(Long width) {
		this.width = width;
	}

	public Long getHeight() {
		return height;
	}

	public void setHeight(Long height) {
		this.height = height;
	}

	public Pose getOrigin() {
		return origin;
	}

	public void setOrigin(Pose origin) {
		this.origin = origin;
	}
}
