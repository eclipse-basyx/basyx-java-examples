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

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.examples.snippets.AbstractSnippetTest;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.junit.Test;

/**
 * Test for the RetrieveRemoteAAS snippet
 * 
 * @author conradi
 *
 */
public class TestRetrieveAAS extends AbstractSnippetTest {
	
	@Test
	public void testRetrieveRemoteAAS() {
		
		// Get the Identifier of the example AAS
		IIdentifier aasIdentifier = new Identifier(IdentifierType.CUSTOM, AAS_ID);
		
		// Retrieve the AAS from the server
		IAssetAdministrationShell remoteAAS =
				RetrieveAAS.retrieveRemoteAAS(aasIdentifier, registryComponent.getRegistryPath());
		
		// Check if the retrieved AAS can be used correctly
		assertEquals(AAS_ID_SHORT, remoteAAS.getIdShort());
	}
}
