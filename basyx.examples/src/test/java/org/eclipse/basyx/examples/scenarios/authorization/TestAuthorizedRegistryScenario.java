/*******************************************************************************
 * Copyright (C) 2022 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.scenarios.authorization;

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
import org.eclipse.basyx.examples.scenarios.authorization.exception.RealmDeletionException;
import org.eclipse.basyx.extensions.aas.registration.authorization.AuthorizedAASRegistryProxy;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the AuthorizedRegistryScenario implementation.
 * 
 * @author danish
 *
 */
public class TestAuthorizedRegistryScenario {
	private static final int EXPECTED_TEMP = 555;
	private static final String ENDPOINT_KEY = "address";

	private static AuthorizedRegistryScenario scenario;
	
	private static AuthorizationProvider authorizationProvider = new AuthorizationProvider();
	
	@BeforeClass
	public static void startup() {
		scenario = new AuthorizedRegistryScenario();
	}
	
	@AfterClass
	public static void tearDown() throws RealmDeletionException {
		authorizationProvider.deleteRealm();
		
		scenario.stop();
	}

	private IAASRegistry getRegistry() {
		return new AuthorizedAASRegistryProxy(AuthorizedRegistryScenario.REGISTRY_ENDPOINT, authorizationProvider.getAuthorizationSupplier());
	}
	
	@Test
	public void checkIfAasHasTheCorrectIdShort() {
		ConnectedAssetAdministrationShellManager manager = getConnectedAssetAdministrationShellManager();
		
		IAssetAdministrationShell aas = manager.retrieveAAS(AuthorizedRegistryScenario.aasIdentifier);
		
		assertEquals(AuthorizedComponentFactory.AAS_ID_SHORT, aas.getIdShort());
	}
	
	@Test
	public void checkTheSizeOfAasDescriptors() {
		assertEquals(1, getAasDescriptors().size());
	}
	
	@Test
	public void checkIfAasDescriptorHasCorrectNumberOfSmDescriptors() {
		AASDescriptor aasDescriptor = getAasDescriptor();
		
		checkEndpoint(AuthorizedComponentFactory.AAS_ENDPOINT, aasDescriptor.getEndpoints());
		
		assertEquals(2, aasDescriptor.getSubmodelDescriptors().size());
	}

	@Test
	public void checkIfExpectedIdShortAndEndpointArePresentInSmDescriptor() {
		AASDescriptor aasDescriptor = getAasDescriptor();
		
		for(SubmodelDescriptor smDescriptor: aasDescriptor.getSubmodelDescriptors()) {
			if(smDescriptor.getIdShort().equals(AuthorizedComponentFactory.EDGESM_ID_SHORT)) {
				checkEndpoint(AuthorizedComponentFactory.EDGESM_ENDPOINT, smDescriptor.getEndpoints());
			} 
			else if(smDescriptor.getIdShort().equals(AuthorizedComponentFactory.DOCUSM_ID_SHORT)) {
				checkEndpoint(AuthorizedComponentFactory.DOCUSM_ENDPOINT, smDescriptor.getEndpoints());
			} 
			else {
				fail("The SMDescriptor with idShort " + smDescriptor.getIdShort() + " has an unexpected idShort");
			}
		}
	}

	private AASDescriptor getAasDescriptor() {
		AASDescriptor aasDescriptor = getAasDescriptors().get(0);
		
		return aasDescriptor;
	}

	private List<AASDescriptor> getAasDescriptors() {
		List<AASDescriptor> aasDescriptors = getRegistry().lookupAll();
		
		return aasDescriptors;
	}
	
	@Test
	public void checkIfEdgeSmAndDocuSmArePresentInSubmodel() {
		ConnectedAssetAdministrationShellManager manager = getConnectedAssetAdministrationShellManager();
		
		IAssetAdministrationShell aas = manager.retrieveAAS(AuthorizedRegistryScenario.aasIdentifier);
		
		Map<String, ISubmodel> submodels = manager.retrieveSubmodels(aas.getIdentification());
		
		assertTrue(submodels.containsKey(AuthorizedComponentFactory.EDGESM_ID_SHORT) && submodels.containsKey(AuthorizedComponentFactory.DOCUSM_ID_SHORT));
	}
	
	@Test
	public void checkIfDocuSmHasCorrectIdShort() {
		ConnectedAssetAdministrationShellManager manager = getConnectedAssetAdministrationShellManager();
		
		ISubmodel docuSM = getDocuSubmodel(manager);
		
		assertEquals(AuthorizedComponentFactory.DOCUSM_ID_SHORT, docuSM.getIdShort());
	}
	
	@Test
	public void checkIfCorrectNumberOfSubmodelElementsArePresentInDocuSm() {
		ConnectedAssetAdministrationShellManager manager = getConnectedAssetAdministrationShellManager();
		
		ISubmodel docuSM = getDocuSubmodel(manager);
		
		assertEquals(1, docuSM.getSubmodelElements().size());
	}
	
	private ISubmodel getDocuSubmodel(ConnectedAssetAdministrationShellManager manager) {
		ISubmodel docuSM = manager.retrieveSubmodel(AuthorizedRegistryScenario.aasIdentifier, AuthorizedRegistryScenario.docuSubmodelIdentifier);
		return docuSM;
	}
	
	@Test
	public void checkIfEdgeSmHasCorrectIdShort() {
		ConnectedAssetAdministrationShellManager manager = getConnectedAssetAdministrationShellManager();

		ISubmodel edgeSM = getEdgeSubmodel(manager);
		
		assertEquals(AuthorizedComponentFactory.EDGESM_ID_SHORT, edgeSM.getIdShort());
	}
	
	@Test
	public void checkIfEdgeSmHasCorrectNumberOfSubmodelElements() {
		ConnectedAssetAdministrationShellManager manager = getConnectedAssetAdministrationShellManager();

		ISubmodel edgeSM = getEdgeSubmodel(manager);
		
		assertEquals(4, edgeSM.getSubmodelElements().size());
	}
	
	@Test
	public void checkIfTheTemperatureIsSetProperlyAfterSettingTargetTemp() {
		ConnectedAssetAdministrationShellManager manager = getConnectedAssetAdministrationShellManager();

		ISubmodel edgeSM = getEdgeSubmodel(manager);
		
		setTargetTemperatureToEdgeSubmodelElement();
		
		IProperty targetTemp = (IProperty) edgeSM.getSubmodelElement(AuthorizedComponentFactory.EDGESM_TARGET_TEMP_ID_SHORT);
		
		assertEquals(EXPECTED_TEMP, targetTemp.getValue());
	}
	
	private void setTargetTemperatureToEdgeSubmodelElement() {
		ConnectedAssetAdministrationShellManager manager = getConnectedAssetAdministrationShellManager();

		ISubmodel edgeSM = getEdgeSubmodel(manager);
		
		IOperation setTempOperation = (IOperation) edgeSM.getSubmodelElement(AuthorizedComponentFactory.EDGESM_OP_SET_TEMP_ID_SHORT);
		setTempOperation.invokeSimple(EXPECTED_TEMP);
	}
	
	private ISubmodel getEdgeSubmodel(ConnectedAssetAdministrationShellManager manager) {
		ISubmodel edgeSM = manager.retrieveSubmodel(AuthorizedRegistryScenario.aasIdentifier, AuthorizedRegistryScenario.edgeSubmodelIdentifier);
		return edgeSM;
	}
	
	private ConnectedAssetAdministrationShellManager getConnectedAssetAdministrationShellManager() {
		ConnectedAssetAdministrationShellManager manager =
				new ConnectedAssetAdministrationShellManager(getRegistry());
		
		return manager;
	}
	
	private void checkEndpoint(String expectedEndpointAddress, Collection<Map<String, Object>> actualEndpointCollection) {
		assertEquals(1, actualEndpointCollection.size());
		
		Map<String, Object> endpoint = new ArrayList<Map<String, Object>>(actualEndpointCollection).get(0);
		
		assertEquals(expectedEndpointAddress, endpoint.get(ENDPOINT_KEY));
	}
}
