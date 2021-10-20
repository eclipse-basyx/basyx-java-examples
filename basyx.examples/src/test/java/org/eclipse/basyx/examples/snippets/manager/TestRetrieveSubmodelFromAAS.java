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

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.examples.snippets.AbstractSnippetTest;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.junit.Test;

/**
 * Test for the RetrieveSubmodelFromAAS snippet
 * 
 * @author conradi
 *
 */
public class TestRetrieveSubmodelFromAAS extends AbstractSnippetTest {

	
	@Test
	public void testRetrieveSubmodelFromAAS() {
		
		// Get the Identifiers of the AAS and Submodel
		IIdentifier aasIdentifier = new Identifier(IdentifierType.CUSTOM, AAS_ID);
		IIdentifier smIdentifier = new Identifier(IdentifierType.CUSTOM, SM_ID);
		
		// Get the Submodel from the server
		ISubmodel remoteSubmodel = RetrieveSubmodelFromAAS.retrieveSubmodelFromAAS(
				smIdentifier, aasIdentifier, registryComponent.getRegistryPath());
		
		// Check if the Submodel can be used correctly
		assertEquals(SM_ID_SHORT, remoteSubmodel.getIdShort());
	}
	
}
