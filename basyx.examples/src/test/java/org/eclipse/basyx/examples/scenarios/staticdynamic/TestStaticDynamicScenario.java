/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * SPDX-License-Identifier: MIT
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
