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

public class NavSatStatus extends RosMessage{
	
	private Integer STATUS_NO_FIX = -1;
	private Integer STATUS_FIX = 0;
	private Integer STATUS_SBAS_FIX = 1;
	private Integer STATUS_GBAS_FIX = 2;
	
	private Integer status;

	
	private Integer SERVICE_GPS = 1;
	private Integer SERVICE_GLONASS = 2;
	private Integer SERVICE_COMPASS = 4;
	private Integer SERVICE_GALILEO = 8;

	private Integer service;
	
	public NavSatStatus() {
		
	}

	public NavSatStatus(Integer sTATUS_NO_FIX, Integer sTATUS_FIX, Integer sTATUS_SBAS_FIX, Integer sTATUS_GBAS_FIX,
			Integer status, Integer sERVICE_GPS, Integer sERVICE_GLONASS, Integer sERVICE_COMPASS,
			Integer sERVICE_GALILEO, Integer service) {
		this.STATUS_NO_FIX = sTATUS_NO_FIX;
		this.STATUS_FIX = sTATUS_FIX;
		this.STATUS_SBAS_FIX = sTATUS_SBAS_FIX;
		this.STATUS_GBAS_FIX = sTATUS_GBAS_FIX;
		this.status = status;
		this.SERVICE_GPS = sERVICE_GPS;
		this.SERVICE_GLONASS = sERVICE_GLONASS;
		this.SERVICE_COMPASS = sERVICE_COMPASS;
		this.SERVICE_GALILEO = sERVICE_GALILEO;
		this.service = service;
	}
	
	public NavSatStatus(Integer status, Integer service) {
		this.status = status;
		this.service = service;
	}

	public Integer getSTATUS_NO_FIX() {
		return STATUS_NO_FIX;
	}

	public void setSTATUS_NO_FIX(Integer sTATUS_NO_FIX) {
		STATUS_NO_FIX = sTATUS_NO_FIX;
	}

	public Integer getSTATUS_FIX() {
		return STATUS_FIX;
	}

	public void setSTATUS_FIX(Integer sTATUS_FIX) {
		STATUS_FIX = sTATUS_FIX;
	}

	public Integer getSTATUS_SBAS_FIX() {
		return STATUS_SBAS_FIX;
	}

	public void setSTATUS_SBAS_FIX(Integer sTATUS_SBAS_FIX) {
		STATUS_SBAS_FIX = sTATUS_SBAS_FIX;
	}

	public Integer getSTATUS_GBAS_FIX() {
		return STATUS_GBAS_FIX;
	}

	public void setSTATUS_GBAS_FIX(Integer sTATUS_GBAS_FIX) {
		STATUS_GBAS_FIX = sTATUS_GBAS_FIX;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSERVICE_GPS() {
		return SERVICE_GPS;
	}

	public void setSERVICE_GPS(Integer sERVICE_GPS) {
		SERVICE_GPS = sERVICE_GPS;
	}

	public Integer getSERVICE_GLONASS() {
		return SERVICE_GLONASS;
	}

	public void setSERVICE_GLONASS(Integer sERVICE_GLONASS) {
		SERVICE_GLONASS = sERVICE_GLONASS;
	}

	public Integer getSERVICE_COMPASS() {
		return SERVICE_COMPASS;
	}

	public void setSERVICE_COMPASS(Integer sERVICE_COMPASS) {
		SERVICE_COMPASS = sERVICE_COMPASS;
	}

	public Integer getSERVICE_GALILEO() {
		return SERVICE_GALILEO;
	}

	public void setSERVICE_GALILEO(Integer sERVICE_GALILEO) {
		SERVICE_GALILEO = sERVICE_GALILEO;
	}

	public Integer getService() {
		return service;
	}

	public void setService(Integer service) {
		this.service = service;
	}
	
	
	

}
