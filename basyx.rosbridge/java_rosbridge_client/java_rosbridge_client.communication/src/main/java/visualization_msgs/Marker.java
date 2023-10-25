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

package visualization_msgs;

import geometry_msgs.Point;
import geometry_msgs.Pose;
import geometry_msgs.Vector3;
import java_rosbridge_client.core.utility.RosMessage;
import msg_elements.Duration;
import std_msgs.ColorRGBA;
import std_msgs.Header;

public class Marker extends RosMessage {
	
	private Short ARROW=0;
	private Short CUBE=1;
	private Short SPHERE=2;
	private Short CYLINDER=3;
	private Short LINE_STRIP=4;
	private Short LINE_LIST=5;
	private Short CUBE_LIST=6;
	private Short SPHERE_LIST=7;
	private Short POINTS=8;
	private Short TEXT_VIEW_FACING=9;
	private Short MESH_RESOURCE=10;
	private Short TRIANGLE_LIST=11;
	private Short ADD=0;
	private Short MODIFY=0;
	private Short DELETE=2;
	private Short DELETEALL=3;
	private Header header;
	private String ns;
	private Integer id;
	private Integer type;
	private Integer action;
	private Pose pose;
	private Vector3 scale;
	private ColorRGBA color;
	private Duration lifetime;
	private Boolean frame_locked;
	private Point[] points;
	private ColorRGBA[] colors;
	private String text;
	private String mesh_resource;
	private Boolean mesh_use_embedded_materials;
	
	public Marker() {
		
	}
	
	public Marker(Short ARROW, Short CUBE, Short SPHERE, Short CYLINDER, Short LINE_STRIP, Short LINE_LIST,
			Short CUBE_LIST, Short SPHERE_LIST, Short POINTS, Short TEXT_VIEW_FACING, Short MESH_RESOURCE,
			Short TRIANGLE_LIST, Short ADD, Short MODIFY, Short DELETE, Short DELETEALL, 
			Header header, String ns, Integer id, Integer type, Integer action, Pose pose, Vector3 scale,
			ColorRGBA color, Duration lifetime, Boolean frame_locked, Point[] points, ColorRGBA[] colors,
			String text, String mesh_resource, Boolean mesh_use_embedded_materials) {
		this.setARROW(ARROW);
		this.setCUBE(CUBE);
		this.setSPHERE(SPHERE);
		this.setCYLINDER(CYLINDER);
		this.setLINE_STRIP(LINE_STRIP);
		this.setLINE_LIST(LINE_LIST);
		this.setCUBE_LIST(CUBE_LIST);
		this.setSPHERE_LIST(SPHERE_LIST);
		this.setPOINTS(POINTS);
		this.setTEXT_VIEW_FACING(TEXT_VIEW_FACING);
		this.setMESH_RESOURCE(MESH_RESOURCE);
		this.setTRIANGLE_LIST(TRIANGLE_LIST);
		this.setADD(ADD);
		this.setMODIFY(MODIFY);
		this.setDELETE(DELETEALL);
		this.setDELETEALL(DELETEALL);
		this.setHeader(header);
		this.setNs(ns);
		this.setId(id);
		this.setType(type);
		this.setAction(action);
		this.setPose(pose);
		this.setScale(scale);
		this.setColor(color);
		this.setLifetime(lifetime);
		this.setFrame_locked(frame_locked);
		this.setPoints(points);
		this.setColors(colors);
		this.setText(text);
		this.setMesh_resource(mesh_resource);
		this.setMesh_use_embedded_materials(mesh_use_embedded_materials);
	}
	
	public Marker(Header header, String ns, Integer id, Integer type, Integer action, Pose pose, Vector3 scale,
			ColorRGBA color, Duration lifetime, Boolean frame_locked, Point[] points, ColorRGBA[] colors,
			String text, String mesh_resource, Boolean mesh_use_embedded_materials) {
		this.setHeader(header);
		this.setNs(ns);
		this.setId(id);
		this.setType(type);
		this.setAction(action);
		this.setPose(pose);
		this.setScale(scale);
		this.setColor(color);
		this.setLifetime(lifetime);
		this.setFrame_locked(frame_locked);
		this.setPoints(points);
		this.setColors(colors);
		this.setText(text);
		this.setMesh_resource(mesh_resource);
		this.setMesh_use_embedded_materials(mesh_use_embedded_materials);
	}

	public Short getARROW() {
		return ARROW;
	}

	public void setARROW(Short aRROW) {
		ARROW = aRROW;
	}

	public Short getCUBE() {
		return CUBE;
	}

	public void setCUBE(Short cUBE) {
		CUBE = cUBE;
	}

	public Short getSPHERE() {
		return SPHERE;
	}

	public void setSPHERE(Short sPHERE) {
		SPHERE = sPHERE;
	}

	public Short getCYLINDER() {
		return CYLINDER;
	}

	public void setCYLINDER(Short cYLINDER) {
		CYLINDER = cYLINDER;
	}

	public Short getLINE_STRIP() {
		return LINE_STRIP;
	}

	public void setLINE_STRIP(Short lINE_STRIP) {
		LINE_STRIP = lINE_STRIP;
	}

	public Short getLINE_LIST() {
		return LINE_LIST;
	}

	public void setLINE_LIST(Short lINE_LIST) {
		LINE_LIST = lINE_LIST;
	}

	public Short getCUBE_LIST() {
		return CUBE_LIST;
	}

	public void setCUBE_LIST(Short cUBE_LIST) {
		CUBE_LIST = cUBE_LIST;
	}

	public Short getSPHERE_LIST() {
		return SPHERE_LIST;
	}

	public void setSPHERE_LIST(Short sPHERE_LIST) {
		SPHERE_LIST = sPHERE_LIST;
	}

	public Short getPOINTS() {
		return POINTS;
	}

	public void setPOINTS(Short pOINTS) {
		POINTS = pOINTS;
	}

	public Short getTEXT_VIEW_FACING() {
		return TEXT_VIEW_FACING;
	}

	public void setTEXT_VIEW_FACING(Short tEXT_VIEW_FACING) {
		TEXT_VIEW_FACING = tEXT_VIEW_FACING;
	}

	public Short getMESH_RESOURCE() {
		return MESH_RESOURCE;
	}

	public void setMESH_RESOURCE(Short mESH_RESOURCE) {
		MESH_RESOURCE = mESH_RESOURCE;
	}

	public Short getTRIANGLE_LIST() {
		return TRIANGLE_LIST;
	}

	public void setTRIANGLE_LIST(Short tRIANGLE_LIST) {
		TRIANGLE_LIST = tRIANGLE_LIST;
	}

	public Short getADD() {
		return ADD;
	}

	public void setADD(Short aDD) {
		ADD = aDD;
	}

	public Short getMODIFY() {
		return MODIFY;
	}

	public void setMODIFY(Short mODIFY) {
		MODIFY = mODIFY;
	}

	public Short getDELETE() {
		return DELETE;
	}

	public void setDELETE(Short dELETE) {
		DELETE = dELETE;
	}

	public Short getDELETEALL() {
		return DELETEALL;
	}

	public void setDELETEALL(Short dELETEALL) {
		DELETEALL = dELETEALL;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public String getNs() {
		return ns;
	}

	public void setNs(String ns) {
		this.ns = ns;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public Pose getPose() {
		return pose;
	}

	public void setPose(Pose pose) {
		this.pose = pose;
	}

	public Vector3 getScale() {
		return scale;
	}

	public void setScale(Vector3 scale) {
		this.scale = scale;
	}

	public ColorRGBA getColor() {
		return color;
	}

	public void setColor(ColorRGBA color) {
		this.color = color;
	}

	public Duration getLifetime() {
		return lifetime;
	}

	public void setLifetime(Duration lifetime) {
		this.lifetime = lifetime;
	}

	public Boolean getFrame_locked() {
		return frame_locked;
	}

	public void setFrame_locked(Boolean frame_locked) {
		this.frame_locked = frame_locked;
	}

	public ColorRGBA[] getColors() {
		return colors;
	}

	public void setColors(ColorRGBA[] colors) {
		this.colors = colors;
	}

	public Point[] getPoints() {
		return points;
	}

	public void setPoints(Point[] points) {
		this.points = points;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getMesh_resource() {
		return mesh_resource;
	}

	public void setMesh_resource(String mesh_resource) {
		this.mesh_resource = mesh_resource;
	}

	public Boolean getMesh_use_embedded_materials() {
		return mesh_use_embedded_materials;
	}

	public void setMesh_use_embedded_materials(Boolean mesh_use_embedded_materials) {
		this.mesh_use_embedded_materials = mesh_use_embedded_materials;
	}

}
