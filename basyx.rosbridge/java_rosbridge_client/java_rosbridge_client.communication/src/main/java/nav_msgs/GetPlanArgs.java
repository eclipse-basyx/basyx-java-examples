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

import geometry_msgs.PoseStamped;
import java_rosbridge_client.core.utility.RosServiceArgs;

public class GetPlanArgs extends RosServiceArgs{
	private PoseStamped start;
	private PoseStamped goal;
	private Float tolerance;
	
	public GetPlanArgs() {
		
	}
	
    public GetPlanArgs(PoseStamped start, PoseStamped goal, Float tolerance) {
		this.setStart(start);
		this.setGoal(goal);
		this.setTolerance(tolerance);
	}

	public PoseStamped getStart() {
		return start;
	}

	public void setStart(PoseStamped start) {
		this.start = start;
	}

	public PoseStamped getGoal() {
		return goal;
	}

	public void setGoal(PoseStamped goal) {
		this.goal = goal;
	}

	public Float getTolerance() {
		return tolerance;
	}

	public void setTolerance(Float tolerance) {
		this.tolerance = tolerance;
	}
}
