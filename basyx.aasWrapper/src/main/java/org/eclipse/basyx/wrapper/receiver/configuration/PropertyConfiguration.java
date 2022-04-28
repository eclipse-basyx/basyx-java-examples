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
package org.eclipse.basyx.wrapper.receiver.configuration;

import java.util.HashMap;
import java.util.StringJoiner;

/**
 * Represents a single configuration for a proxy property. 
 * 
 * @author espen
 *
 */
public class PropertyConfiguration extends HashMap<String, String> {
	private static final long serialVersionUID = 1L;

	// configuration indices
	public static final String INDEX = "properties";
	public static final String ID = "id";
	public static final String TYPE = "type";
	public static final String ACTIVE = "active";
	public static final String INTERVAL = "interval";
	public static final String MAX_VALUES = "maxValues";

	public boolean getActive() {
		return "true".equals(get(ACTIVE));
	}

	public void setActive(Boolean active) {
		put(ACTIVE, active.toString());
	}

	/**
	 * Get active update interval in ms
	 * 
	 * @return
	 */
	public int getInterval() {
		if (get(INTERVAL) != null) {
			return Integer.parseInt(get(INTERVAL));
		} else {
			return 0;
		}
	}

	public void setInterval(int interval) {
		put(INTERVAL, String.valueOf(interval));
	}

	public int getMaxValues() {
		if (get(MAX_VALUES) != null) {
			return Integer.parseInt(get(MAX_VALUES));
		} else {
			return 1;
		}
	}

	public void setMaxValues(int maxValues) {
		put(INTERVAL, String.valueOf(maxValues));
	}

	public String getId() {
		return get(ID);
	}

	public void setId(String id) {
		put(ID, id);
	}

	public String getType() {
		return get(TYPE);
	}

	public void setType(String type) {
		put(TYPE, type);
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(", ");
		entrySet().forEach((Entry<String, String> e) -> {
			joiner.add(e.getKey() + "=" + e.getValue());
		});
		return "{ " + joiner.toString() + " }";
	}
}
