/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.snippets.submodel;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.examples.snippets.AbstractSnippetTest;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.junit.Test;

/**
 * Test for the CreateSubmodel snippet
 * 
 * @author conradi
 *
 */
public class TestCreateSubmodel extends AbstractSnippetTest {

	private static final String SM_ID_SHORT = "smIdShort";
	private static final String SM_ID = "smId";
	
	private static final String PROPERTY_ID_SHORT = "propIdShort";
	private static final String PROPERTY_VALUE = "value";
	
	
	@Test
	public void testCreateSubmodel() {
		
		// Create a Submodel
		Submodel sm = CreateSubmodel.createSubmodel(SM_ID_SHORT, new Identifier(IdentifierType.CUSTOM, SM_ID), PROPERTY_ID_SHORT, PROPERTY_VALUE);
		
		// Check the created Submodel
		assertEquals(SM_ID_SHORT, sm.getIdShort());
		assertEquals(SM_ID, sm.getIdentification().getId());
		
		// Get the Property from the Submodel
		// This will throw an Exception if an Element with the given Id does not exist
		ISubmodelElement property = sm.getSubmodelElement(PROPERTY_ID_SHORT);
		
		// Check the Property
		assertEquals(property.getIdShort(), PROPERTY_ID_SHORT);
		assertEquals(property.getValue(), PROPERTY_VALUE);
	}
	
}
