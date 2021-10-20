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
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.examples.snippets.AbstractSnippetTest;
import org.eclipse.basyx.examples.support.ExampleComponentBuilder;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.junit.Test;

/**
 * Test for the RegisterSubmodel snippet
 * 
 * @author conradi
 *
 */
public class TestRegisterSubmodel extends AbstractSnippetTest {

	private static final String NEW_SM_ID_SHORT = "smIdShort_New";
	private static final String NEW_SM_ID = "smId_New";
	private static final String NEW_SM_ENDPOINT = "http://localhost:8080/aasComponent/" + NEW_SM_ID_SHORT + "/submodel";
	
	@Test
	public void testRegisterSubmodel() {
		
		// Get the example AAS and Submodel
		AssetAdministrationShell aas = ExampleComponentBuilder.buildExampleAAS(AAS_ID_SHORT, AAS_ID);
		Submodel sm = ExampleComponentBuilder.buildExampleSubmodel(NEW_SM_ID_SHORT, NEW_SM_ID);
		
		// Register this AAS
		RegisterAAS.registerAAS(aas, AAS_ENDPOINT, registryComponent.getRegistryPath());
		
		// Register this Submodel
		RegisterSubmodel.registerSubmodel(sm, NEW_SM_ENDPOINT, aas.getIdentification(), registryComponent.getRegistryPath());
		
		// Check if the Submodel was correctly registered
		AASRegistryProxy registryProxy = new AASRegistryProxy(registryComponent.getRegistryPath());
		SubmodelDescriptor descriptor = registryProxy.lookupSubmodel(aas.getIdentification(), sm.getIdentification());
		assertEquals(NEW_SM_ENDPOINT, descriptor.getFirstEndpoint());
		
	}
}
