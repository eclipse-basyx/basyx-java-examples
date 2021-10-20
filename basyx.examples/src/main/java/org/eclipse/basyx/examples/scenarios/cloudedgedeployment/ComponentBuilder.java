/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.scenarios.cloudedgedeployment;

import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.CustomId;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

/**
 * A helper Class used to build the necessary objects. 
 * 
 * @author conradi
 *
 */
public class ComponentBuilder {
	public static final String AAS_ID_SHORT = "oven";
	public static final String AAS_ID = "basyx.examples.oven";
	public static final String AAS_ENDPOINT = "http://localhost:8081/cloud/shells/" + AAS_ID + "/aas";

	public static final String EDGESM_ID_SHORT = "curr_temp";
	public static final String EDGESM_ID = "basyx.examples.oven.current_oven_temperature";
	public static final String EDGESM_ENDPOINT = "http://localhost:8082/oven/" + EDGESM_ID_SHORT + "/submodel";
	
	public static final String DOCUSM_ID_SHORT = "oven_doc";
	public static final String DOCUSM_ID = "basyx.examples.oven.oven_documentation_sm";
	public static final String DOCUSM_ENDPOINT = "http://localhost:8081/cloud/shells/" + AAS_ID + "/aas/submodels/" + DOCUSM_ID_SHORT + "/submodel";
	
	/**
	 * Creates the AAS, which gets pushed to the cloud server
	 * 
	 * @return the created AAS
	 */
	public static AssetAdministrationShell getAAS() {
		// Create the oven asset
		Asset ovenAsset = new Asset("OvenAsset", new CustomId("basyx.examples.OvenAsset"), AssetKind.INSTANCE);

		// Create the AAS representing the oven
		AssetAdministrationShell ovenAAS = new AssetAdministrationShell(AAS_ID_SHORT, new CustomId(AAS_ID), ovenAsset);

		return ovenAAS;
	}
	
	/**
	 * Creates the Submodel, which gets pushed to the cloud server
	 * It contains static non changing information
	 * 
	 * @return the created Submodel
	 */
	public static Submodel getDocuSM() {
		// Create the documentation Submodel
		Submodel docuSm = new Submodel(DOCUSM_ID_SHORT, new CustomId(DOCUSM_ID));

		// Create the maximum temperature property and include it in the submodel
		Property maxTemp = new Property("max_temp", 1000);
		docuSm.addSubmodelElement(maxTemp);
		
		return docuSm;
	}
	
	/**
	 * Creates the SubmodelDescriptor for the Docu-Submodel
	 * 
	 * @return the created SubmodelDescriptor
	 */
	public static SubmodelDescriptor getDocuSMDescriptor() {
		return new SubmodelDescriptor(getDocuSM(), DOCUSM_ENDPOINT);
	}
	
	/**
	 * Creates the Submodel, which gets hosted near the machine
	 * It contains measured values that are updated frequently
	 * 
	 * @return the created Submodel
	 */
	public static Submodel createEdgeSubmodel() {
		// Create the edge submodel
		Submodel edgeSm = new Submodel(EDGESM_ID_SHORT, new CustomId(EDGESM_ID));
		
		// The property in this Submodel contains the currently measured temperature of the oven
		// It is represented by a static value in this example
		Property property = new Property("temp", 31);
		edgeSm.addSubmodelElement(property);
		return edgeSm;
	}
	
	/**
	 * Creates the SubmodelDescriptor for the Edge-Submodel
	 * 
	 * @return the created SubmodelDescriptor
	 */
	public static SubmodelDescriptor getEdgeSubmodelDescriptor() {
		Identifier identifier = new Identifier(IdentifierType.CUSTOM, EDGESM_ID);
		return new SubmodelDescriptor(EDGESM_ID_SHORT, identifier, EDGESM_ENDPOINT);
	}

}
