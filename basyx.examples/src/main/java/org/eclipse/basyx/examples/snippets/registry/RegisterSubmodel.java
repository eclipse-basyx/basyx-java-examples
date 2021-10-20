/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.snippets.registry;

import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;

/**
 * Snippet that showcases how to register a given Submodel in a RegistryComponent
 * 
 * @author conradi
 *
 */
public class RegisterSubmodel {

	/**
	 * Registers a given Submodel in a registry.
	 * 
	 * @param submodel the Submodel to be registered
	 * @param smEndpoint the address where the SM will be hosted (e.g. http://localhost:8080/aasList/{aasId}/aas/submodels/{smId})
	 * @param aasIdentifier the Identifier of the AAS the Submodel should be registered to
	 * @param registryServerURL the address of the registry
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
