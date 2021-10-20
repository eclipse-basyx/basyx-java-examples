/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.wrapper.provider.aas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyType;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProviderHelper;
import org.eclipse.basyx.wrapper.receiver.IPropertyWrapperService;
import org.eclipse.basyx.wrapper.receiver.PropertyResult;
import org.eclipse.basyx.wrapper.receiver.configuration.PropertyConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A submodel for wrapping the data
 * 
 * @author espen
 *
 */
public class WrapperSubmodel extends Submodel {
	private static final Logger logger = LoggerFactory.getLogger(WrapperSubmodel.class);

	protected Map<String, SubmodelElementCollection> collections = new HashMap<>();
	private IPropertyWrapperService proxyService;

	public WrapperSubmodel(String idShort, IIdentifier id, IPropertyWrapperService connector,
			Collection<PropertyConfiguration> config) {
		super();
		setIdShort(idShort);
		setIdentification(id.getIdType(), id.getId());
		setSemanticId(new Reference(new Key(KeyElements.CONCEPTDESCRIPTION, true, "0112/2///61360_4#AAF891#001", KeyType.IRDI)));

		proxyService = connector;
		proxyService.addProxyListener((String propId, PropertyResult result) -> {
			SubmodelElementCollection coll = collections.get(propId);
			if (coll != null) {
				Collection<ISubmodelElement> smElements = generateSubmodelElements(propId, result);
				coll.setValue(smElements);
			} else {
				logger.error("Invalid property '" + propId + "' has been updated");
			}
		});
		initialize(config, connector);
	}

	private void initialize(Collection<PropertyConfiguration> configs, IPropertyWrapperService connector) {
		for ( PropertyConfiguration config : configs ) {
			String propId = config.getId();
			if ( config.getActive() ) {
				setActiveProperty(propId);
			} else {
				setPassiveProperty(propId);		
			}
		}
	}

	private Collection<ISubmodelElement> generateSubmodelElements(String idShort, PropertyResult result) {
		Collection<ISubmodelElement> elements = new ArrayList<>();
		List<Object> data = result.getData();
		List<String> dates = result.getTimestamps();
		for (int i = data.size() - 1; i >= 0; i--) {
			Property dateProp = new Property();
			dateProp.setIdShort("time" + i);
			dateProp.setValue(dates.get(i).toString());
			elements.add(dateProp);
			Property valueProp = new Property();
			valueProp.setIdShort("value" + i);
			valueProp.setValue(data.get(i));
			elements.add(valueProp);
		}
		Operation setOperation = new Operation();
		setOperation.setIdShort("set" + idShort);
		Function<Object[], Object> fillInvokable = (params) -> {
			// not supported, yet
			return params;
		};
		setOperation.setInvokable(fillInvokable);
		elements.add(setOperation);
		return elements;
	}

	private void setActiveProperty(String propId) {
		SubmodelElementCollection coll = new SubmodelElementCollection();
		coll.setIdShort(propId);
		collections.put(propId, coll);
		addSubmodelElement(coll);
	}

	private void setPassiveProperty(final String propId) {
		SubmodelElementCollection coll = new SubmodelElementCollection();
		coll.setIdShort(propId);
		collections.put(propId, new SubmodelElementCollection());
		addSubmodelElement(coll);

		coll.put(Property.VALUE, VABLambdaProviderHelper.createSimple(() -> {
			proxyService.generatePassiveValue(propId);
			return collections.get(propId).getValue();
		}, null));
	}
}
