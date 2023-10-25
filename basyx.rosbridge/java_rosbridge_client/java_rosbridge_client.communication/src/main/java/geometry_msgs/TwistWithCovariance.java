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

package geometry_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class TwistWithCovariance extends RosMessage{
	
	private Twist twist;
	private Double[] covariance;
	
	public TwistWithCovariance() {
		
	}
	
	public TwistWithCovariance(Twist twist, Double[] covariance) {
		this.setTwist(twist);
		this.setCovariance(covariance);
	}

	public Twist getTwist() {
		return twist;
	}

	public void setTwist(Twist twist) {
		this.twist = twist;
	}

	public Double[] getCovariance() {
		return covariance;
	}

	public void setCovariance(Double[] covariance) {
		this.covariance = covariance;
	}
	
	

}
