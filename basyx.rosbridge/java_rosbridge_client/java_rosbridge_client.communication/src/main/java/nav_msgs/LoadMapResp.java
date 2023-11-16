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

import java_rosbridge_client.core.utility.RosServiceResp;

public class LoadMapResp extends RosServiceResp{
	private Integer RESULT_SUCCESS=0;
	private Integer RESULT_MAP_DOES_NOT_EXIST=1;
	private Integer RESULT_INVALID_MAP_DATA=2;
	private Integer RESULT_INVALID_MAP_METADATA=3;
	private Integer RESULT_UNDEFINED_FAILURE=255;
	private OccupancyGrid map;
	private Integer result;
	
	public LoadMapResp() {
		
	}
	
    public LoadMapResp(Integer RESULT_SUCCESS, Integer RESULT_MAP_DOES_NOT_EXIST, Integer RESULT_INVALID_MAP_DATA, 
    		Integer RESULT_INVALID_MAP_METADATA, Integer RESULT_UNDEFINED_FAILURE, OccupancyGrid map, Integer result) {
		this.setRESULT_SUCCESS(RESULT_SUCCESS);
		this.setRESULT_MAP_DOES_NOT_EXIST(RESULT_MAP_DOES_NOT_EXIST);
		this.setRESULT_INVALID_MAP_DATA(RESULT_INVALID_MAP_DATA);
		this.setRESULT_INVALID_MAP_METADATA(RESULT_INVALID_MAP_METADATA);
		this.setRESULT_UNDEFINED_FAILURE(RESULT_UNDEFINED_FAILURE);
		this.setMap(map);
		this.setResult(result);
	}
    
    public LoadMapResp(OccupancyGrid map, Integer result) {
		this.setMap(map);
		this.setResult(result);
	}

	public Integer getRESULT_SUCCESS() {
		return RESULT_SUCCESS;
	}

	public void setRESULT_SUCCESS(Integer rESULT_SUCCESS) {
		RESULT_SUCCESS = rESULT_SUCCESS;
	}

	public Integer getRESULT_MAP_DOES_NOT_EXIST() {
		return RESULT_MAP_DOES_NOT_EXIST;
	}

	public void setRESULT_MAP_DOES_NOT_EXIST(Integer rESULT_MAP_DOES_NOT_EXIST) {
		RESULT_MAP_DOES_NOT_EXIST = rESULT_MAP_DOES_NOT_EXIST;
	}

	public Integer getRESULT_INVALID_MAP_DATA() {
		return RESULT_INVALID_MAP_DATA;
	}

	public void setRESULT_INVALID_MAP_DATA(Integer rESULT_INVALID_MAP_DATA) {
		RESULT_INVALID_MAP_DATA = rESULT_INVALID_MAP_DATA;
	}

	public Integer getRESULT_INVALID_MAP_METADATA() {
		return RESULT_INVALID_MAP_METADATA;
	}

	public void setRESULT_INVALID_MAP_METADATA(Integer rESULT_INVALID_MAP_METADATA) {
		RESULT_INVALID_MAP_METADATA = rESULT_INVALID_MAP_METADATA;
	}

	public Integer getRESULT_UNDEFINED_FAILURE() {
		return RESULT_UNDEFINED_FAILURE;
	}

	public void setRESULT_UNDEFINED_FAILURE(Integer rESULT_UNDEFINED_FAILURE) {
		RESULT_UNDEFINED_FAILURE = rESULT_UNDEFINED_FAILURE;
	}

	public OccupancyGrid getMap() {
		return map;
	}

	public void setMap(OccupancyGrid map) {
		this.map = map;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

}
