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

import geometry_msgs.Quaternion;
import geometry_msgs.Vector3;
import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class Imu extends RosMessage{

	private Header header;
	private Quaternion orientation;
	private Double[] orientation_covariance;
	private Vector3 angular_velocity;
	private Double[] angular_velocity_covariance;
	private Vector3 linear_acceleration;
	private Double[] linear_acceleration_covariance;
	
	public Imu() {
		
	}

	public Imu(Header header, Quaternion orientation, Double[] orientation_covariance, Vector3 angular_velocity,
			Double[] angular_velocity_covariance, Vector3 linear_acceleration,
			Double[] linear_acceleration_covariance) {
		this.header = header;
		this.orientation = orientation;
		this.orientation_covariance = orientation_covariance;
		this.angular_velocity = angular_velocity;
		this.angular_velocity_covariance = angular_velocity_covariance;
		this.linear_acceleration = linear_acceleration;
		this.linear_acceleration_covariance = linear_acceleration_covariance;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Quaternion getOrientation() {
		return orientation;
	}

	public void setOrientation(Quaternion orientation) {
		this.orientation = orientation;
	}

	public Double[] getOrientation_covariance() {
		return orientation_covariance;
	}

	public void setOrientation_covariance(Double[] orientation_covariance) {
		this.orientation_covariance = orientation_covariance;
	}

	public Vector3 getAngular_velocity() {
		return angular_velocity;
	}

	public void setAngular_velocity(Vector3 angular_velocity) {
		this.angular_velocity = angular_velocity;
	}

	public Double[] getAngular_velocity_covariance() {
		return angular_velocity_covariance;
	}

	public void setAngular_velocity_covariance(Double[] angular_velocity_covariance) {
		this.angular_velocity_covariance = angular_velocity_covariance;
	}

	public Vector3 getLinear_acceleration() {
		return linear_acceleration;
	}

	public void setLinear_acceleration(Vector3 linear_acceleration) {
		this.linear_acceleration = linear_acceleration;
	}

	public Double[] getLinear_acceleration_covariance() {
		return linear_acceleration_covariance;
	}

	public void setLinear_acceleration_covariance(Double[] linear_acceleration_covariance) {
		this.linear_acceleration_covariance = linear_acceleration_covariance;
	}
	
	
}
