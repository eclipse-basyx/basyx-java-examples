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

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;

/**
 * Snippet that showcases how to look up an AAS in a RegistryComponent
 * 
 * @author conradi
 *
 */
public class LookupAAS {

	/**
	 * Gets the Descriptor of the requested AAS from a registry
	 * 
	 * @param aasIdentifier the Identifier of the AAS to be looked up in the registry
	 * @param registryServerURL the URL of the registry server
	 * @return the AASDescriptor looked up in the registry
	 */
	public static AASDescriptor lookupAAS(IIdentifier aasIdentifier, String registryServerURL) {
		
		// Create a proxy pointing to the registry
		AASRegistryProxy registryProxy = new AASRegistryProxy(registryServerURL);
		
		// Lookup the AAS in the registry
		return registryProxy.lookupAAS(aasIdentifier);
	}
	
}
