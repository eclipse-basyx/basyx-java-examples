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
