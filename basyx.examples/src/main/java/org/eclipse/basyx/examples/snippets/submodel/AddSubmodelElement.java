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
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;

/**
 * This snippet showcases how add to a SubmodelElement to a Submodel,
 * that already exists on a server
 * 
 * @author conradi
 *
 */
public class AddSubmodelElement {

	/**
	 * Adds a SubmodelElement to a remote Submodel
	 * 
	 * @param smElement the SubmodelElement to be added
	 * @param smIdentifier the Identifier of the Submodel the element should be added to
	 * @param aasIdentifier the Identifier of the AAS the Submodel belongs to
	 * @param registryServerURL the URL of the registry server
	 */
	public static void addSubmodelElement(SubmodelElement smElement, IIdentifier smIdentifier, IIdentifier aasIdentifier, String registryServerURL) {

		// Get the ConnectedSubmodel
		ISubmodel submodel = RetrieveSubmodelFromAAS.retrieveSubmodelFromAAS(smIdentifier, aasIdentifier, registryServerURL);
		
		// Add the element to it
		submodel.addSubmodelElement(smElement);
		
	}

}
