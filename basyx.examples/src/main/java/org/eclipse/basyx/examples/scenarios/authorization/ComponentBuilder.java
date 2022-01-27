/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.scenarios.authorization;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.CustomId;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.aas.registration.api.IAASRegistry;
import org.eclipse.basyx.examples.scenarios.authorization.exception.AddClientException;
import org.eclipse.basyx.examples.scenarios.authorization.exception.RealmCreationException;
import org.eclipse.basyx.examples.scenarios.authorization.exception.RealmDeletionException;
import org.eclipse.basyx.extensions.aas.registration.authorization.AuthorizedAASRegistryProxy;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.ModelingKind;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;
import org.json.simple.parser.ParseException;
import javassist.NotFoundException;

/**
 * A helper Class used to build the necessary objects. 
 * 
 * @author conradi, danish
 *
 */
public class ComponentBuilder {
	public static final String AAS_ID_SHORT = "oven";
	public static final String AAS_ID = "basyx.examples.oven";
	public static final String AAS_ENDPOINT = "http://localhost:8081/cloud/shells/" + AAS_ID + "/aas";

	public static final String EDGESM_ID_SHORT = "temp";
	public static final String EDGESM_ID = "basyx.examples.oven.oven_temperature";
	public static final String EDGESM_ENDPOINT = "http://localhost:8082/oven/" + EDGESM_ID_SHORT + "/submodel";
	public static final String EDGESM_CURR_TEMP_ID_SHORT = "curr_temp";
	public static final String EDGESM_TARGET_TEMP_ID_SHORT = "target_temp";
	
	public static final String EDGESM_OP_SET_TEMP_ID_SHORT = "setTargetTemp";
	public static final String OP_SET_TEMP_IN_VAR_ID_SHORT = "targetTemp";
	
	public static final String EDGESM_OP_GET_TEMP_ID_SHORT = "getCurrTemp";
	public static final String OP_GET_TEMP_OUT_VAR_ID_SHORT = "currTemp";
	
	
	public static final String DOCUSM_ID_SHORT = "oven_doc";
	public static final String DOCUSM_ID = "basyx.examples.oven.oven_documentation_sm";
	public static final String DOCUSM_ENDPOINT = "http://localhost:8081/cloud/shells/" + AAS_ID + "/aas/submodels/" + DOCUSM_ID_SHORT + "/submodel";
	
	private static AuthorizationProvider authorizationProvider = new AuthorizationProvider();
	
	/**
	 * Creates the AAS, which gets pushed to the cloud server
	 * 
	 * @return the created AAS
	 */
	public static AssetAdministrationShell getAAS() {
		Asset ovenAsset = createOvenAsset();

		AssetAdministrationShell ovenAAS = new AssetAdministrationShell(AAS_ID_SHORT, new CustomId(AAS_ID), ovenAsset);

		return ovenAAS;
	}

	private static Asset createOvenAsset() {
		Asset ovenAsset = new Asset("OvenAsset", new CustomId("basyx.examples.OvenAsset"), AssetKind.INSTANCE);
		
		return ovenAsset;
	}
	
	/**
	 * Creates the Submodel, which gets pushed to the cloud server
	 * It contains static non changing information
	 * 
	 * @return the created Submodel
	 */
	public static Submodel getDocuSM() {
		Submodel docuSm = createDocuSubmodel();

		Property maxTemp = new Property("max_temp", 1000);
		
		docuSm.addSubmodelElement(maxTemp);
		
		return docuSm;
	}

	private static Submodel createDocuSubmodel() {
		Submodel docuSm = new Submodel(DOCUSM_ID_SHORT, new CustomId(DOCUSM_ID));
		
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
		Submodel edgeSm = new Submodel(EDGESM_ID_SHORT, new CustomId(EDGESM_ID));
		
		setPropertyToEdgeSubmodel(edgeSm);
		
		return edgeSm;
	}

	private static void setPropertyToEdgeSubmodel(Submodel edgeSm) {
		Property property = new Property(EDGESM_CURR_TEMP_ID_SHORT, 31);
		edgeSm.addSubmodelElement(property);
		
		property = new Property(EDGESM_TARGET_TEMP_ID_SHORT, 250);
		edgeSm.addSubmodelElement(property);
		
		edgeSm.addSubmodelElement(getSetTargetTempOperation());
		edgeSm.addSubmodelElement(getGetCurrTempOperation());
	}
	
	
	/**
	 * Returns the Operation to set the value of the targetTemp property
	 * This Operation only has input, no output
	 * 
	 * @return Operation
	 */
	private static Operation getSetTargetTempOperation() {
		
		Operation operation = new Operation(EDGESM_OP_SET_TEMP_ID_SHORT);
		
		Property newTargetTemp = new Property(OP_SET_TEMP_IN_VAR_ID_SHORT, 0);
		newTargetTemp.setModelingKind(ModelingKind.TEMPLATE);
		
		OperationVariable var = new OperationVariable(newTargetTemp);
		
		operation.setInputVariables(Arrays.asList(var));
		
		Consumer<Object[]> consumer = (Consumer<Object[]>) t -> {
			ConnectedAssetAdministrationShellManager manager = null;
			try {
				manager = getManagerWithAuthorization();
			} catch (RealmCreationException | AddClientException | RealmDeletionException e) {
				e.printStackTrace();
			}
			ISubmodel sm = manager.retrieveSubmodel(new ModelUrn(AAS_ID), new ModelUrn(EDGESM_ID));
			ISubmodelElement elem = sm.getSubmodelElement(EDGESM_TARGET_TEMP_ID_SHORT);
			elem.setValue((int) (t[0]));
		};
		
		operation.setInvokable(consumer);
		
		return operation;
	}
	
	/**
	 * Returns the Operation to get the value of the currTemp property
	 * This Operation only has output, no input
	 * 
	 * @return Operation
	 */
	private static Operation getGetCurrTempOperation() {
		Operation operation = new Operation(EDGESM_OP_GET_TEMP_ID_SHORT);

		Property currentTemp = new Property(OP_GET_TEMP_OUT_VAR_ID_SHORT, 0);
		currentTemp.setModelingKind(ModelingKind.TEMPLATE);
		
		OperationVariable var = new OperationVariable(currentTemp);
		
		operation.setOutputVariables(Arrays.asList(var));
		
		Supplier<Object> supplier = (Supplier<Object>) () -> {
			ConnectedAssetAdministrationShellManager manager = null;
			try {
				manager = getManagerWithAuthorization();
			} catch (RealmCreationException | AddClientException | RealmDeletionException e) {
				e.printStackTrace();
			}
			ISubmodel sm = manager.retrieveSubmodel(new ModelUrn(AAS_ID), new ModelUrn(EDGESM_ID));
			ISubmodelElement elem = sm.getSubmodelElement(EDGESM_CURR_TEMP_ID_SHORT);
			return elem.getValue();
		};
		
		operation.setInvokable(supplier);
		
		return operation;
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
	
	/**
	 * Creates a ConnectedAssetAdministrationShellManager connected to the AASServer
	 * 
	 * @return ConnectedAssetAdministrationShellManager
	 * @throws RealmDeletionException 
	 * @throws ParseException 
	 * @throws AddClientException 
	 * @throws NotFoundException 
	 * @throws IOException 
	 * @throws RealmCreationException 
	 */
	private static ConnectedAssetAdministrationShellManager getManagerWithAuthorization() throws RealmCreationException, AddClientException, RealmDeletionException {
		
		IAASRegistry registry = new AuthorizedAASRegistryProxy(AuthorizedRegistryScenario.REGISTRY_ENDPOINT, authorizationProvider.getAuthorizationSupplier());
		
		return new ConnectedAssetAdministrationShellManager(registry);
	}
}
