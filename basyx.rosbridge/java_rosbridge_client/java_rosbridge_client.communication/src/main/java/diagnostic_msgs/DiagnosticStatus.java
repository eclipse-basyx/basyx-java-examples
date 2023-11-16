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

package diagnostic_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class DiagnosticStatus extends RosMessage{
	private Integer OK=0;
	private Integer WARN=1;
	private Integer ERROR=2;
	private Integer STALE=3;
	
	private Integer level;
	private String name;
	private String message;
	private String hardware_id;
	private KeyValue[] values;
	
	public DiagnosticStatus() {
		
	}
	
	public DiagnosticStatus(Integer OK, Integer WARN, Integer ERROR, Integer STALE, Integer level,
			String name, String message, String hardware_id, KeyValue[] values) {
		this.setOK(OK);
		this.setWARN(WARN);
		this.setERROR(ERROR);
		this.setSTALE(STALE);
		this.setLevel(level);
		this.setName(name);
		this.setMessage(message);
		this.setHardware_id(hardware_id);
		this.setValues(values);
		
	}
	
	public DiagnosticStatus(Integer level, String name, String message, String hardware_id, KeyValue[] values) {
		this.setLevel(level);
		this.setName(name);
		this.setMessage(message);
		this.setHardware_id(hardware_id);
		this.setValues(values);
	}

	public Integer getOK() {
		return OK;
	}

	public void setOK(Integer oK) {
		OK = oK;
	}

	public Integer getWARN() {
		return WARN;
	}

	public void setWARN(Integer wARN) {
		WARN = wARN;
	}

	public Integer getERROR() {
		return ERROR;
	}

	public void setERROR(Integer eRROR) {
		ERROR = eRROR;
	}

	public Integer getSTALE() {
		return STALE;
	}

	public void setSTALE(Integer sTALE) {
		STALE = sTALE;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getHardware_id() {
		return hardware_id;
	}

	public void setHardware_id(String hardware_id) {
		this.hardware_id = hardware_id;
	}

	public KeyValue[] getValues() {
		return values;
	}

	public void setValues(KeyValue[] values) {
		this.values = values;
	}

}
