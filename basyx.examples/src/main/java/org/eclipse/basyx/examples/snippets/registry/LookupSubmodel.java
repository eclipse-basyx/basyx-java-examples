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
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;

/**
 * Snippet that showcases how to look up a Submodel in a RegistryComponent
 * 
 * @author conradi
 *
 */
public class LookupSubmodel {

	/**
	 * Gets the Descriptor of the requested Submodel from a registry
	 * 
	 * @param smIdentifier the Identifier of the Submodel to be looked up in the registry
	 * @param aasIdentifier the Identifier of the AAS the Submodel belongs to
	 * @param registryServerURL the URL of the registry server
	 * @return the SubmodelDescriptor looked up in the registry
	 */
	public static SubmodelDescriptor lookupSubmodel(IIdentifier smIdentifier, IIdentifier aasIdentifier, String registryServerURL) {
		
		// Create a proxy pointing to the registry
		AASRegistryProxy registryProxy = new AASRegistryProxy(registryServerURL);
		
		// Lookup the Submodel in the registry
		return registryProxy.lookupSubmodel(aasIdentifier, smIdentifier);
	}
	
}
