package geometry_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class PolygonStamped extends RosMessage {
	private Header header;
	private Polygon polygon;
	
	public PolygonStamped() {
		
	}
	
	public PolygonStamped(Header header, Polygon polygon) {
		this.setHeader(header);
		this.setPolygon(polygon);
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Polygon getPolygon() {
		return polygon;
	}

	public void setPolygon(Polygon polygon) {
		this.polygon = polygon;
	}

}
