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

package nav_msgs;

import geometry_msgs.PoseWithCovariance;
import geometry_msgs.TwistWithCovariance;
import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class Odometry extends RosMessage{
	private Header header;
	private String child_frame_id;
	private PoseWithCovariance pose;
	private TwistWithCovariance twist;
	
	public Odometry() {
		
	}
	
	public Odometry(Header header, String child_frame_id, PoseWithCovariance pose, TwistWithCovariance twist) {
		this.setHeader(header);
		this.setChild_frame_id(child_frame_id);
		this.setPose(pose);
		this.setTwist(twist);
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public String getChild_frame_id() {
		return child_frame_id;
	}

	public void setChild_frame_id(String child_frame_id) {
		this.child_frame_id = child_frame_id;
	}

	public PoseWithCovariance getPose() {
		return pose;
	}

	public void setPose(PoseWithCovariance pose) {
		this.pose = pose;
	}

	public TwistWithCovariance getTwist() {
		return twist;
	}

	public void setTwist(TwistWithCovariance twist) {
		this.twist = twist;
	}

}
