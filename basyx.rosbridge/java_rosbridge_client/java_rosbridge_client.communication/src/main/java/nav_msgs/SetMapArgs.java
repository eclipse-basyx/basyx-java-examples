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

import geometry_msgs.PoseWithCovarianceStamped;
import java_rosbridge_client.core.utility.RosServiceArgs;

public class SetMapArgs extends RosServiceArgs{
	
	private OccupancyGrid map;
	private PoseWithCovarianceStamped initial_pose;
	
	public SetMapArgs() {
		
	}
	
    public SetMapArgs(OccupancyGrid map, PoseWithCovarianceStamped initial_pose) {
		this.setMap(map);
    	this.setInitial_pose(initial_pose);
	}

	public OccupancyGrid getMap() {
		return map;
	}

	public void setMap(OccupancyGrid map) {
		this.map = map;
	}

	public PoseWithCovarianceStamped getInitial_pose() {
		return initial_pose;
	}

	public void setInitial_pose(PoseWithCovarianceStamped initial_pose) {
		this.initial_pose = initial_pose;
	}

}
