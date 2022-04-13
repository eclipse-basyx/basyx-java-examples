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
