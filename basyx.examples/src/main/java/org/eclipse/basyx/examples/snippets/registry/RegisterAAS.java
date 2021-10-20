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
	 * @param aas the AssetAdministrationShell to be registered
	 * @param aasEndpoint the address where the AAS will be hosted (e.g. http://localhost:8080/aasList/{aasId}/aas)
	 * @param registryServerURL the address of the registry
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
