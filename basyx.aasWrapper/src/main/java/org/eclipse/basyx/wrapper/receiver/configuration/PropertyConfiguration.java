/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
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
