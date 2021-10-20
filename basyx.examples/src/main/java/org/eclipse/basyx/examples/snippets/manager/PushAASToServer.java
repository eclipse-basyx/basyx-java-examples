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
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;


/**
 * This snippet showcases how to push an AAS to a server using the AASManager
 * 
 * @author conradi
 *
 */
public class PushAASToServer {
	
	/**
	 * Pushes the AAS to a server and registers it
	 * 
	 * @param aas the AssetAdministrationShell to be pushed to the server
	 * @param aasServerURL the URL of the aas server (e.g. http://localhost:8080/aasComponent)
	 * @param registryServerURL the URL of the registry server (e.g. http://localhost:8080/registry)
	 */
	public static void pushAAS(AssetAdministrationShell aas, String aasServerURL, String registryServerURL) {

		// Create a proxy pointing to the registry server
		AASRegistryProxy registryProxy = new AASRegistryProxy(registryServerURL);
		
		// Create a ConnectedAASManager using the registryProxy as its registry
		ConnectedAssetAdministrationShellManager manager =
				new ConnectedAssetAdministrationShellManager(registryProxy);
		
		// The ConnectedAASManager automatically pushes the given AAS
		// to the server to which the address was given
		// It also registers the AAS in the registry it got in its ctor
		manager.createAAS(aas, aasServerURL);
	}
}
