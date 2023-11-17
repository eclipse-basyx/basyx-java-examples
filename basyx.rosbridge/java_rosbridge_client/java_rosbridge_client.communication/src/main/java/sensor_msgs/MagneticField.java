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

import geometry_msgs.Vector3;
import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class MagneticField extends RosMessage {
	
	private Header header;
	private Vector3 magnetic_field;
	private Double[] magnetic_field_covariance;
	
	public MagneticField() {
		
	}

	public MagneticField(Header header, Vector3 magnetic_field, Double[] magnetic_field_covariance) {
		this.header = header;
		this.magnetic_field = magnetic_field;
		this.magnetic_field_covariance = magnetic_field_covariance;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Vector3 getMagnetic_field() {
		return magnetic_field;
	}

	public void setMagnetic_field(Vector3 magnetic_field) {
		this.magnetic_field = magnetic_field;
	}

	public Double[] getMagnetic_field_covariance() {
		return magnetic_field_covariance;
	}

	public void setMagnetic_field_covariance(Double[] magnetic_field_covariance) {
		this.magnetic_field_covariance = magnetic_field_covariance;
	}
	
	

}
