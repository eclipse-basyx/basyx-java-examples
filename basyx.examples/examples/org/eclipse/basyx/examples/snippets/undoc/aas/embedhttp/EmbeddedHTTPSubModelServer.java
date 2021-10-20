/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.snippets.undoc.aas.embedhttp;

import java.util.HashMap;

import org.eclipse.basyx.vab.core.IModelProvider;





/**
 * Implements an embedded HTTP server that hosts BaSys sub models
 * 
 * @author kuhn
 *
 */
public class EmbeddedHTTPSubmodelServer extends EmbeddedHTTPServer {

	
	/**
	 * Constructor
	 */
	public EmbeddedHTTPSubmodelServer(String context, IModelProvider provider) {
		// Invoke base constructor
		super(context, provider);

		// Prepare model provider for hosting a sub model
		try {
			provider.createValue("", new HashMap<String, Object>());
			provider.createValue("aas", new HashMap<String, Object>());
			provider.createValue("aas/submodels", new HashMap<String, Object>());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

