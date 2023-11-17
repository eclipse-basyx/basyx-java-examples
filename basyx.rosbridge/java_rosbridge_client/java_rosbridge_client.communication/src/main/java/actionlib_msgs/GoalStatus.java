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

package actionlib_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class GoalStatus extends RosMessage{

	private Integer status;
	
	private Integer PENDING = 0;
	private Integer ACTIVE = 1;
	private Integer PREEMPTED = 2;
	private Integer SUCCEEDED = 3;
	private Integer ABORTED = 4;
	private Integer REJECTED = 5;
	private Integer PREEMPTING = 6;
	private Integer RECALLING = 7;
	private Integer RECALLED = 8;
	private Integer LOST = 9;
	
	private String text;
	
	public GoalStatus() {
		
	}
	
	public GoalStatus(Integer status, Integer PENDING, Integer ACTIVE, Integer PREEMPTED, Integer SUCCEEDED,
			Integer ABORTED, Integer REJECTED, Integer PREEMPTING, Integer RECALLING, Integer RECALLED, Integer LOST,
			String text) {
		this.setStatus(status);
		this.PENDING = PENDING;
		this.ACTIVE = ACTIVE;
		this.PREEMPTED = PREEMPTED;
		this.SUCCEEDED = SUCCEEDED;
		this.ABORTED = ABORTED;
		this.REJECTED = REJECTED;
		this.PREEMPTING = PREEMPTING;
		this.RECALLING = RECALLING;
		this.RECALLED = RECALLED;
		this.LOST = LOST;
		this.setText(text);
	}
	
	public GoalStatus(Integer status, String text) {
		this.setStatus(status);
		this.setText(text);
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getACTIVE() {
		return ACTIVE;
	}

	public void setACTIVE(Integer aCTIVE) {
		ACTIVE = aCTIVE;
	}

	public Integer getPENDING() {
		return PENDING;
	}

	public void setPENDING(Integer pENDING) {
		PENDING = pENDING;
	}

	public Integer getPREEMPTED() {
		return PREEMPTED;
	}

	public void setPREEMPTED(Integer pREEMPTED) {
		PREEMPTED = pREEMPTED;
	}

	public Integer getSUCCEEDED() {
		return SUCCEEDED;
	}

	public void setSUCCEEDED(Integer sUCCEEDED) {
		SUCCEEDED = sUCCEEDED;
	}

	public Integer getABORTED() {
		return ABORTED;
	}

	public void setABORTED(Integer aBORTED) {
		ABORTED = aBORTED;
	}

	public Integer getREJECTED() {
		return REJECTED;
	}

	public void setREJECTED(Integer rEJECTED) {
		REJECTED = rEJECTED;
	}

	public Integer getPREEMPTING() {
		return PREEMPTING;
	}

	public void setPREEMPTING(Integer pREEMPTING) {
		PREEMPTING = pREEMPTING;
	}

	public Integer getRECALLING() {
		return RECALLING;
	}

	public void setRECALLING(Integer rECALLING) {
		RECALLING = rECALLING;
	}

	public Integer getRECALLED() {
		return RECALLED;
	}

	public void setRECALLED(Integer rECALLED) {
		RECALLED = rECALLED;
	}

	public Integer getLOST() {
		return LOST;
	}

	public void setLOST(Integer lOST) {
		LOST = lOST;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
