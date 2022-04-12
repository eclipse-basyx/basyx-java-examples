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

import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

/**
 * Snippet that showcases how to create a Submodel
 * with the mandatory attributes and a Property.
 * 
 * @author conradi
 *
 */
public class CreateSubmodel {

	/**
	 * Creates a new Submodel with a Property
	 * 
	 * @param idShort the idShort of the Submodel to be created
	 * @param smIdentifier the Identifier of the Submodel to be created
	 * @param propertyIdShort the idShort of the Property to be created
	 * @param propertyValue the value of the Property (see PropertyValueTypeDef for allowed value types)
	 * @return the newly created Submodel
	 */
	public static Submodel createSubmodel(String idShort, IIdentifier smIdentifier, String propertyIdShort, Object propertyValue) {
		
		// Create a new Property with the given idShort and value
		Property property = new Property(propertyIdShort, propertyValue);
		
		// Create a new Submodel object
		Submodel sm = new Submodel(idShort, smIdentifier);
		
		// Add the Property to the Submodel
		sm.addSubmodelElement(property);
		
		return sm;
	}
	
}
