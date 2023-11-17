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

import java.math.BigInteger;

import java_rosbridge_client.core.utility.RosMessage;

public class InteractiveMarkerUpdate extends RosMessage{
	
	private Short KEEP_ALIVE=0;
	private Short UPDATE=1;
	private String server_id;
	private BigInteger seq_num;
	private Short type;
	private InteractiveMarker[] markers;
	private InteractiveMarkerPose[] poses;
	private String[] erases;
	
	public InteractiveMarkerUpdate() {
		
	}
	
	public InteractiveMarkerUpdate(Short KEEP_ALIVE, Short UPDATE, String server_id, BigInteger seq_num,
			Short type, InteractiveMarker[] markers, InteractiveMarkerPose[] poses, String[] erases) {
		this.setKEEP_ALIVE(KEEP_ALIVE);
		this.setUPDATE(UPDATE);
		this.setServer_id(server_id);
		this.setSeq_num(seq_num);
		this.setType(type);
		this.setMarkers(markers);
		this.setPoses(poses);
		this.setErases(erases);
	}
	
	public InteractiveMarkerUpdate(String server_id, BigInteger seq_num, Short type, 
			InteractiveMarker[] markers, InteractiveMarkerPose[] poses, String[] erases) {
		this.setServer_id(server_id);
		this.setSeq_num(seq_num);
		this.setType(type);
		this.setMarkers(markers);
		this.setPoses(poses);
		this.setErases(erases);
	}

	public Short getKEEP_ALIVE() {
		return KEEP_ALIVE;
	}

	public void setKEEP_ALIVE(Short kEEP_ALIVE) {
		KEEP_ALIVE = kEEP_ALIVE;
	}

	public Short getUPDATE() {
		return UPDATE;
	}

	public void setUPDATE(Short uPDATE) {
		UPDATE = uPDATE;
	}

	public String getServer_id() {
		return server_id;
	}

	public void setServer_id(String server_id) {
		this.server_id = server_id;
	}

	public BigInteger getSeq_num() {
		return seq_num;
	}

	public void setSeq_num(BigInteger seq_num) {
		this.seq_num = seq_num;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public InteractiveMarker[] getMarkers() {
		return markers;
	}

	public void setMarkers(InteractiveMarker[] markers) {
		this.markers = markers;
	}

	public InteractiveMarkerPose[] getPoses() {
		return poses;
	}

	public void setPoses(InteractiveMarkerPose[] poses) {
		this.poses = poses;
	}

	public String[] getErases() {
		return erases;
	}

	public void setErases(String[] erases) {
		this.erases = erases;
	}

}
