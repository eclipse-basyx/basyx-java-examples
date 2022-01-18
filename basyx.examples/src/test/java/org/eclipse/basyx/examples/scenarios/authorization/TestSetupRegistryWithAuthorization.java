/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.api.IAASRegistry;
import org.eclipse.basyx.examples.scenarios.authorization.exception.AddClientException;
import org.eclipse.basyx.examples.scenarios.authorization.exception.RealmCreationException;
import org.eclipse.basyx.examples.scenarios.authorization.exception.RealmDeletionException;
import org.eclipse.basyx.extensions.aas.registration.authorization.AuthorizedAASRegistryProxy;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.json.simple.parser.ParseException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import javassist.NotFoundException;

/**
 * Tests the SetupRegistryWithAuthorization implementation.
 * 
 * @author danish
 *
 */
public class TestSetupRegistryWithAuthorization {
	private static final int EXPECTED_TEMP = 555;
	private static final String ENDPOINT_KEY = "address";

	private static SetupRegistryWithAuthorization scenario;
	
	private static AuthorizationProvider authorizationProvider = new AuthorizationProvider();
	
	@BeforeClass
	public static void startup() throws Exception {
		scenario = new SetupRegistryWithAuthorization();
	}
	
	@AfterClass
	public static void tearDown() throws RealmCreationException, IOException, NotFoundException, RealmDeletionException {
		authorizationProvider.deleteRealm();
		
		scenario.stop();
	}

	private IAASRegistry getRegistry() throws RealmCreationException, IOException, NotFoundException, 
						AddClientException, ParseException, RealmDeletionException {
		
		return new AuthorizedAASRegistryProxy(SetupRegistryWithAuthorization.REGISTRY_ENDPOINT, authorizationProvider.getAuthorizationSupplier());
	}
	
	@Test
	public void checkIfAasHasTheCorrectIdShort() throws RealmCreationException, IOException, 
				NotFoundException, AddClientException, ParseException, RealmDeletionException {
		
		ConnectedAssetAdministrationShellManager manager = getConnectedAssetAdministrationShellManager();
		
		IAssetAdministrationShell aas = manager.retrieveAAS(SetupRegistryWithAuthorization.aasIdentifier);
		
		assertEquals(ComponentBuilder.AAS_ID_SHORT, aas.getIdShort());
	}
	
	@Test
	public void checkTheSizeOfAasDescriptors() throws RealmCreationException, IOException, 
				NotFoundException, AddClientException, ParseException, RealmDeletionException {
		
		assertEquals(1, getAasDescriptors().size());
	}
	
	@Test
	public void checkIfAasDescriptorHasCorrectNumberOfSmDescriptors() throws RealmCreationException, IOException, 
				NotFoundException, AddClientException, ParseException, RealmDeletionException {
		
		AASDescriptor aasDescriptor = getAasDescriptor();
		
		checkEndpoint(ComponentBuilder.AAS_ENDPOINT, aasDescriptor.getEndpoints());
		
		assertEquals(2, aasDescriptor.getSubmodelDescriptors().size());
	}

	@Test
	public void checkIfExpectedIdShortAndEndpointArePresentInSmDescriptor() throws RealmCreationException, IOException, 
				NotFoundException, AddClientException, ParseException, RealmDeletionException {
		
		AASDescriptor aasDescriptor = getAasDescriptor();
		
		for(SubmodelDescriptor smDescriptor: aasDescriptor.getSubmodelDescriptors()) {
			if(smDescriptor.getIdShort().equals(ComponentBuilder.EDGESM_ID_SHORT)) {
				checkEndpoint(ComponentBuilder.EDGESM_ENDPOINT, smDescriptor.getEndpoints());
			} 
			else if(smDescriptor.getIdShort().equals(ComponentBuilder.DOCUSM_ID_SHORT)) {
				checkEndpoint(ComponentBuilder.DOCUSM_ENDPOINT, smDescriptor.getEndpoints());
			} 
			else {
				fail("The SMDescriptor with idShort " + smDescriptor.getIdShort() + " has an unexpected idShort");
			}
		}
	}

	private AASDescriptor getAasDescriptor() throws RealmCreationException, IOException, NotFoundException,
			AddClientException, ParseException, RealmDeletionException {
		
		AASDescriptor aasDescriptor = getAasDescriptors().get(0);
		
		return aasDescriptor;
	}

	private List<AASDescriptor> getAasDescriptors() throws RealmCreationException, IOException, NotFoundException,
			AddClientException, ParseException, RealmDeletionException {
		
		List<AASDescriptor> aasDescriptors = getRegistry().lookupAll();
		
		return aasDescriptors;
	}
	
	@Test
	public void checkIfEdgeSmAndDocuSmArePresentInSubmodel() throws RealmCreationException, IOException, NotFoundException, 
				AddClientException, ParseException, RealmDeletionException {
		
		ConnectedAssetAdministrationShellManager manager = getConnectedAssetAdministrationShellManager();
		
		IAssetAdministrationShell aas = manager.retrieveAAS(SetupRegistryWithAuthorization.aasIdentifier);
		
		Map<String, ISubmodel> submodels = manager.retrieveSubmodels(aas.getIdentification());
		
		assertTrue(submodels.containsKey(ComponentBuilder.EDGESM_ID_SHORT) && submodels.containsKey(ComponentBuilder.DOCUSM_ID_SHORT));
	}
	
	@Test
	public void checkIfDocuSmHasCorrectIdShort() throws RealmCreationException, IOException, NotFoundException, 
				AddClientException, ParseException, RealmDeletionException {
		
		ConnectedAssetAdministrationShellManager manager = getConnectedAssetAdministrationShellManager();
		
		ISubmodel docuSM = getDocuSubmodel(manager);
		
		assertEquals(ComponentBuilder.DOCUSM_ID_SHORT, docuSM.getIdShort());
	}
	
	@Test
	public void checkIfCorrectNumberOfSubmodelElementsArePresentInDocuSm() throws RealmCreationException, IOException, 
				NotFoundException, AddClientException, ParseException, RealmDeletionException {
		
		ConnectedAssetAdministrationShellManager manager = getConnectedAssetAdministrationShellManager();
		
		ISubmodel docuSM = getDocuSubmodel(manager);
		
		assertEquals(1, docuSM.getSubmodelElements().size());
	}
	
	private ISubmodel getDocuSubmodel(ConnectedAssetAdministrationShellManager manager) {
		ISubmodel docuSM = manager.retrieveSubmodel(SetupRegistryWithAuthorization.aasIdentifier, SetupRegistryWithAuthorization.docuSubmodelIdentifier);
		return docuSM;
	}
	
	@Test
	public void checkIfEdgeSmHasCorrectIdShort() throws RealmCreationException, IOException, NotFoundException, 
				AddClientException, ParseException, RealmDeletionException {
		
		ConnectedAssetAdministrationShellManager manager = getConnectedAssetAdministrationShellManager();

		ISubmodel edgeSM = getEdgeSubmodel(manager);
		
		assertEquals(ComponentBuilder.EDGESM_ID_SHORT, edgeSM.getIdShort());
	}
	
	@Test
	public void checkIfEdgeSmHasCorrectNumberOfSubmodelElements() throws RealmCreationException, IOException, 
				NotFoundException, AddClientException, ParseException, RealmDeletionException {
		
		ConnectedAssetAdministrationShellManager manager = getConnectedAssetAdministrationShellManager();

		ISubmodel edgeSM = getEdgeSubmodel(manager);
		
		assertEquals(4, edgeSM.getSubmodelElements().size());
	}
	
	@Test
	public void checkIfTheTemperatureIsSetProperlyAfterSettingTargetTemp() throws RealmCreationException, IOException, 
				NotFoundException, AddClientException, ParseException, RealmDeletionException {
		
		ConnectedAssetAdministrationShellManager manager = getConnectedAssetAdministrationShellManager();

		ISubmodel edgeSM = getEdgeSubmodel(manager);
		
		setTargetTemperatureToEdgeSubmodelElement();
		
		IProperty targetTemp = (IProperty) edgeSM.getSubmodelElement(ComponentBuilder.EDGESM_TARGET_TEMP_ID_SHORT);
		
		assertEquals(EXPECTED_TEMP, targetTemp.getValue());
	}
	
	private void setTargetTemperatureToEdgeSubmodelElement() throws RealmCreationException, IOException, NotFoundException, 
				 AddClientException, ParseException, RealmDeletionException {
		
		ConnectedAssetAdministrationShellManager manager = getConnectedAssetAdministrationShellManager();

		ISubmodel edgeSM = getEdgeSubmodel(manager);
		
		IOperation setTempOperation = (IOperation) edgeSM.getSubmodelElement(ComponentBuilder.EDGESM_OP_SET_TEMP_ID_SHORT);
		setTempOperation.invokeSimple(EXPECTED_TEMP);
	}
	
	private ISubmodel getEdgeSubmodel(ConnectedAssetAdministrationShellManager manager) {
		ISubmodel edgeSM = manager.retrieveSubmodel(SetupRegistryWithAuthorization.aasIdentifier, SetupRegistryWithAuthorization.edgeSubmodelIdentifier);
		return edgeSM;
	}
	
	private ConnectedAssetAdministrationShellManager getConnectedAssetAdministrationShellManager()
			throws RealmCreationException, IOException, NotFoundException, AddClientException, ParseException,
			RealmDeletionException {
		
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
