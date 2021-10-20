/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.snippets.aas;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.examples.snippets.AbstractSnippetTest;
import org.eclipse.basyx.examples.support.ExampleComponentBuilder;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.junit.Test;

/**
 * Test for the AddSubmodelToAAS snippet
 * 
 * @author conradi
 *
 */
public class TestAddSubmodelToAAS extends AbstractSnippetTest {

	private static final String NEW_SM_ID_SHORT = "smIdShort_New";
	private static final String NEW_SM_ID = "smId_New";
	
	private static final String NEW_SM_ENDPOINT = AAS_ENDPOINT + "/submodels/" + NEW_SM_ID_SHORT + "/submodel";
	
	@Test
	public void testAddSubmodelToAAS() {
		
		// Get the example AAS and Submodel
		Submodel submodel = ExampleComponentBuilder.buildExampleSubmodel(NEW_SM_ID_SHORT, NEW_SM_ID);

		// Get the Identifiers for the AAS and the Submodel
		IIdentifier aasIdentifier = new Identifier(IdentifierType.CUSTOM, AAS_ID);
		IIdentifier smIdentifier = submodel.getIdentification();
		
		// Get the AAS as ConnectedAAS
		ConnectedAssetAdministrationShellManager manager = getManager();
		IAssetAdministrationShell aas = manager.retrieveAAS(aasIdentifier);
		
		// Add the Submodel to the AAS
		AddSubmodelToAAS.addSubmodelToAAS(submodel, aas);
		
		
		// Register the new Submodel
		// This needs to be done to be able to retrieve it again
		AASRegistryProxy registry = new AASRegistryProxy(registryComponent.getRegistryPath());
		registry.register(aasIdentifier, new SubmodelDescriptor(submodel, NEW_SM_ENDPOINT));
		
		
		// Check if the Submodel was correctly added
		ISubmodel remoteSM = manager.retrieveSubmodel(aasIdentifier, smIdentifier);
		assertEquals(NEW_SM_ID_SHORT, remoteSM.getIdShort());
		
	}
	
}
