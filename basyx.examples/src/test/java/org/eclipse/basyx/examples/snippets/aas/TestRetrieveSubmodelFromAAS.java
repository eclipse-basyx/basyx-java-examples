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
package org.eclipse.basyx.examples.snippets.aas;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.examples.snippets.AbstractSnippetTest;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.junit.Test;

/**
 * Test for the RetrieveSubmodelFromAAS snippet
 * 
 * @author conradi
 *
 */
public class TestRetrieveSubmodelFromAAS extends AbstractSnippetTest {

	@Test
	public void testRetrieveSubmodelFromAAS() {
		// Get the Identifiers for the AAS and the Submodel
		IIdentifier aasIdentifier = new Identifier(IdentifierType.CUSTOM, AAS_ID);
		IIdentifier smIdentifier = new Identifier(IdentifierType.CUSTOM, SM_ID);

		// Get the AAS as ConnectedAAS
		ConnectedAssetAdministrationShellManager manager = getManager();
		IAssetAdministrationShell aas = manager.retrieveAAS(aasIdentifier);

		// Get the Submodel from the server
		ISubmodel remoteSubmodel = RetrieveSubmodelFromAAS.retrieveSubmodelFromAAS(smIdentifier, aas);

		// Check if the Submodel can be used correctly
		assertEquals(SM_ID_SHORT, remoteSubmodel.getIdShort());
	}

}
