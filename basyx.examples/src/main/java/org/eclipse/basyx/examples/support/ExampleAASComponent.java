/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.support;

import org.eclipse.basyx.aas.registration.api.IAASRegistry;
import org.eclipse.basyx.components.aas.AASServerComponent;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;

/**
 * This class is used to startup an AAS-Server using the AASServerComponent
 * 
 * @author conradi
 *
 */
public class ExampleAASComponent {

	public static final String CONTEXT_PATH = "aasComponent"; 
	
	private int port;
	
	private IAASRegistry registry;
	
	// Hold a reference to the server to be able to shut it down
	private AASServerComponent aasServer = null;
	
	
	public ExampleAASComponent(int port, IAASRegistry registry) {
		this.port = port;
		this.registry = registry;
	}
	
	public void startupAASServer() {
		// Create a Configuration telling the component the port to use and the contextPath
		// The contextPath is attached to the address of the server "http://localhost:8080/{contextPath}"
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration(port, CONTEXT_PATH);
		aasServer = new AASServerComponent(contextConfig);
		
		// Set the registry to be used by the server
		aasServer.setRegistry(registry);
		
		// Startup the AASServer
		aasServer.startComponent();
	}
	
	public void shutdownAASServer() {
		// If an AASServer was started -> stop it
		if(aasServer != null) {
			aasServer.stopComponent();
		}
	}
	
	public String getAASServerPath() {
		return "http://localhost:" + port + "/" + CONTEXT_PATH;
	}
}
