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

import geometry_msgs.Pose;
import java_rosbridge_client.core.utility.RosMessage;
import msg_elements.Time;

public class MapMetaData extends RosMessage{
	private Time map_load_time;
	private Float resolution;
	private Long width;
	private Long height;
	private Pose origin;
	
	public MapMetaData() {
		
	}
	
    public MapMetaData(Time map_load_time, Float resolution, Long width, Long height, Pose origin) {
		this.setMap_load_time(map_load_time);
		this.setResolution(resolution);
		this.setWidth(width);
		this.setHeight(height);
		this.setOrigin(origin);
	}

	public Time getMap_load_time() {
		return map_load_time;
	}

	public void setMap_load_time(Time map_load_time) {
		this.map_load_time = map_load_time;
	}

	public Float getResolution() {
		return resolution;
	}

	public void setResolution(Float resolution) {
		this.resolution = resolution;
	}

	public Long getWidth() {
		return width;
	}

	public void setWidth(Long width) {
		this.width = width;
	}

	public Long getHeight() {
		return height;
	}

	public void setHeight(Long height) {
		this.height = height;
	}

	public Pose getOrigin() {
		return origin;
	}

	public void setOrigin(Pose origin) {
		this.origin = origin;
	}
}
