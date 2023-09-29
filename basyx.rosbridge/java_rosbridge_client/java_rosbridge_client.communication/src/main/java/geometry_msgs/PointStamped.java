package geometry_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class PointStamped extends RosMessage{
	private Header header;
	private Point point;
	
	public PointStamped() {
		
	}
	
    public PointStamped(Header header, Point point) {
		this.setHeader(header);
		this.setPoint(point);
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}


}
