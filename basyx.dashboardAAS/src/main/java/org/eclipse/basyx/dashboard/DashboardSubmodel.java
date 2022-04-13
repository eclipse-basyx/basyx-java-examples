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
package org.eclipse.basyx.dashboard;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyType;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetype.ValueType;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProviderHelper;

/**
 * Dummy Submodel with two dynamic values. A configurable temperature value that randomly
 * returns values in a specific range. And a random boolean value.
 * 
 * @author espen
 *
 */
public class DashboardSubmodel extends Submodel {
	private int minValue;
	private int maxValue;

	public DashboardSubmodel() {
		setIdShort("DashboardSubmodel");
		setIdentification(IdentifierType.CUSTOM, "DashboardTemperatureSubmodel");
		setSemanticId(new Reference(
				new Key(KeyElements.CONCEPTDESCRIPTION, true, "0112/2///61360_4#AAF891#001", KeyType.IRDI)));
		setTemperatureProperty();
		setDummyProperty();

		DashboardSubmodelConfiguration config = new DashboardSubmodelConfiguration();
		config.loadFromDefaultSource();
		minValue = config.getMin();
		maxValue = config.getMax();
	}

	private void setTemperatureProperty() {
		Property temperatureProperty = new Property();
		temperatureProperty.setIdShort("temperature");
		temperatureProperty.set(VABLambdaProviderHelper.createSimple(() -> {
			return Math.random() * (maxValue - minValue) + minValue;
		}, null), ValueType.Double);
		// Adds a reference to a semantic ID to specify the property semantics (see CDD)
		// Ref by identifier:
		Key key = new Key(KeyElements.CONCEPTDESCRIPTION, true, "0112/2///61360_4#AAF891#001", KeyType.IRDI);
		IReference refByIdentifier = new Reference(key);
		temperatureProperty.setSemanticId(refByIdentifier);
		addSubmodelElement(temperatureProperty);
	}

	private void setDummyProperty() {
		Property dummyProperty = new Property();
		dummyProperty.setIdShort("dummy");
		dummyProperty.set(VABLambdaProviderHelper.createSimple(() -> {
			return (Math.random() > 0.5);
		}, null), ValueType.Boolean);
		addSubmodelElement(dummyProperty);
	}
}
