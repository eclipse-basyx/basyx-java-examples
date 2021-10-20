/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
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
