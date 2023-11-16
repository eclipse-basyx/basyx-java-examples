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

public class InteractiveMarkerInit extends RosMessage{
	
	private String server_id;
	private BigInteger seq_num;
	private InteractiveMarker[] markers;
	
	public InteractiveMarkerInit() {
		
	}
	
	public InteractiveMarkerInit(String server_id, BigInteger seq_num, InteractiveMarker[] markers) {
		this.setServer_id(server_id);
		this.setSeq_num(seq_num);
		this.setMarkers(markers);
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

	public InteractiveMarker[] getMarkers() {
		return markers;
	}

	public void setMarkers(InteractiveMarker[] markers) {
		this.markers = markers;
	}
	
	

}
