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
