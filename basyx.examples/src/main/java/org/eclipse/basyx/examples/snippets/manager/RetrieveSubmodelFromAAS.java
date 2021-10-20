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
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;


/**
 * This snippet showcases how to retrieve a Submodel
 * from an AAS on a server using the AASManager
 * 
 * @author conradi
 *
 */
public class RetrieveSubmodelFromAAS {

	/**
	 * Gets a Submodel from an AAS
	 * 
	 * @param smIdentifier the Identifier of the requested Submodel
	 * @param aasIdentifier the Identifier of the AAS the Submodel belongs to
	 * @param registryServerURL the URL of the registry server
	 * @return the requested Submodel as ConnectedSubmodel
	 */
	public static ISubmodel retrieveSubmodelFromAAS(IIdentifier smIdentifier, IIdentifier aasIdentifier, String registryServerURL) {

		// Create a proxy pointing to the registry server
		AASRegistryProxy registryProxy = new AASRegistryProxy(registryServerURL);
		
		// Create a ConnectedAASManager using the registryProxy as its registry
		ConnectedAssetAdministrationShellManager manager =
				new ConnectedAssetAdministrationShellManager(registryProxy);
		
		// Get the requested Submodel from the ConnectedAASManager using the Identifiers of the AAS and the SM
		return manager.retrieveSubmodel(aasIdentifier, smIdentifier);
	}
	
}
