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
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;

/**
 * Snippet that showcases how to delete a Submodel from a server
 * 
 * @author conradi
 *
 */
public class DeleteSubmodelFromAAS {

	/**
	 * Removes a Submodel from an AAS
	 * 
	 * @param smIdentifier the Identifier of the Submodel to be deleted
	 * @param aas the AAS the Submodel belongs to
	 */
	public static void deleteSubmodelFromAAS(IIdentifier smIdentifier, IAssetAdministrationShell aas) {
		
		// Delete the Submodel from the AAS
		// Does NOT deregisters the Submodel
		// The DeregisterSubmodel snippet can be used to do that
		aas.removeSubmodel(smIdentifier);
	}
}
