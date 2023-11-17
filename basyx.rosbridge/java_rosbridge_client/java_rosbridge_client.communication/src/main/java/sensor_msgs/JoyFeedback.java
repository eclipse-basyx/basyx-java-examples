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

import java_rosbridge_client.core.utility.RosMessage;

public class JoyFeedback extends RosMessage {
	private Integer TYPE_LED = 0;
	private Integer TYPE_RUMBLE = 1;
	private Integer TYPE_BUZZER = 2; 
	
	private Integer type;
	//only 0-255 allowed, start with 0 for first LED
	private Integer id;
	private Float intensity;
	
	public JoyFeedback() {
		
	}

	public JoyFeedback(Integer tYPE_LED, Integer tYPE_RUMBLE, Integer tYPE_BUZZER, Integer type, Integer id,
			Float intensity) {
		TYPE_LED = tYPE_LED;
		TYPE_RUMBLE = tYPE_RUMBLE;
		TYPE_BUZZER = tYPE_BUZZER;
		this.type = type;
		this.id = id;
		this.intensity = intensity;
	}

	public Integer getTYPE_LED() {
		return TYPE_LED;
	}

	public void setTYPE_LED(Integer tYPE_LED) {
		TYPE_LED = tYPE_LED;
	}

	public Integer getTYPE_RUMBLE() {
		return TYPE_RUMBLE;
	}

	public void setTYPE_RUMBLE(Integer tYPE_RUMBLE) {
		TYPE_RUMBLE = tYPE_RUMBLE;
	}

	public Integer getTYPE_BUZZER() {
		return TYPE_BUZZER;
	}

	public void setTYPE_BUZZER(Integer tYPE_BUZZER) {
		TYPE_BUZZER = tYPE_BUZZER;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getIntensity() {
		return intensity;
	}

	public void setIntensity(Float intensity) {
		this.intensity = intensity;
	}
	
}
