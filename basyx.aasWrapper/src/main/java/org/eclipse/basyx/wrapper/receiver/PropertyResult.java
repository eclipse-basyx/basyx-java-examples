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
package org.eclipse.basyx.wrapper.receiver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a property result with multiple datapoints
 * 
 * @author espen
 *
 */
public class PropertyResult extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	public static final String CONTENT = "content";
	public static final String DATES = "timestamp";
	public static final String TITLE = "title";
	public static final String SUCCESS = "success";

	protected int maxValues = 10;
	protected List<Object> content = new ArrayList<>();
	// VAB can't serialize dates, yet
	protected List<String> timestamp = new ArrayList<>();

	public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

	public PropertyResult(String title, int maxValues) {
		this.maxValues = maxValues;
		put(CONTENT, content);
		put(DATES, timestamp);
		put(SUCCESS, true);
		put(TITLE, "Value of property '" + title + "'");
	}

	public PropertyResult(PropertyResult clone) {
		this.maxValues = clone.maxValues;
		content = new ArrayList<>(clone.getData());
		timestamp = new ArrayList<>(clone.getTimestamps());
		put(CONTENT, content);
		put(DATES, timestamp);
		put(SUCCESS, clone.getSuccess());
		put(TITLE, "Value of property '" + clone.getTitle() + "'");
	}

	public void setSuccess(boolean success) {
		put(SUCCESS, success);
	}

	public boolean getSuccess() {
		return (boolean) get(SUCCESS);
	}

	public void setTitle(String title) {
		put(TITLE, title);
	}

	public String getTitle() {
		return (String) get(TITLE);
	}

	public List<Object> getData() {
		return content;
	}

	public List<String> getTimestamps() {
		return timestamp;
	}

	public synchronized void addDataPoint(DataPoint value) {
		content.add(value.getValue());
		timestamp.add(DATE_FORMAT.format(value.getTimestamp()));
		while (content.size() > maxValues) {
			content.remove(0);
			timestamp.remove(0);
		}
	}
}
