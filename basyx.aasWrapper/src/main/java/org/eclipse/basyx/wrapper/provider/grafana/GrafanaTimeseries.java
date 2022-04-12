/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
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
package org.eclipse.basyx.wrapper.provider.grafana;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.wrapper.receiver.PropertyResult;

/**
 * Conversion class for Grafana timeseries response
 * 
 * @author espen
 *
 */
public class GrafanaTimeseries extends ArrayList<Object> {
	private static final long serialVersionUID = 1L;
	public GrafanaTimeseries() {
		super();
	}

	public void addTarget(String idShort, PropertyResult result) {
		Map<String, Object> targetData = new HashMap<>();
		
		List<Object> values = result.getData();
		List<String> timestamps = result.getTimestamps();
		List<List<Object>> dataPoints = new ArrayList<>(values.size());
		for ( int i = 0; i < values.size(); i++ ) {
			List<Object> entry = new ArrayList<>(2);
			entry.add(values.get(i));
			try {
				Date date = PropertyResult.DATE_FORMAT.parse(timestamps.get(i));
				long ms = date.getTime();
				entry.add(ms);
			} catch (ParseException e) {
				e.printStackTrace();
				break;
			}
			// entry.add(Integer.parseInt(timestamps.get(i))); // add string formatted timestamp
			dataPoints.add(entry);
		}
		targetData.put("target", idShort);
		targetData.put("datapoints", dataPoints);
		this.add(targetData);
	}
}
