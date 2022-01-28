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
public class ComponentFactory extends org.eclipse.basyx.examples.scenarios.cloudedgedeployment.ComponentFactory {
	
	private static AuthorizationProvider authorizationProvider = new AuthorizationProvider();

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
	public ConnectedAssetAdministrationShellManager getManager() {
		
		IAASRegistry registry = new AuthorizedAASRegistryProxy(AuthorizedRegistryScenario.REGISTRY_ENDPOINT, authorizationProvider.getAuthorizationSupplier());
		
		return new ConnectedAssetAdministrationShellManager(registry);
	}
}
