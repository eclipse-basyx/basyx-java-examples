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

package shape_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class SolidPrimitive extends RosMessage{
	private Integer BOX=1;
	private Integer SPHERE=2;
	private Integer CYLINDER=3;
	private Integer CONE=4;
	private Integer BOX_X=0;
	private Integer BOX_Y=1;
	private Integer BOX_Z=2;
	private Integer SPHERE_RADIUS=0;
	private Integer CYLINDER_HEIGHT=0;
	private Integer CYLINDER_RADIUS=1;
	private Integer CONE_HEIGHT=0;
	private Integer CONE_RADIUS=1;
	private Integer type;
	private Double[] dimensions;
	
	public SolidPrimitive() {
		
	}
	
	public SolidPrimitive(Integer BOX, Integer SPHERE, Integer CYLINDER, Integer CONE, Integer BOX_X,
			Integer BOX_Y, Integer BOX_Z, Integer SPHERE_RADIUS, Integer CYLINDER_HEIGHT, Integer CYLINDER_RADIUS,
			Integer CONE_HEIGHT, Integer CONE_RADIUS, Integer type, Double[] dimensions) {
		this.setBOX(BOX);
		this.setSPHERE(SPHERE);
		this.setCYLINDER(CYLINDER);
		this.setCONE(CONE);
		this.setBOX_X(BOX_X);
		this.setBOX_Y(BOX_Y);
		this.setBOX_Z(BOX_Z);
		this.setSPHERE_RADIUS(SPHERE_RADIUS);
		this.setCYLINDER_HEIGHT(CYLINDER_HEIGHT);
		this.setCYLINDER_RADIUS(CYLINDER_RADIUS);
		this.setCONE_HEIGHT(CONE_HEIGHT);
		this.setCYLINDER_RADIUS(CYLINDER_RADIUS);
		this.setType(type);
		this.setDimensions(dimensions);
	}
	
	public SolidPrimitive(Integer type, Double[] dimensions) {
		this.setType(type);
		this.setDimensions(dimensions);
	}

	public Integer getBOX() {
		return BOX;
	}

	public void setBOX(Integer bOX) {
		BOX = bOX;
	}

	public Integer getSPHERE() {
		return SPHERE;
	}

	public void setSPHERE(Integer sPHERE) {
		SPHERE = sPHERE;
	}

	public Integer getCYLINDER() {
		return CYLINDER;
	}

	public void setCYLINDER(Integer cYLINDER) {
		CYLINDER = cYLINDER;
	}

	public Integer getCONE() {
		return CONE;
	}

	public void setCONE(Integer cONE) {
		CONE = cONE;
	}

	public Integer getBOX_X() {
		return BOX_X;
	}

	public void setBOX_X(Integer bOX_X) {
		BOX_X = bOX_X;
	}

	public Integer getBOX_Y() {
		return BOX_Y;
	}

	public void setBOX_Y(Integer bOX_Y) {
		BOX_Y = bOX_Y;
	}

	public Integer getBOX_Z() {
		return BOX_Z;
	}

	public void setBOX_Z(Integer bOX_Z) {
		BOX_Z = bOX_Z;
	}

	public Integer getSPHERE_RADIUS() {
		return SPHERE_RADIUS;
	}

	public void setSPHERE_RADIUS(Integer sPHERE_RADIUS) {
		SPHERE_RADIUS = sPHERE_RADIUS;
	}

	public Integer getCYLINDER_HEIGHT() {
		return CYLINDER_HEIGHT;
	}

	public void setCYLINDER_HEIGHT(Integer cYLINDER_HEIGHT) {
		CYLINDER_HEIGHT = cYLINDER_HEIGHT;
	}

	public Integer getCYLINDER_RADIUS() {
		return CYLINDER_RADIUS;
	}

	public void setCYLINDER_RADIUS(Integer cYLINDER_RADIUS) {
		CYLINDER_RADIUS = cYLINDER_RADIUS;
	}

	public Integer getCONE_HEIGHT() {
		return CONE_HEIGHT;
	}

	public void setCONE_HEIGHT(Integer cONE_HEIGHT) {
		CONE_HEIGHT = cONE_HEIGHT;
	}

	public Integer getCONE_RADIUS() {
		return CONE_RADIUS;
	}

	public void setCONE_RADIUS(Integer cONE_RADIUS) {
		CONE_RADIUS = cONE_RADIUS;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Double[] getDimensions() {
		return dimensions;
	}

	public void setDimensions(Double[] dimensions) {
		this.dimensions = dimensions;
	}
}
