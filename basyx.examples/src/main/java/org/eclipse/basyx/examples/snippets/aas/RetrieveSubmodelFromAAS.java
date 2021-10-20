/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.snippets.aas;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;


/**
 * This snippet showcases how to retrieve a Submodel from an AAS
 * 
 * @author conradi
 *
 */
public class RetrieveSubmodelFromAAS {

	/**
	 * Gets a Submodel from an AAS
	 * 
	 * @param smIdentifier the Identifier of the requested Submodel
	 * @return the requested Submodel
	 */
	public static ISubmodel retrieveSubmodelFromAAS(IIdentifier smIdentifier, IAssetAdministrationShell aas) {
		// Get the requested Submodel from the AAS
		ISubmodel submodel = aas.getSubmodel(smIdentifier);
		return submodel;
	}
	
}
