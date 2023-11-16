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
import std_msgs.Header;

public class NavSatFix extends RosMessage {
	private Header header;
	private NavSatStatus status;
	private Double latitude;
	private Double longitude;
	private Double altitude;
	private Double[] position_covariance;
	
	private Integer COVARIANCE_TYPE_UNKNOWN = 0;
	private Integer COVARIANCE_TYPE_APPROXIMATED = 1;
	private Integer COVARIANCE_TYPE_DIAGONAL_KNOWN = 2;
	private Integer COVARIANCE_TYPE_KNOWN = 3;
	
	private Integer position_covariance_type;
	
	public NavSatFix() {
		
	}

	public NavSatFix(Header header, NavSatStatus status, Double latitude, Double longitude, Double altitude,
			Double[] position_covariance, Integer cOVARIANCE_TYPE_UNKNOWN, Integer cOVARIANCE_TYPE_APPROXIMATED,
			Integer cOVARIANCE_TYPE_DIAGONAL_KNOWN, Integer cOVARIANCE_TYPE_KNOWN, Integer position_covariance_type) {
		this.header = header;
		this.status = status;
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.position_covariance = position_covariance;
		this.COVARIANCE_TYPE_UNKNOWN = cOVARIANCE_TYPE_UNKNOWN;
		this.COVARIANCE_TYPE_APPROXIMATED = cOVARIANCE_TYPE_APPROXIMATED;
		this.COVARIANCE_TYPE_DIAGONAL_KNOWN = cOVARIANCE_TYPE_DIAGONAL_KNOWN;
		this.COVARIANCE_TYPE_KNOWN = cOVARIANCE_TYPE_KNOWN;
		this.position_covariance_type = position_covariance_type;
	}
	
	public NavSatFix(Header header, NavSatStatus status, Double latitude, Double longitude, Double altitude,
			Double[] position_covariance, Integer position_covariance_type) {
		this.header = header;
		this.status = status;
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.position_covariance = position_covariance;
		this.position_covariance_type = position_covariance_type;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public NavSatStatus getStatus() {
		return status;
	}

	public void setStatus(NavSatStatus status) {
		this.status = status;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getAltitude() {
		return altitude;
	}

	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}

	public Double[] getPosition_covariance() {
		return position_covariance;
	}

	public void setPosition_covariance(Double[] position_covariance) {
		this.position_covariance = position_covariance;
	}

	public Integer getCOVARIANCE_TYPE_UNKNOWN() {
		return COVARIANCE_TYPE_UNKNOWN;
	}

	public void setCOVARIANCE_TYPE_UNKNOWN(Integer cOVARIANCE_TYPE_UNKNOWN) {
		COVARIANCE_TYPE_UNKNOWN = cOVARIANCE_TYPE_UNKNOWN;
	}

	public Integer getCOVARIANCE_TYPE_APPROXIMATED() {
		return COVARIANCE_TYPE_APPROXIMATED;
	}

	public void setCOVARIANCE_TYPE_APPROXIMATED(Integer cOVARIANCE_TYPE_APPROXIMATED) {
		COVARIANCE_TYPE_APPROXIMATED = cOVARIANCE_TYPE_APPROXIMATED;
	}

	public Integer getCOVARIANCE_TYPE_DIAGONAL_KNOWN() {
		return COVARIANCE_TYPE_DIAGONAL_KNOWN;
	}

	public void setCOVARIANCE_TYPE_DIAGONAL_KNOWN(Integer cOVARIANCE_TYPE_DIAGONAL_KNOWN) {
		COVARIANCE_TYPE_DIAGONAL_KNOWN = cOVARIANCE_TYPE_DIAGONAL_KNOWN;
	}

	public Integer getCOVARIANCE_TYPE_KNOWN() {
		return COVARIANCE_TYPE_KNOWN;
	}

	public void setCOVARIANCE_TYPE_KNOWN(Integer cOVARIANCE_TYPE_KNOWN) {
		COVARIANCE_TYPE_KNOWN = cOVARIANCE_TYPE_KNOWN;
	}

	public Integer getPosition_covariance_type() {
		return position_covariance_type;
	}

	public void setPosition_covariance_type(Integer position_covariance_type) {
		this.position_covariance_type = position_covariance_type;
	}
	
	
	
	
	
}
