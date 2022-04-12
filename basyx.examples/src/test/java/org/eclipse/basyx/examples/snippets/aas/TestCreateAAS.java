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

import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.examples.snippets.AbstractSnippetTest;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.junit.Test;

/**
 * Test for the CreateAAS snippet
 * 
 * @author conradi
 *
 */
public class TestCreateAAS extends AbstractSnippetTest {

	private static final String AAS_ID_SHORT = "aasIdShort";
	private static final String AAS_ID = "aasId";
	
	private static final String ASSET_ID_SHORT = "assetIdShort";
	private static final String ASSET_ID = "assetId";
	
	
	@Test
	public void testCreateAAS() {
		
		// Create an example Asset to be used in the AAS
		Asset asset = new Asset(ASSET_ID_SHORT, new Identifier(IdentifierType.CUSTOM, ASSET_ID), AssetKind.INSTANCE);
		
		// Create an AAS
		AssetAdministrationShell aas = CreateAAS.createAAS(AAS_ID_SHORT, new Identifier(IdentifierType.CUSTOM, AAS_ID), asset);
		
		// Check the created AAS
		assertEquals(AAS_ID_SHORT, aas.getIdShort());
		assertEquals(AAS_ID, aas.getIdentification().getId());
		assertEquals(ASSET_ID_SHORT, aas.getAsset().getIdShort());
		assertEquals(ASSET_ID, aas.getAsset().getIdentification().getId());
	}
	
}
