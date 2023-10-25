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

import geometry_msgs.Quaternion;
import java_rosbridge_client.core.utility.RosMessage;

public class InteractiveMarkerControl extends RosMessage{
	
	private Short INHERIT=0;
	private Short FIXED=1;
	private Short VIEW_FACING=2;
	private Short NONE=0;
	private Short MENU=1;
	private Short BUTTON=2;
	private Short MOVE_AXIS=3;
	private Short MOVE_PLANE=4;
	private Short ROTATE_AXIS=5;
	private Short MOVE_ROTATE=6;
	private Short MOVE_3D=7;
	private Short ROTATE_3D=8;
	private Short MOVE_ROTATE_3D=9;
	private String name;
	private Quaternion orientation;
	private Short orientation_mode;
	private Short interaction_mode;
	private Boolean always_visible;
	private Marker[] markers;
	private Boolean independent_marker_orientation;
	private String description;
			
	public InteractiveMarkerControl() {
		
	}

	public InteractiveMarkerControl(Short INHERIT, Short FIXED, Short VIEW_FACING, Short NONE,
			Short MENU, Short BUTTON, Short MOVE_AXIS, Short MOVE_PLANE, Short ROTATE_AXIS,
			Short MOVE_ROTATE, Short MOVE_3D, Short ROTATE_3D, Short MOVE_ROTATE_3D, 
			String name, Quaternion orientation, Short orientation_mode, Short interaction_mode,
			Boolean always_visible, Marker[] markers, Boolean independent_marker_orientation, 
			String description) {
		this.setINHERIT(INHERIT);
		this.setFIXED(FIXED);
		this.setVIEW_FACING(VIEW_FACING);
		this.setNONE(NONE);
		this.setMENU(MENU);
		this.setBUTTON(BUTTON);
		this.setMOVE_AXIS(MOVE_AXIS);
		this.setMOVE_PLANE(MOVE_PLANE);
		this.setROTATE_AXIS(ROTATE_AXIS);
		this.setMOVE_ROTATE(MOVE_ROTATE);
		this.setMOVE_3D(MOVE_3D);
		this.setROTATE_3D(MOVE_ROTATE_3D);
		this.setMOVE_ROTATE_3D(MOVE_ROTATE_3D);
		this.setName(name);
		this.setOrientation(orientation);
		this.setOrientation_mode(orientation_mode);
		this.setInteraction_mode(interaction_mode);
		this.setAlways_visible(always_visible);
		this.setMarkers(markers);
		this.setIndependent_marker_orientation(independent_marker_orientation);
		this.setDescription(description);
	}
	
	public InteractiveMarkerControl(String name, Quaternion orientation, Short orientation_mode, Short interaction_mode,
			Boolean always_visible, Marker[] markers, Boolean independent_marker_orientation, String description) {
		this.setName(name);
		this.setOrientation(orientation);
		this.setOrientation_mode(orientation_mode);
		this.setInteraction_mode(interaction_mode);
		this.setAlways_visible(always_visible);
		this.setMarkers(markers);
		this.setIndependent_marker_orientation(independent_marker_orientation);
		this.setDescription(description);
	}

	public Short getINHERIT() {
		return INHERIT;
	}

	public void setINHERIT(Short iNHERIT) {
		INHERIT = iNHERIT;
	}

	public Short getFIXED() {
		return FIXED;
	}

	public void setFIXED(Short fIXED) {
		FIXED = fIXED;
	}

	public Short getVIEW_FACING() {
		return VIEW_FACING;
	}

	public void setVIEW_FACING(Short vIEW_FACING) {
		VIEW_FACING = vIEW_FACING;
	}

	public Short getNONE() {
		return NONE;
	}

	public void setNONE(Short nONE) {
		NONE = nONE;
	}

	public Short getMENU() {
		return MENU;
	}

	public void setMENU(Short mENU) {
		MENU = mENU;
	}

	public Short getBUTTON() {
		return BUTTON;
	}

	public void setBUTTON(Short bUTTON) {
		BUTTON = bUTTON;
	}

	public Short getMOVE_AXIS() {
		return MOVE_AXIS;
	}

	public void setMOVE_AXIS(Short mOVE_AXIS) {
		MOVE_AXIS = mOVE_AXIS;
	}

	public Short getMOVE_PLANE() {
		return MOVE_PLANE;
	}

	public void setMOVE_PLANE(Short mOVE_PLANE) {
		MOVE_PLANE = mOVE_PLANE;
	}

	public Short getROTATE_AXIS() {
		return ROTATE_AXIS;
	}

	public void setROTATE_AXIS(Short rOTATE_AXIS) {
		ROTATE_AXIS = rOTATE_AXIS;
	}

	public Short getMOVE_ROTATE() {
		return MOVE_ROTATE;
	}

	public void setMOVE_ROTATE(Short mOVE_ROTATE) {
		MOVE_ROTATE = mOVE_ROTATE;
	}

	public Short getMOVE_3D() {
		return MOVE_3D;
	}

	public void setMOVE_3D(Short mOVE_3D) {
		MOVE_3D = mOVE_3D;
	}

	public Short getROTATE_3D() {
		return ROTATE_3D;
	}

	public void setROTATE_3D(Short rOTATE_3D) {
		ROTATE_3D = rOTATE_3D;
	}

	public Short getMOVE_ROTATE_3D() {
		return MOVE_ROTATE_3D;
	}

	public void setMOVE_ROTATE_3D(Short mOVE_ROTATE_3D) {
		MOVE_ROTATE_3D = mOVE_ROTATE_3D;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Quaternion getOrientation() {
		return orientation;
	}

	public void setOrientation(Quaternion orientation) {
		this.orientation = orientation;
	}

	public Short getOrientation_mode() {
		return orientation_mode;
	}

	public void setOrientation_mode(Short orientation_mode) {
		this.orientation_mode = orientation_mode;
	}

	public Short getInteraction_mode() {
		return interaction_mode;
	}

	public void setInteraction_mode(Short interaction_mode) {
		this.interaction_mode = interaction_mode;
	}

	public Boolean getAlways_visible() {
		return always_visible;
	}

	public void setAlways_visible(Boolean always_visible) {
		this.always_visible = always_visible;
	}

	public Marker[] getMarkers() {
		return markers;
	}

	public void setMarkers(Marker[] markers) {
		this.markers = markers;
	}

	public Boolean getIndependent_marker_orientation() {
		return independent_marker_orientation;
	}

	public void setIndependent_marker_orientation(Boolean independent_marker_orientation) {
		this.independent_marker_orientation = independent_marker_orientation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
