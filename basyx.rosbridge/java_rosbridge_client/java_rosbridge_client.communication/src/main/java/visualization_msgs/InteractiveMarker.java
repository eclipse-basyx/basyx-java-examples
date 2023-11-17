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

package visualization_msgs;

import geometry_msgs.Pose;
import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class InteractiveMarker extends RosMessage{
	
	private Header header;
	private Pose pose;
	private String name;
	private String description;
	private Float scale;
	private MenuEntry[] menu_entries;
	private InteractiveMarkerControl[] controls;
	
	public InteractiveMarker() {
		
	}
	
	public InteractiveMarker(Header header, Pose pose, String name, String description,
			Float scale, MenuEntry[] menu_entries, InteractiveMarkerControl[] controls) {
		this.setHeader(header);
		this.setPose(pose);
		this.setName(name);
		this.setDescription(description);
		this.setScale(scale);
		this.setMenu_entries(menu_entries);
		this.setControls(controls);
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Pose getPose() {
		return pose;
	}

	public void setPose(Pose pose) {
		this.pose = pose;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getScale() {
		return scale;
	}

	public void setScale(Float scale) {
		this.scale = scale;
	}

	public MenuEntry[] getMenu_entries() {
		return menu_entries;
	}

	public void setMenu_entries(MenuEntry[] menu_entries) {
		this.menu_entries = menu_entries;
	}

	public InteractiveMarkerControl[] getControls() {
		return controls;
	}

	public void setControls(InteractiveMarkerControl[] controls) {
		this.controls = controls;
	}

}
