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
