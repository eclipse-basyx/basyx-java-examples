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

import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.registry.RegistryComponent;
import org.eclipse.basyx.components.registry.configuration.BaSyxRegistryConfiguration;
import org.eclipse.basyx.components.registry.configuration.RegistryBackend;

/**
 * This class is used to startup a registry using the RegistryComponent
 * 
 * @author conradi
 *
 */
public class ExampleRegistryComponent {

	public static final String CONTEXT_PATH = "registry";

	private int port; 
	
	// Hold a reference to the server to be able to shut it down
	private RegistryComponent registry = null;
	
	
	public ExampleRegistryComponent(int port) {
		this.port = port;
	}
	
	
	public void startupRegistry() {	
		// Create a Configuration telling the component the port to use and the contextPath
		// The contextPath is attached to the address of the server "http://localhost:8080/{contextPath}"
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration(port, CONTEXT_PATH);
		
		// Create a RegistrationConfiguration telling the component to hold the whole registry in memory
		BaSyxRegistryConfiguration registryConfig = new BaSyxRegistryConfiguration(RegistryBackend.INMEMORY);
		
		registry = new RegistryComponent(contextConfig, registryConfig);
		
		// Startup the Registry
		registry.startComponent();
	}
	
	public void shutdownRegistry() {
		// If a registry was started -> stop it
		if(registry != null) {
			registry.stopComponent();
		}
	}
	
	public String getRegistryPath() {
		return "http://localhost:" + port + "/" + CONTEXT_PATH;
	}
}
