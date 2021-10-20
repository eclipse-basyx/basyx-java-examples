/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.snippets.registry;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.examples.snippets.AbstractSnippetTest;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.junit.Test;

/**
 * Test for the LookupSubmodel snippet
 * 
 * @author conradi
 *
 */
public class TestLookupSubmodel extends AbstractSnippetTest {

	@Test
	public void testLookupSubmodel() {
		
		// Get the Identifiers of the AAS and Submodel
		IIdentifier aasIdentifier = new Identifier(IdentifierType.CUSTOM, AAS_ID);
		IIdentifier smIdentifier = new Identifier(IdentifierType.CUSTOM, SM_ID);
		
		// Lookup the Submodel in the registry
		SubmodelDescriptor descriptor = LookupSubmodel.lookupSubmodel(
				smIdentifier, aasIdentifier, registryComponent.getRegistryPath());
		
		// Check if the returned Descriptor is as expected
		assertEquals(SM_ENDPOINT, descriptor.getFirstEndpoint());
		
	}
	
}
