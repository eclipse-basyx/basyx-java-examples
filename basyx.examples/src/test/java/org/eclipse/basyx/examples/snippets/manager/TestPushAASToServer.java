/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.snippets.manager;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.examples.snippets.AbstractSnippetTest;
import org.eclipse.basyx.examples.support.ExampleComponentBuilder;
import org.junit.Test;

/**
 * Test for the PushAASToServer snippet
 * 
 * @author conradi
 *
 */
public class TestPushAASToServer extends AbstractSnippetTest {
	
	protected static final String NEW_AAS_ID_SHORT = "aasIdShort_New";
	protected static final String NEW_AAS_ID = "aasId_New";
	
	@Test
	public void testPushAAS() throws Exception {
		
		// Get the example AAS
		AssetAdministrationShell aas = ExampleComponentBuilder.buildExampleAAS(NEW_AAS_ID_SHORT, NEW_AAS_ID);
		
		// Push the AAS to the server
		PushAASToServer.pushAAS(aas, aasComponent.getAASServerPath(), registryComponent.getRegistryPath());
		
		// Check if the AAS is present on the server
		ConnectedAssetAdministrationShellManager manager = getManager();
		IAssetAdministrationShell remoteAAS = manager.retrieveAAS(aas.getIdentification());
		assertEquals(NEW_AAS_ID_SHORT, remoteAAS.getIdShort());
	}
	
}
