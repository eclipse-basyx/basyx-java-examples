/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.snippets.manager;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;

/**
 * This snippet showcases how to retrieve an AAS from a server using the AASManager
 * 
 * @author conradi
 *
 */
public class RetrieveAAS {

	/**
	 * Get an AAS from a server
	 * 
	 * @param aasIdentifier the Identifier of the requested AAS
	 * @param registryServerURL the URL of the registry server
	 * @return The requested AAS as ConnectedAAS
	 */
	public static IAssetAdministrationShell retrieveRemoteAAS(IIdentifier aasIdentifier, String registryServerURL) {

		// Create a proxy pointing to the registry server
		AASRegistryProxy registryProxy = new AASRegistryProxy(registryServerURL);
		
		// Create a ConnectedAASManager using the registryProxy as its registry
		ConnectedAssetAdministrationShellManager manager =
				new ConnectedAssetAdministrationShellManager(registryProxy);
		
		// Get the requested AAS from the manager
		return manager.retrieveAAS(aasIdentifier);
	}
	
}
