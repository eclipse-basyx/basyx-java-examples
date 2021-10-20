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
