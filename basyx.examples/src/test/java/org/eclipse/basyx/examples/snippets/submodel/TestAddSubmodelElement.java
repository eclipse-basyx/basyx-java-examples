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

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.examples.snippets.AbstractSnippetTest;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.junit.Test;

/**
 * Test for the AddSubmodelElement snippet
 * 
 * @author conradi
 *
 */
public class TestAddSubmodelElement extends AbstractSnippetTest {

	private static final String NEW_PROPERTY_ID = "new_prop";
	private static final int NEW_PROPERTY_VALUE = 321;

	@Test
	public void testAddSubmodelElement() {

		// Get the Identifiers of the AAS and Submodel
		IIdentifier aasIdentifier = new Identifier(IdentifierType.CUSTOM, AAS_ID);
		IIdentifier smIdentifier = new Identifier(IdentifierType.CUSTOM, SM_ID);

		// Build a new SubmodelElement
		Property newProperty = new Property();
		newProperty.setIdShort(NEW_PROPERTY_ID);
		newProperty.setValue(NEW_PROPERTY_VALUE);

		// Add the new Element to the Submodel
		AddSubmodelElement.addSubmodelElement(newProperty, smIdentifier, aasIdentifier, registryComponent.getRegistryPath());

		// Get the Element from the server
		ConnectedAssetAdministrationShellManager manager = getManager();
		ISubmodel remoteSubmodel = manager.retrieveSubmodel(aasIdentifier, smIdentifier);
		ISubmodelElement remoteElement = remoteSubmodel.getSubmodelElement(NEW_PROPERTY_ID);

		// Check if its value is as expected
		assertEquals(NEW_PROPERTY_VALUE, remoteElement.getValue());
	}

}
