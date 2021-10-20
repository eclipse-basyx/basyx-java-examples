/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
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
