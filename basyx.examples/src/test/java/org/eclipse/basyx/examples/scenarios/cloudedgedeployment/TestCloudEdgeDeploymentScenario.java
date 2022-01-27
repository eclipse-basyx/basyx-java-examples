/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.scenarios.cloudedgedeployment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.api.IAASRegistry;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestCloudEdgeDeploymentScenario {

	private static CloudEdgeDeploymentScenario scenario;
	
	@BeforeClass
	public static void startup() throws Exception {
		scenario = new CloudEdgeDeploymentScenario();
	}
	
	@AfterClass
	public static void tearDown() {
		scenario.stop();
	}

	private IAASRegistry getRegistry() {
		return new AASRegistryProxy(CloudEdgeDeploymentScenario.registryPath);
	}

	/**
	 * This tests if all the expected registry entries are present
	 * 
	 */
	@Test
	public void testRegistry() throws Exception {
		List<AASDescriptor> aasDescriptors = getRegistry().lookupAll();
		assertEquals(1, aasDescriptors.size());
		
		AASDescriptor aasDescriptor = aasDescriptors.get(0);
		
		// Check if aasDescriptor has the correct endpoint
		checkEndpoint(ComponentFactory.AAS_ENDPOINT, aasDescriptor.getEndpoints());
		
		// Check if aasDescriptor has the correct number of SMDescriptors
		assertEquals(2, aasDescriptor.getSubmodelDescriptors().size());
		
		// Iterate over the Collection of SMDescriptors and
		// test if both have the expected idShort and endpoint
		for(SubmodelDescriptor smDescriptor: aasDescriptor.getSubmodelDescriptors()) {
			if(smDescriptor.getIdShort().equals(ComponentFactory.EDGESM_ID_SHORT)) {
				checkEndpoint(ComponentFactory.EDGESM_ENDPOINT, smDescriptor.getEndpoints());
			} else if(smDescriptor.getIdShort().equals(ComponentFactory.DOCUSM_ID_SHORT)) {
				checkEndpoint(ComponentFactory.DOCUSM_ENDPOINT, smDescriptor.getEndpoints());
			} else {
				// There is a SMDescriptor with an unexpected idShort
				fail();
			}
		}
	}
	
	/**
	 * This tests if the AAS is retrievable, has the correct idShot and
	 * contains the expected Submodels 
	 * 
	 */
	@Test
	public void testAAS() throws Exception {
		ConnectedAssetAdministrationShellManager manager =
				new ConnectedAssetAdministrationShellManager(getRegistry());
		
		IAssetAdministrationShell aas = manager.retrieveAAS(scenario.aasIdentifier);
		
		// Check if it has the correct idShort
		assertEquals(ComponentFactory.AAS_ID_SHORT, aas.getIdShort());
		
		// Get the Submodels and check if both expected SMs are present 
		Map<String, ISubmodel> submodels = aas.getSubmodels();
		
		assertTrue(submodels.containsKey(ComponentFactory.EDGESM_ID_SHORT));
		assertTrue(submodels.containsKey(ComponentFactory.DOCUSM_ID_SHORT));
	}
	
	/**
	 * This test checks if the DocuSM is retrievable, has the correct idShort and
	 * contains the correct number of SubmodelElements
	 * 
	 */
	@Test
	public void testDocuSM() {
		ConnectedAssetAdministrationShellManager manager =
				new ConnectedAssetAdministrationShellManager(getRegistry());
		
		ISubmodel docuSM = manager.retrieveSubmodel(scenario.aasIdentifier, scenario.docuSmIdentifier);
		
		// Check if it has the correct idShort, and 1 SubmodelElement
		assertEquals(ComponentFactory.DOCUSM_ID_SHORT, docuSM.getIdShort());
		assertEquals(1, docuSM.getSubmodelElements().size());
	}
	
	/**
	 * This test checks if the EdgeSM is retrievable, has the correct idShort and
	 * contains the correct number of SubmodelElements
	 * 
	 */
	@Test
	public void testEdgeSM() {
		ConnectedAssetAdministrationShellManager manager =
				new ConnectedAssetAdministrationShellManager(getRegistry());
		
		ISubmodel edgeSM = manager.retrieveSubmodel(scenario.aasIdentifier, scenario.edgeSmIdentifier);
		
		// Check if it has the correct idShort, and 1 SubmodelElement
		assertEquals(ComponentFactory.EDGESM_ID_SHORT, edgeSM.getIdShort());
		assertEquals(4, edgeSM.getSubmodelElements().size());
		
		int expectedTemp = 555;
		
		// Execute Operation to set target temp
		IOperation setTempOp = (IOperation) edgeSM.getSubmodelElement(ComponentFactory.EDGESM_OP_SET_TEMP_ID_SHORT);
		setTempOp.invokeSimple(expectedTemp);
		
		// Check if target temp has expected value
		IProperty targetTemp = (IProperty) edgeSM.getSubmodelElement(ComponentFactory.EDGESM_TARGET_TEMP_ID_SHORT);
		assertEquals(expectedTemp, targetTemp.getValue());
	}
	
	/**
	 * This is a helper function used to check if a given endpoint is
	 * contained in a collection.
	 * 
	 * @param expected the expected endpoint address
	 * @param actual the endpoint collection
	 */
	private void checkEndpoint(String expected, Collection<Map<String, Object>> actual) {
		assertEquals(1, actual.size());
		Map<String, Object> endpoint = new ArrayList<Map<String, Object>>(actual).get(0);
		assertEquals(expected, endpoint.get("address"));
	}
}
