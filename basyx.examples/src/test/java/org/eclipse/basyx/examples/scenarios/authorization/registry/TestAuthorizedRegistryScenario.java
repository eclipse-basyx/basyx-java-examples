/*******************************************************************************
 * Copyright (C) 2022 the Eclipse BaSyx Authors
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
package org.eclipse.basyx.examples.scenarios.authorization.registry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.api.IAASRegistry;
import org.eclipse.basyx.examples.scenarios.authorization.AuthorizationProvider;
import org.eclipse.basyx.examples.scenarios.authorization.AuthorizedComponentFactory;
import org.eclipse.basyx.examples.scenarios.authorization.exception.RealmDeletionException;
import org.eclipse.basyx.extensions.aas.manager.authorized.AuthorizedConnectedAASManager;
import org.eclipse.basyx.extensions.aas.registration.authorization.AuthorizedAASRegistryProxy;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.junit.AfterClass;
import org.junit.Assert;
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
		AuthorizedConnectedAASManager manager = getAuthorizedConnectedAASManager();

		IAssetAdministrationShell aas = manager.retrieveAAS(AuthorizedRegistryScenario.aasIdentifier);

		Assert.assertEquals(AuthorizedComponentFactory.AAS_ID_SHORT, aas.getIdShort());
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

		for (SubmodelDescriptor smDescriptor : aasDescriptor.getSubmodelDescriptors()) {
			if (smDescriptor.getIdShort().equals(AuthorizedComponentFactory.EDGESM_ID_SHORT)) {
				checkEndpoint(AuthorizedComponentFactory.EDGESM_ENDPOINT, smDescriptor.getEndpoints());
			} else if (smDescriptor.getIdShort().equals(AuthorizedComponentFactory.DOCUSM_ID_SHORT)) {
				checkEndpoint(AuthorizedComponentFactory.DOCUSM_ENDPOINT, smDescriptor.getEndpoints());
			} else {
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
		AuthorizedConnectedAASManager manager = getAuthorizedConnectedAASManager();

		IAssetAdministrationShell aas = manager.retrieveAAS(AuthorizedRegistryScenario.aasIdentifier);

		Map<String, ISubmodel> submodels = manager.retrieveSubmodels(aas.getIdentification());

		assertTrue(submodels.containsKey(AuthorizedComponentFactory.EDGESM_ID_SHORT) && submodels.containsKey(AuthorizedComponentFactory.DOCUSM_ID_SHORT));
	}

	@Test
	public void checkIfDocuSmHasCorrectIdShort() {
		AuthorizedConnectedAASManager manager = getAuthorizedConnectedAASManager();

		ISubmodel docuSM = getDocuSubmodel(manager);

		assertEquals(AuthorizedComponentFactory.DOCUSM_ID_SHORT, docuSM.getIdShort());
	}

	@Test
	public void checkIfCorrectNumberOfSubmodelElementsArePresentInDocuSm() {
		AuthorizedConnectedAASManager manager = getAuthorizedConnectedAASManager();

		ISubmodel docuSM = getDocuSubmodel(manager);

		assertEquals(1, docuSM.getSubmodelElements().size());
	}

	private ISubmodel getDocuSubmodel(AuthorizedConnectedAASManager manager) {
		ISubmodel docuSM = manager.retrieveSubmodel(AuthorizedRegistryScenario.aasIdentifier, AuthorizedRegistryScenario.docuSubmodelIdentifier);
		return docuSM;
	}

	@Test
	public void checkIfEdgeSmHasCorrectIdShort() {
		AuthorizedConnectedAASManager manager = getAuthorizedConnectedAASManager();

		ISubmodel edgeSM = getEdgeSubmodel(manager);

		assertEquals(AuthorizedComponentFactory.EDGESM_ID_SHORT, edgeSM.getIdShort());
	}

	@Test
	public void checkIfEdgeSmHasCorrectNumberOfSubmodelElements() {
		AuthorizedConnectedAASManager manager = getAuthorizedConnectedAASManager();

		ISubmodel edgeSM = getEdgeSubmodel(manager);

		assertEquals(4, edgeSM.getSubmodelElements().size());
	}

	@Test
	public void checkIfTheTemperatureIsSetProperlyAfterSettingTargetTemp() {
		AuthorizedConnectedAASManager manager = getAuthorizedConnectedAASManager();

		ISubmodel edgeSM = getEdgeSubmodel(manager);

		setTargetTemperatureToEdgeSubmodelElement();

		IProperty targetTemp = (IProperty) edgeSM.getSubmodelElement(AuthorizedComponentFactory.EDGESM_TARGET_TEMP_ID_SHORT);

		assertEquals(EXPECTED_TEMP, targetTemp.getValue());
	}

	private void setTargetTemperatureToEdgeSubmodelElement() {
		AuthorizedConnectedAASManager manager = getAuthorizedConnectedAASManager();

		ISubmodel edgeSM = getEdgeSubmodel(manager);

		IOperation setTempOperation = (IOperation) edgeSM.getSubmodelElement(AuthorizedComponentFactory.EDGESM_OP_SET_TEMP_ID_SHORT);
		setTempOperation.invokeSimple(EXPECTED_TEMP);
	}

	private ISubmodel getEdgeSubmodel(AuthorizedConnectedAASManager manager) {
		ISubmodel edgeSM = manager.retrieveSubmodel(AuthorizedRegistryScenario.aasIdentifier, AuthorizedRegistryScenario.edgeSubmodelIdentifier);
		return edgeSM;
	}

	private AuthorizedConnectedAASManager getAuthorizedConnectedAASManager() {
		AuthorizedConnectedAASManager manager = new AuthorizedConnectedAASManager(getRegistry(), authorizationProvider.getAuthorizationSupplier());
		return manager;
	}

	private void checkEndpoint(String expectedEndpointAddress, Collection<Map<String, Object>> actualEndpointCollection) {
		assertEquals(1, actualEndpointCollection.size());

		Map<String, Object> endpoint = new ArrayList<Map<String, Object>>(actualEndpointCollection).get(0);

		assertEquals(expectedEndpointAddress, endpoint.get(ENDPOINT_KEY));
	}
}
