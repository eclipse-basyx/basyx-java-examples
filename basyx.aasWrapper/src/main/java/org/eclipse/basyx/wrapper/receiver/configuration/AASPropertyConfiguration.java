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

/**
 * Represents a single configuration for a property. The datasource for this
 * property is an AAS property.
 * 
 * @author espen
 *
 */
public class AASPropertyConfiguration extends PropertyConfiguration {
	private static final long serialVersionUID = 1L;

	public static final String TYPE = "AAS";

	// indices in the configuration file
	public static final String AAS = "aas";
	public static final String SUBMODEL = "submodel";
	public static final String SHORTID = "shortId";

	public AASPropertyConfiguration() {
		super();
	}

	public AASPropertyConfiguration(PropertyConfiguration config) {
		putAll(config);
	}

	public String getAAS() {
		return get(AAS);
	}

	public void setAAS(String aas) {
		put(AAS, aas);
	}

	public String getSubmodel() {
		return get(SUBMODEL);
	}

	public void setSubmodel(String submodel) {
		put(SUBMODEL, submodel);
	}

	public String getShortId() {
		return get(SHORTID);
	}

	public void setShortId(String shortId) {
		put(SHORTID, shortId);
	}
}
