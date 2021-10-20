/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.contexts;

import org.eclipse.basyx.components.aas.servlet.AASAggregatorServlet;
import org.eclipse.basyx.components.registry.servlet.InMemoryRegistryServlet;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;

/**
 * BaSyx context that contains an Industrie 4.0 Servlet infrastructure <br>
 * - One in-memory AAS Server that receives and hosts asset administration
 * shells and submodels<br>
 * - One in-memory registry that manages AAS and sub model registrations<br>
 * 
 * @author kuhn, schnicke
 *
 */
public class BaSyxExamplesContext extends BaSyxContext {

	
	/**
	 * Version of serialized instance
	 */
	private static final long serialVersionUID = 1L;

	private static final String CONTEXT = "/basys.examples";
	
	private static final String AASSERVERFRAGMENT = "/Components/AasServer/";
	public static final String AASSERVERURL = CONTEXT + AASSERVERFRAGMENT;

	private static final String REGISTRYFRAGMENT = "/Components/Registry/";
	public static final String REGISTRYURL = CONTEXT + REGISTRYFRAGMENT;

	/**
	 * Constructor
	 */
	public BaSyxExamplesContext() {
		// Invoke base constructor to set up Tomcat server in basys.components context
		super(CONTEXT, "");
		
		// Define Servlet infrastucture
		addServletMapping(REGISTRYFRAGMENT + "*", new InMemoryRegistryServlet());
		addServletMapping(AASSERVERFRAGMENT + "*", new AASAggregatorServlet());
	}
}

