package sensor_msgs;

import geometry_msgs.Point32;
import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class PointCloud extends RosMessage{
	private Header header;
	
	private Point32[] points;
	private ChannelFloat32 channels;
	
	public PointCloud() {
		
	}

	public PointCloud(Header header, Point32[] points, ChannelFloat32 channels) {
		this.header = header;
		this.points = points;
		this.channels = channels;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Point32[] getPoints() {
		return points;
	}

	public void setPoints(Point32[] points) {
		this.points = points;
	}

	public ChannelFloat32 getChannels() {
		return channels;
	}

	public void setChannels(ChannelFloat32 channels) {
		this.channels = channels;
	}
	
	

}
