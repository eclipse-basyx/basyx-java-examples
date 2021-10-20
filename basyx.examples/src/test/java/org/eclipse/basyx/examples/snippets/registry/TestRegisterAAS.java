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

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.examples.snippets.AbstractSnippetTest;
import org.eclipse.basyx.examples.support.ExampleComponentBuilder;
import org.junit.Test;

/**
 * Test for the RegisterAAS snippet
 * 
 * @author conradi
 *
 */
public class TestRegisterAAS extends AbstractSnippetTest {
	
	protected static final String NEW_AAS_ID_SHORT = "aasIdShort_New";
	protected static final String NEW_AAS_ID = "aasId_New";
	protected static final String NEW_AAS_ENDPOINT = "http://localhost:8080/aasComponent/shells/" + NEW_AAS_ID + "/aas";
	
	@Test
	public void testRegisterAAS() {
		
		// Get the example AAS
		AssetAdministrationShell aas = ExampleComponentBuilder.buildExampleAAS(NEW_AAS_ID_SHORT, NEW_AAS_ID);
		
		// Register this AAS
		RegisterAAS.registerAAS(aas, NEW_AAS_ENDPOINT, registryComponent.getRegistryPath());
		
		// Check if the AAS was correctly registered
		AASRegistryProxy registryProxy = new AASRegistryProxy(registryComponent.getRegistryPath());
		AASDescriptor descriptor = registryProxy.lookupAAS(aas.getIdentification());
		assertEquals(NEW_AAS_ENDPOINT, descriptor.getFirstEndpoint());
		
	}
	
}
