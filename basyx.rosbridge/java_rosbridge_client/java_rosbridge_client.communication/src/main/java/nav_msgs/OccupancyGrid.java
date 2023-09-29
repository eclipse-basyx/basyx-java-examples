package nav_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class OccupancyGrid extends RosMessage {
	private Header header;
	private MapMetaData info;
	private Integer[] data;
	
	public OccupancyGrid() {
		
	}
	
	public OccupancyGrid(Header header, MapMetaData info, Integer[] data) {
		this.setHeader(header);
		this.setInfo(info);
		this.setData(data);
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public MapMetaData getInfo() {
		return info;
	}

	public void setInfo(MapMetaData info) {
		this.info = info;
	}

	public Integer[] getData() {
		return data;
	}

	public void setData(Integer[] data) {
		this.data = data;
	}
}
