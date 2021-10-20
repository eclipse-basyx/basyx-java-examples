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

import static org.junit.Assert.fail;

import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.examples.snippets.AbstractSnippetTest;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.junit.Test;

/**
 * Test for the DeregisterAAS Snippet
 * 
 * @author conradi
 *
 */
public class TestDeregisterAAS extends AbstractSnippetTest {

	@Test
	public void testDeregisterAAS() {
		
		// Get the Identifier of the example AAS
		IIdentifier aasIdentifier = new Identifier(IdentifierType.CUSTOM, AAS_ID);
		
		DeregisterAAS.registerAAS(aasIdentifier, registryComponent.getRegistryPath());
		
		// Lookup the AAS in the registry
		AASRegistryProxy registry = new AASRegistryProxy(registryComponent.getRegistryPath());
		
		try {
			registry.lookupAAS(aasIdentifier);
			fail();
		} catch (ResourceNotFoundException e) {
		}	
	}
	
}
