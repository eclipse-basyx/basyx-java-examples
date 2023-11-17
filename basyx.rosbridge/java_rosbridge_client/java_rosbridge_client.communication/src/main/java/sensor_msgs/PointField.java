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

public class PointField extends RosMessage{
	
	private Integer INT8    = 1;
	private Integer UINT8   = 2;
	private Integer INT16   = 3;
	private Integer UINT16  = 4;
	private Integer INT32   = 5;
	private Integer UINT32  = 6;
	private Integer FLOAT32 = 7;
	private Integer FLOAT64 = 8;
	
	private String name;
	private Long offset;
	private Integer datatype;
	private Long count;
	
	public PointField() {
		
	}

	public PointField(Integer iNT8, Integer uINT8, Integer iNT16, Integer uINT16, Integer iNT32, Integer uINT32,
			Integer fLOAT32, Integer fLOAT64, String name, Long offset, Integer datatype, Long count) {
		this.INT8 = iNT8;
		this.UINT8 = uINT8;
		this.INT16 = iNT16;
		this.UINT16 = uINT16;
		this.INT32 = iNT32;
		this.UINT32 = uINT32;
		this.FLOAT32 = fLOAT32;
		this.FLOAT64 = fLOAT64;
		this.name = name;
		this.offset = offset;
		this.datatype = datatype;
		this.count = count;
	}
	
	public PointField(String name, Long offset, Integer datatype, Long count) {
		this.name = name;
		this.offset = offset;
		this.datatype = datatype;
		this.count = count;
	}

	public Integer getINT8() {
		return INT8;
	}

	public void setINT8(Integer iNT8) {
		INT8 = iNT8;
	}

	public Integer getUINT8() {
		return UINT8;
	}

	public void setUINT8(Integer uINT8) {
		UINT8 = uINT8;
	}

	public Integer getINT16() {
		return INT16;
	}

	public void setINT16(Integer iNT16) {
		INT16 = iNT16;
	}

	public Integer getUINT16() {
		return UINT16;
	}

	public void setUINT16(Integer uINT16) {
		UINT16 = uINT16;
	}

	public Integer getINT32() {
		return INT32;
	}

	public void setINT32(Integer iNT32) {
		INT32 = iNT32;
	}

	public Integer getUINT32() {
		return UINT32;
	}

	public void setUINT32(Integer uINT32) {
		UINT32 = uINT32;
	}

	public Integer getFLOAT32() {
		return FLOAT32;
	}

	public void setFLOAT32(Integer fLOAT32) {
		FLOAT32 = fLOAT32;
	}

	public Integer getFLOAT64() {
		return FLOAT64;
	}

	public void setFLOAT64(Integer fLOAT64) {
		FLOAT64 = fLOAT64;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getOffset() {
		return offset;
	}

	public void setOffset(Long offset) {
		this.offset = offset;
	}

	public Integer getDatatype() {
		return datatype;
	}

	public void setDatatype(Integer datatype) {
		this.datatype = datatype;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
	
	
}
