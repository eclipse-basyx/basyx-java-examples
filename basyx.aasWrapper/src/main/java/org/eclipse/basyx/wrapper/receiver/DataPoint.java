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

import java.util.Date;

/**
 * Represents a single datapoint for a property
 * 
 * @author espen
 *
 */
public class DataPoint {
    private Date timestamp;
    private Object value;

	public DataPoint(Object value) {
		this.timestamp = new Date();
		this.value = value;
	}

    public DataPoint(Date timestamp, Object value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
