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

import geometry_msgs.Transform;
import geometry_msgs.Twist;
import geometry_msgs.Wrench;
import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class MultiDOFJointState extends RosMessage {
	private Header header;
	
	private String[] joint_names;
	private Transform[] transforms;
	private Twist[] twist;
	private Wrench[] wrench;
	
	public MultiDOFJointState() {
		
	}

	public MultiDOFJointState(Header header, String[] joint_names, Transform[] transforms, Twist[] twist,
			Wrench[] wrench) {
		this.header = header;
		this.joint_names = joint_names;
		this.transforms = transforms;
		this.twist = twist;
		this.wrench = wrench;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public String[] getJoint_names() {
		return joint_names;
	}

	public void setJoint_names(String[] joint_names) {
		this.joint_names = joint_names;
	}

	public Transform[] getTransforms() {
		return transforms;
	}

	public void setTransforms(Transform[] transforms) {
		this.transforms = transforms;
	}

	public Twist[] getTwist() {
		return twist;
	}

	public void setTwist(Twist[] twist) {
		this.twist = twist;
	}

	public Wrench[] getWrench() {
		return wrench;
	}

	public void setWrench(Wrench[] wrench) {
		this.wrench = wrench;
	}
	
	

}
