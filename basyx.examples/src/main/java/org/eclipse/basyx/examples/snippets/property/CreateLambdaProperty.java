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
package org.eclipse.basyx.examples.snippets.property;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.AASLambdaPropertyHelper;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetype.ValueType;

/**
 * This snippet showcases how create a lambda Property, i.e. a property that
 * retrieves its value at runtime from an arbitrary backend. <br>
 * This is for example useful if a sensor or a edge device has to be connected.
 * It is valid to only provide a getter, i.e. in case of a sensor. <br>
 * The lambda property will only work if it is included in a submodel and being
 * hosted} <br>
 * <br>
 * Please note, that a submodel containing a dynamic property can not be pushed
 * to another server. Instead, it has to be hosted on its own server (see
 * {@link org.eclipse.basyx.examples.snippets.submodel.HostPreconfiguredSubmodel
 * Host Preconfigured Submodel}).
 * 
 * @author schnicke
 *
 */
public class CreateLambdaProperty {

	/**
	 * Configures the passed property so that calls to its setValue/getValue are
	 * delegated
	 * 
	 * @param propertyIdShort
	 *            id of the lambda property
	 * @param type
	 *            type of the lambda property
	 * @param get
	 *            getter to be used
	 * @param set
	 *            setter to be used
	 * @return the created lambda property
	 */
	public static Property createLambdaProperty(String propertyIdShort, ValueType type, Supplier<Object> get, Consumer<Object> set) {
		Property prop = new Property(propertyIdShort, type);

		AASLambdaPropertyHelper.setLambdaValue(prop, get, set);

		return prop;
	}
}
