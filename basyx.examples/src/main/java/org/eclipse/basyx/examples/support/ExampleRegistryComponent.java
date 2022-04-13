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
		// Create a Configuration telling the component the port to use and the
		// contextPath
		// The contextPath is attached to the address of the server
		// "http://localhost:8080/{contextPath}"
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration(port, CONTEXT_PATH);

		// Create a RegistrationConfiguration telling the component to hold the whole
		// registry in memory
		BaSyxRegistryConfiguration registryConfig = new BaSyxRegistryConfiguration(RegistryBackend.INMEMORY);

		registry = new RegistryComponent(contextConfig, registryConfig);

		// Startup the Registry
		registry.startComponent();
	}

	public void shutdownRegistry() {
		// If a registry was started -> stop it
		if (registry != null) {
			registry.stopComponent();
		}
	}

	public String getRegistryPath() {
		return "http://localhost:" + port + "/" + CONTEXT_PATH;
	}
}
