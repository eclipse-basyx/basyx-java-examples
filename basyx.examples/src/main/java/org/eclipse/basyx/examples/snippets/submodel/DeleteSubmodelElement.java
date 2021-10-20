/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.snippets.submodel;

import org.eclipse.basyx.examples.snippets.manager.RetrieveSubmodelFromAAS;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;

/**
 * Snippet that showcases how to delete a SubmodelElement from a Submodel
 * 
 * @author conradi
 *
 */
public class DeleteSubmodelElement {

	/**
	 * Removes a SubmodelElement from a Submodel
	 * 
	 * @param elementId the Id of the Element to be deleted (can also be a path if the Element is in a Collection)
	 * @param smIdentifier the Identifier of the Submodel the Element belongs to
	 * @param aasIdentifier the Identifier of the AAS the Submodel belongs to
	 * @param registryServerURL the URL of the registry server (e.g. http://localhost:8080/registry)
	 */
	public static void deleteSubmodelElement(String elementId, IIdentifier smIdentifier, IIdentifier aasIdentifier, String registryServerURL) {
	
		// Retrieve the Submodel from the server as a ConnectedSubmodel
		ISubmodel submodel = RetrieveSubmodelFromAAS.retrieveSubmodelFromAAS(smIdentifier, aasIdentifier, registryServerURL);
		
		// Delete the Element from the Submodel
		submodel.deleteSubmodelElement(elementId);
	}
}
