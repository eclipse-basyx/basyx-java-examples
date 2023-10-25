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

package trajectory_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import msg_elements.Duration;

public class JointTrajectoryPoint extends RosMessage{
	
	private Double[] positions;
	private Double[] velocities;
	private Double[] accelerations;
	private Double[] effort;
	private Duration time_from_start;
	
	public JointTrajectoryPoint() {
		
	}
	
	public JointTrajectoryPoint(Double[] positions, Double[] velocities, Double[] accelerations,
			Double[] effort, Duration time_from_start) {
		this.setPositions(positions);
		this.setVelocities(velocities);
		this.setAccelerations(accelerations);
		this.setEffort(effort);
		this.setTime_from_start(time_from_start);
	}

	public Double[] getPositions() {
		return positions;
	}

	public void setPositions(Double[] positions) {
		this.positions = positions;
	}

	public Double[] getVelocities() {
		return velocities;
	}

	public void setVelocities(Double[] velocities) {
		this.velocities = velocities;
	}

	public Double[] getAccelerations() {
		return accelerations;
	}

	public void setAccelerations(Double[] accelerations) {
		this.accelerations = accelerations;
	}

	public Double[] getEffort() {
		return effort;
	}

	public void setEffort(Double[] effort) {
		this.effort = effort;
	}

	public Duration getTime_from_start() {
		return time_from_start;
	}

	public void setTime_from_start(Duration time_from_start) {
		this.time_from_start = time_from_start;
	}

}
