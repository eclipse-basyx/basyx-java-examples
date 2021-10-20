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
import org.eclipse.basyx.submodel.metamodel.map.Submodel;

/**
 * This snippet showcases how to add a Submodel to an AAS,
 * that already exists on a server.
 * It uses the AASManager
 * 
 * @author conradi
 *
 */
public class AddSubmodelToAAS {

	/**
	 * Adds a Submodel to an AAS and registers it
	 * 
	 * @param submodel the Submodel to be added
	 * @param aasIdentifier the Identifier of the AAS the Submodel should be added to
	 * @param registryServerURL the URL of the registry server
	 */
	public static void addSubmodelToAAS(Submodel submodel, IIdentifier aasIdentifier, String registryServerURL) {

		// Create a proxy pointing to the registry server
		AASRegistryProxy registryProxy = new AASRegistryProxy(registryServerURL);
		
		// Create a ConnectedAASManager using the registryProxy as its registry
		ConnectedAssetAdministrationShellManager manager =
				new ConnectedAssetAdministrationShellManager(registryProxy);
		
		// Add the submodel to the AAS using the ConnectedAASManager
		// The manager pushes the submodel to the server and registers it
		// For this to work, the Identification of the Submodel has to be set
		manager.createSubmodel(aasIdentifier, submodel);
		
	}
	
}
