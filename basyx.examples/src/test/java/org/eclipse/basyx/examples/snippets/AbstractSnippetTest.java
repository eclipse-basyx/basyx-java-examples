/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.snippets;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.examples.support.ExampleAASComponent;
import org.eclipse.basyx.examples.support.ExampleComponentBuilder;
import org.eclipse.basyx.examples.support.ExampleRegistryComponent;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.junit.After;
import org.junit.Before;

/**
 * This class provides the server environment required by all snippet test
 * 
 * @author conradi
 *
 */
public abstract class AbstractSnippetTest {
	

	protected static final String AAS_ID_SHORT = "aasIdShort";
	protected static final String AAS_ID = "aasId";
	protected static final String AAS_ENDPOINT = "http://localhost:8080/aasComponent/shells/" + AAS_ID + "/aas";
	
	protected static final String SM_ID_SHORT = "smIdShort";
	protected static final String SM_ID = "smId";
	protected static final String SM_ENDPOINT = AAS_ENDPOINT + "/submodels/" + SM_ID_SHORT + "/submodel";
	
	protected ExampleAASComponent aasComponent;
	protected ExampleRegistryComponent registryComponent;
	
	@Before
	public void setupServers() {
		registryComponent = new ExampleRegistryComponent(8081);
		registryComponent.startupRegistry();
		aasComponent = new ExampleAASComponent(8080, new AASRegistryProxy(registryComponent.getRegistryPath()));
		aasComponent.startupAASServer();
		
		// Populate the Server with an example AAS/SM
		populateServer();
	}
	
	@After
	public void shutdownServers() {
		aasComponent.shutdownAASServer();
		registryComponent.shutdownRegistry();
	}
	
	/**
	 * Pushes and registers an AAS and a Submodel to the test server
	 */
	private void populateServer() {
		ConnectedAssetAdministrationShellManager manager = getManager();
		
		// Get the example AAS and Submodel
		AssetAdministrationShell aas = ExampleComponentBuilder.buildExampleAAS(AAS_ID_SHORT, AAS_ID);
		Submodel sm = ExampleComponentBuilder.buildExampleSubmodel(SM_ID_SHORT, SM_ID);
		
		// Push and register the AAS
		manager.createAAS(aas, aasComponent.getAASServerPath());
		
		// Push and register the Submodel
		manager.createSubmodel(aas.getIdentification(), sm);
	}
	
	/**
	 * Creates a ConnectedAASManager using the started registryComponent
	 * 
	 * @return the created ConnectedAASManager
	 */
	protected ConnectedAssetAdministrationShellManager getManager() {
		AASRegistryProxy registryProxy = new AASRegistryProxy(registryComponent.getRegistryPath());
		return new ConnectedAssetAdministrationShellManager(registryProxy);
	}
}
