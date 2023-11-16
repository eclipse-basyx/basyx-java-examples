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

package std_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class MultiArrayLayout extends RosMessage{
	private MultiArrayDimension[] dim;
	private Long data_offset;
	
	public MultiArrayLayout() {
		
	}
	
	public MultiArrayLayout(MultiArrayDimension[] dim, Long data_offset) {
		this.setDim(dim);
		this.setData_offset(data_offset);
	}

	public MultiArrayDimension[] getDim() {
		return dim;
	}

	public void setDim(MultiArrayDimension[] dim) {
		this.dim = dim;
	}

	public Long getData_offset() {
		return data_offset;
	}

	public void setData_offset(Long data_offset) {
		this.data_offset = data_offset;
	}

}
