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

import static org.junit.Assert.fail;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.examples.snippets.AbstractSnippetTest;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.junit.Test;

/**
 * Test for the DeleteSubmodelFromAAS snippet
 * 
 * @author conradi
 *
 */
public class TestDeleteSubmodelFromAAS extends AbstractSnippetTest {

	@Test
	public void testDeleteSubmodel() {
		
		// Get the Identifier of the example AAS and Submodel
		IIdentifier aasIdentifier = new Identifier(IdentifierType.CUSTOM, AAS_ID);
		IIdentifier smIdentifier = new Identifier(IdentifierType.CUSTOM, SM_ID);
		
		// Get the AAS as ConnectedAAS
		ConnectedAssetAdministrationShellManager manager = getManager();
		IAssetAdministrationShell aas = manager.retrieveAAS(aasIdentifier);
		
		DeleteSubmodelFromAAS.deleteSubmodelFromAAS(smIdentifier, aas);
		
		// Try to retrieve deleted Submodel; should throw ResourceNotFoundException
		try {
			aas.getSubmodel(smIdentifier);
			fail();
		} catch (ResourceNotFoundException e) {
		}
		
	}
	
}
