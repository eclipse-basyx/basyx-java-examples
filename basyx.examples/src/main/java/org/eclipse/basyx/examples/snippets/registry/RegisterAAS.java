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
package org.eclipse.basyx.examples.snippets.registry;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;

/**
 * Snippet that showcases how to register a given AAS in a RegistryComponent
 * 
 * @author conradi
 *
 */
public class RegisterAAS {

	/**
	 * Registers a given AssetAdministrationShell in a registry.
	 * 
	 * @param aas
	 *            the AssetAdministrationShell to be registered
	 * @param aasEndpoint
	 *            the address where the AAS will be hosted (e.g.
	 *            http://localhost:8080/aasList/{aasId}/aas)
	 * @param registryServerURL
	 *            the address of the registry
	 */
	public static void registerAAS(IAssetAdministrationShell aas, String aasEndpoint, String registryServerURL) {

		// Create a proxy pointing to the registry
		AASRegistryProxy registryProxy = new AASRegistryProxy(registryServerURL);

		// Create a Descriptor for the AAS using the endpoint where it will be hosted
		AASDescriptor descriptor = new AASDescriptor(aas, aasEndpoint);

		// Register this Descriptor in the registry
		registryProxy.register(descriptor);
	}

}
