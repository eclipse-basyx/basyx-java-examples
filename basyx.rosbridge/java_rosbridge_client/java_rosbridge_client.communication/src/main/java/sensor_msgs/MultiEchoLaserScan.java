package sensor_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class MultiEchoLaserScan extends RosMessage {
	private Header header;
	
	private Float angle_min;
	private Float angle_max;
	private Float angle_increment;
	private Float time_increment;
	private Float scan_time;
	private Float range_min;
	private Float range_max;
	private LaserEcho[] ranges;
	private LaserEcho[] intensities;
	
	public MultiEchoLaserScan() {
		
	}

	public MultiEchoLaserScan(Header header, Float angle_min, Float angle_max, Float angle_increment,
			Float time_increment, Float scan_time, Float range_min, Float range_max, LaserEcho[] ranges,
			LaserEcho[] intensities) {
		this.header = header;
		this.angle_min = angle_min;
		this.angle_max = angle_max;
		this.angle_increment = angle_increment;
		this.time_increment = time_increment;
		this.scan_time = scan_time;
		this.range_min = range_min;
		this.range_max = range_max;
		this.ranges = ranges;
		this.intensities = intensities;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Float getAngle_min() {
		return angle_min;
	}

	public void setAngle_min(Float angle_min) {
		this.angle_min = angle_min;
	}

	public Float getAngle_max() {
		return angle_max;
	}

	public void setAngle_max(Float angle_max) {
		this.angle_max = angle_max;
	}

	public Float getAngle_increment() {
		return angle_increment;
	}

	public void setAngle_increment(Float angle_increment) {
		this.angle_increment = angle_increment;
	}

	public Float getTime_increment() {
		return time_increment;
	}

	public void setTime_increment(Float time_increment) {
		this.time_increment = time_increment;
	}

	public Float getScan_time() {
		return scan_time;
	}

	public void setScan_time(Float scan_time) {
		this.scan_time = scan_time;
	}

	public Float getRange_min() {
		return range_min;
	}

	public void setRange_min(Float range_min) {
		this.range_min = range_min;
	}

	public Float getRange_max() {
		return range_max;
	}

	public void setRange_max(Float range_max) {
		this.range_max = range_max;
	}

	public LaserEcho[] getRanges() {
		return ranges;
	}

	public void setRanges(LaserEcho[] ranges) {
		this.ranges = ranges;
	}

	public LaserEcho[] getIntensities() {
		return intensities;
	}

	public void setIntensities(LaserEcho[] intensities) {
		this.intensities = intensities;
	}
	
	

}
