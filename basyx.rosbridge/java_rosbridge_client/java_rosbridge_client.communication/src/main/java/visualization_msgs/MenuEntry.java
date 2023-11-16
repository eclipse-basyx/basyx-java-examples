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

import java_rosbridge_client.core.utility.RosMessage;

public class MenuEntry extends RosMessage{

	private Short FEEDBACK=0;
	private Short ROSRUN=1;
	private Short ROSLAUNCH=2;
	private Long id;
	private Long parent_id;
	private String title;
	private String command;
	private Short command_type;
	
	public MenuEntry() {
		
	}
	
	public MenuEntry(Short FEEDBACK, Short ROSRUN, Short ROSLAUNCH, Long id,
			Long parent_id, String title, String command, Short command_type) {
		this.setFEEDBACK(FEEDBACK);
		this.setROSRUN(ROSRUN);
		this.setROSLAUNCH(ROSLAUNCH);
		this.setId(id);
		this.setParent_id(parent_id);
		this.setTitle(title);
		this.setCommand(command);
		this.setCommand_type(command_type);
	}
	
	public MenuEntry(Long id, Long parent_id, String title, String command, Short command_type) {
		this.setId(id);
		this.setParent_id(parent_id);
		this.setTitle(title);
		this.setCommand(command);
		this.setCommand_type(command_type);
	}

	public Short getFEEDBACK() {
		return FEEDBACK;
	}

	public void setFEEDBACK(Short fEEDBACK) {
		FEEDBACK = fEEDBACK;
	}

	public Short getROSRUN() {
		return ROSRUN;
	}

	public void setROSRUN(Short rOSRUN) {
		ROSRUN = rOSRUN;
	}

	public Short getROSLAUNCH() {
		return ROSLAUNCH;
	}

	public void setROSLAUNCH(Short rOSLAUNCH) {
		ROSLAUNCH = rOSLAUNCH;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParent_id() {
		return parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Short getCommand_type() {
		return command_type;
	}

	public void setCommand_type(Short command_type) {
		this.command_type = command_type;
	}
}
