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

public class RegionOfInterest {
	private Long x_offset;
	private Long y_offset;
	private Long height;
	private Long width;
	private Boolean rectify;
	
	public RegionOfInterest() {
		
	}

	public RegionOfInterest(Long x_offset, Long y_offset, Long height, Long width, Boolean rectify) {
		this.x_offset = x_offset;
		this.y_offset = y_offset;
		this.height = height;
		this.width = width;
		this.rectify = rectify;
	}

	public Long getX_offset() {
		return x_offset;
	}

	public void setX_offset(Long x_offset) {
		this.x_offset = x_offset;
	}

	public Long getY_offset() {
		return y_offset;
	}

	public void setY_offset(Long y_offset) {
		this.y_offset = y_offset;
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

	public Boolean getRectify() {
		return rectify;
	}

	public void setRectify(Boolean rectify) {
		this.rectify = rectify;
	}
	
	
}
