/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.support;

import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetype.ValueType;

/**
 * This class is used to build AssetAdministrationShells and Submodels
 * for the scenarios and snippets.
 * 
 * Please note that the generated objects are just for showcasing,
 * several mandatory attributes are missing. 
 * 
 * @author conradi
 *
 */
public class ExampleComponentBuilder {

	public static final String PROPERTY_ID = "prop";
	public static final int PROPERTY_VALUE = 123;

	public static final String COLLECTION_ID = "collection";
	public static final String COLLECTION_PROPERTY_ID = "propInCollection";
	public static final String COLLECTION_PROPERTY_VALUE = "TheValue";
	
	/**
	 * Builds a Submodel containing a Property and a Collection with a Property
	 * 
	 * @param idShort the idShort for the new Submodel
	 * @return the new Submodel
	 */
	public static Submodel buildExampleSubmodel(String idShort, String id) {
		Submodel submodel = new Submodel(idShort, new Identifier(IdentifierType.CUSTOM, id));
		
		// Add a Property to the Submodel
		Property property = new Property(PROPERTY_ID, ValueType.Int32);
		property.setValue(PROPERTY_VALUE);
		submodel.addSubmodelElement(property);
				
		// Add a SubmodelElementCollection
		SubmodelElementCollection collection = new SubmodelElementCollection(COLLECTION_ID);
		
		// Add a Property to the SubmodelElementCollection
		Property property2 = new Property(COLLECTION_PROPERTY_ID, ValueType.String);
		property2.setValue(COLLECTION_PROPERTY_VALUE);
		collection.addSubmodelElement(property2);
		submodel.addSubmodelElement(collection);
		
		return submodel;
	}
	
	/**
	 * Builds an AssetAdministrationShell
	 * 
	 * @param idShort the idShort for the new AAS
	 * @param id the id to be used in Identification
	 * @return the new AAS
	 */
	public static AssetAdministrationShell buildExampleAAS(String idShort, String id) {
		Identifier aasIdentifier = new Identifier(IdentifierType.CUSTOM, id);
		Identifier assetIdentifier = new Identifier(IdentifierType.CUSTOM, id + "asset");
		Asset asset = new Asset(idShort + "asset", assetIdentifier, AssetKind.INSTANCE);
		AssetAdministrationShell aas = new AssetAdministrationShell(idShort, aasIdentifier, asset);
		
		return aas;
	}
	
}
