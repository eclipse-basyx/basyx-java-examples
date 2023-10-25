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

package geometry_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class Inertia extends RosMessage{

	private Double m;
	private Vector3 com;
	private Double ixx;
	private Double ixy;
	private Double ixz;
	private Double iyy;
	private Double iyz;
	private Double izz;
	
	public Inertia() {
		
	}
	
	public Inertia(Double m, Vector3 com, Double ixx, Double ixy, Double ixz,
			Double iyy, Double iyz, Double izz) {
		this.setM(m);
		this.setCom(com);
		this.setIxx(ixx);
		this.setIxy(ixy);
		this.setIxz(ixz);
		this.setIyy(iyy);
		this.setIyz(iyz);
		this.setIzz(izz);
	}

	public Double getM() {
		return m;
	}

	public void setM(Double m) {
		this.m = m;
	}

	public Vector3 getCom() {
		return com;
	}

	public void setCom(Vector3 com) {
		this.com = com;
	}

	public Double getIxz() {
		return ixz;
	}

	public void setIxz(Double ixz) {
		this.ixz = ixz;
	}

	public Double getIxy() {
		return ixy;
	}

	public void setIxy(Double ixy) {
		this.ixy = ixy;
	}

	public Double getIxx() {
		return ixx;
	}

	public void setIxx(Double ixx) {
		this.ixx = ixx;
	}

	public Double getIyy() {
		return iyy;
	}

	public void setIyy(Double iyy) {
		this.iyy = iyy;
	}

	public Double getIyz() {
		return iyz;
	}

	public void setIyz(Double iyz) {
		this.iyz = iyz;
	}

	public Double getIzz() {
		return izz;
	}

	public void setIzz(Double izz) {
		this.izz = izz;
	}

}
