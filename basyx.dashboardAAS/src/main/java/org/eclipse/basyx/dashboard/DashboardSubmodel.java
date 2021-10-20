/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
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
