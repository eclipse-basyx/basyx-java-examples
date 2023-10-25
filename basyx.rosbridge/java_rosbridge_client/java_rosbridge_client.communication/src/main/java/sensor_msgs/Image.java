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

import com.google.gson.annotations.JsonAdapter;

import java_rosbridge_client.core.utility.Base64TypeAdapterFactory;
import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class Image extends RosMessage{
	
	private Header header;
	private Long height;
	private Long width;
	private String encoding;
	private Integer is_bigendian;
	private Long step;
	
	@JsonAdapter(Base64TypeAdapterFactory.class)
	private Integer[] data;
	
	public Image() {
		
	}

	public Image(Header header, Long height, Long width, String encoding, Integer is_bigendian, Long step,
			Integer[] data) {
		this.header = header;
		this.height = height;
		this.width = width;
		this.encoding = encoding;
		this.is_bigendian = is_bigendian;
		this.step = step;
		this.data = data;
		
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Long getHeight() {
		return height;
	}

	public void setHeight(Long height) {
		this.height = height;
	}

	public Long getWidth() {
		return width;
	}

	public void setWidth(Long width) {
		this.width = width;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Integer getIs_bigendian() {
		return is_bigendian;
	}

	public void setIs_bigendian(Integer is_bigendian) {
		this.is_bigendian = is_bigendian;
	}

	public Long getStep() {
		return step;
	}

	public void setStep(Long step) {
		this.step = step;
	}

	public Integer[] getData() {
		return data;
	}

	public void setData(Integer[] data) {
		this.data = data;
	}
	
	
}
