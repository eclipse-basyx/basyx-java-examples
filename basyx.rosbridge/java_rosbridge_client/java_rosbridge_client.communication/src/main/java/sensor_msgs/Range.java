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
import std_msgs.Header;

public class Range extends RosMessage {
	
	private Header header;
	
	private Integer ULTRASOUND=0;
	private Integer INFRARED=1;
	
	private Integer radiation_type;
	
	private Float field_of_view;
	
	private Float min_range;
	private Float max_range;
	
	private Float range;
	
	public Range() {
		
	}

	public Range(Header header, Integer uLTRASOUND, Integer iNFRARED, Integer radiation_type, Float field_of_view,
			Float min_range, Float max_range, Float range) {
		this.header = header;
		this.ULTRASOUND = uLTRASOUND;
		this.INFRARED = iNFRARED;
		this.radiation_type = radiation_type;
		this.field_of_view = field_of_view;
		this.min_range = min_range;
		this.max_range = max_range;
		this.range = range;
	}
	
	public Range(Header header, Integer radiation_type, Float field_of_view,
			Float min_range, Float max_range, Float range) {
		this.header = header;
		
		this.radiation_type = radiation_type;
		this.field_of_view = field_of_view;
		this.min_range = min_range;
		this.max_range = max_range;
		this.range = range;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Integer getULTRASOUND() {
		return ULTRASOUND;
	}

	public void setULTRASOUND(Integer uLTRASOUND) {
		ULTRASOUND = uLTRASOUND;
	}

	public Integer getINFRARED() {
		return INFRARED;
	}

	public void setINFRARED(Integer iNFRARED) {
		INFRARED = iNFRARED;
	}

	public Integer getRadiation_type() {
		return radiation_type;
	}

	public void setRadiation_type(Integer radiation_type) {
		this.radiation_type = radiation_type;
	}

	public Float getField_of_view() {
		return field_of_view;
	}

	public void setField_of_view(Float field_of_view) {
		this.field_of_view = field_of_view;
	}

	public Float getMin_range() {
		return min_range;
	}

	public void setMin_range(Float min_range) {
		this.min_range = min_range;
	}

	public Float getMax_range() {
		return max_range;
	}

	public void setMax_range(Float max_range) {
		this.max_range = max_range;
	}

	public Float getRange() {
		return range;
	}

	public void setRange(Float range) {
		this.range = range;
	}
	
	
	
	

}
