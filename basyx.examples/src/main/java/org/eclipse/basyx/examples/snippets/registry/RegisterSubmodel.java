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

import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;

/**
 * Snippet that showcases how to register a given Submodel in a
 * RegistryComponent
 * 
 * @author conradi
 *
 */
public class RegisterSubmodel {

	/**
	 * Registers a given Submodel in a registry.
	 * 
	 * @param submodel
	 *            the Submodel to be registered
	 * @param smEndpoint
	 *            the address where the SM will be hosted (e.g.
	 *            http://localhost:8080/aasList/{aasId}/aas/submodels/{smId})
	 * @param aasIdentifier
	 *            the Identifier of the AAS the Submodel should be registered to
	 * @param registryServerURL
	 *            the address of the registry
	 */
	public static void registerSubmodel(ISubmodel submodel, String smEndpoint, IIdentifier aasIdentifier, String registryServerURL) {

		// Create a proxy pointing to the registry
		AASRegistryProxy registryProxy = new AASRegistryProxy(registryServerURL);

		// Create a Descriptor for the sm using the endpoint where it will be hosted
		SubmodelDescriptor descriptor = new SubmodelDescriptor(submodel, smEndpoint);

		// Register this Descriptor in the registry
		registryProxy.register(aasIdentifier, descriptor);
	}

}
