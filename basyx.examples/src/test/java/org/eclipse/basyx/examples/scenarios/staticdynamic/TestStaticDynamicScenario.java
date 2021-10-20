/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.scenarios.staticdynamic;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test for StaticDynamicScenario
 * 
 * @author conradi
 *
 */
public class TestStaticDynamicScenario {

	private static StaticDynamicScenario scenario;
	
	@BeforeClass
	public static void startup() throws Exception {
		scenario = new StaticDynamicScenario();
	}
	
	@AfterClass
	public static void tearDown() {
		scenario.stop();
	}
	
	@Test
	public void checkDynamicSubmodel() throws Exception {
		
		AASRegistryProxy proxy = new AASRegistryProxy(StaticDynamicScenario.REGISTRY_URL);
		ConnectedAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(proxy);
		
		Identifier AASIdentifier = new Identifier(IdentifierType.IRI, StaticDynamicScenario.AAS_ID);
		
		Map<String, ISubmodel> submodels = manager.retrieveSubmodels(AASIdentifier);
		
		// The aas should contain 6 SMs
		assertEquals(6, submodels.size());
		
		ISubmodel staticSM = submodels.get(ExampleDynamicSubmodel.SM_ID_SHORT);
		
		// Get the Property from the Submodel
		IProperty smElement = (IProperty) staticSM.getSubmodelElements().get(ExampleDynamicSubmodel.PROPERTY_ID_SHORT);

		// Check if the Property contains the correct value
		assertEquals(ExampleDynamicSubmodel.PROPERTY_VALUE, smElement.getValue());
	}
}
