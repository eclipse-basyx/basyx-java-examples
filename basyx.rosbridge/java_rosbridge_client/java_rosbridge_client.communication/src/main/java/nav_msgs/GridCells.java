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

package nav_msgs;

import geometry_msgs.Point;
import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class GridCells extends RosMessage{
	private Header header;
	private Float cell_width;
	private Float cell_height;
	private Point[] cells;
	
	public GridCells() {
		
	}
    
	public GridCells(Header header, Float cell_width, Float cell_height, Point[] cells) {
		this.setHeader(header);
		this.setCell_width(cell_width);
		this.setCell_height(cell_height);
		this.setCells(cells);
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Float getCell_width() {
		return cell_width;
	}

	public void setCell_width(Float cell_width) {
		this.cell_width = cell_width;
	}

	public Float getCell_height() {
		return cell_height;
	}

	public void setCell_height(Float cell_height) {
		this.cell_height = cell_height;
	}

	public Point[] getCells() {
		return cells;
	}

	public void setCells(Point[] cells) {
		this.cells = cells;
	}


}
