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
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;

/**
 * Snippet that showcases how to delete a Submodel from a server using the AASManager
 * 
 * @author conradi
 *
 */
public class DeleteSubmodelFromAAS {

	/**
	 * Removes a Submodel from an AAS
	 * 
	 * @param smIdentifier the Identifier of the Submodel to be deleted
	 * @param aasIdentifier the Identifier of the AAS the Submodel belongs to
	 * @param registryServerURL the URL of the registry server (e.g. http://localhost:8080/registry)
	 */
	public static void deleteSubmodelFromAAS(IIdentifier smIdentifier, IIdentifier aasIdentifier, String registryServerURL) {
	
		// Create a proxy pointing to the registry server
		AASRegistryProxy registryProxy = new AASRegistryProxy(registryServerURL);
		
		// Create a ConnectedAASManager using the registryProxy as its registry
		ConnectedAssetAdministrationShellManager manager =
				new ConnectedAssetAdministrationShellManager(registryProxy);
		
		// Delete the Submodel
		// Automatically deregisters it
		manager.deleteSubmodel(aasIdentifier, smIdentifier);
	}
}
