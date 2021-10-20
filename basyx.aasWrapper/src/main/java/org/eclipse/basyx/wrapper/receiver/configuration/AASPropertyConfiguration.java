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
