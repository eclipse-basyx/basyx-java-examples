/*******************************************************************************
 * Copyright (C) 2023 the Eclipse BaSyx Authors
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * SPDX-License-Identifier: MIT
 ******************************************************************************/

package sensor_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class LaserScan extends RosMessage{
	private Header header;
	private Float angle_min;
	private Float angle_max;
	private Float angle_increment;
	private Float time_increment;
	private Float scan_time;
	private Float range_min;
	private Float range_max;
	private Float[] ranges;
	private Float[] intensities;
	
	public LaserScan() {
		
	}

	public LaserScan(Header header, Float angle_min, Float angle_max, Float angle_increment, Float time_increment,
			Float scan_time, Float range_min, Float range_max, Float[] ranges, Float[] intensities) {
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

	public Float[] getRanges() {
		return ranges;
	}

	public void setRanges(Float[] ranges) {
		this.ranges = ranges;
	}

	public Float[] getIntensities() {
		return intensities;
	}

	public void setIntensities(Float[] intensities) {
		this.intensities = intensities;
	}
	
	

}
