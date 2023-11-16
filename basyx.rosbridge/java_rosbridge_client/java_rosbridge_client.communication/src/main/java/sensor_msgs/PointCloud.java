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
