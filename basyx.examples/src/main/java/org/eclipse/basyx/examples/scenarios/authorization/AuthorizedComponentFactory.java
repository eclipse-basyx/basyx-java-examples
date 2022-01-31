/*******************************************************************************
 * Copyright (C) 2022 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.scenarios.authorization;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.registration.api.IAASRegistry;
import org.eclipse.basyx.examples.scenarios.cloudedgedeployment.ComponentFactory;
import org.eclipse.basyx.extensions.aas.registration.authorization.AuthorizedAASRegistryProxy;

/**
 * A derived helper Class used to build the necessary objects. 
 * 
 * @author danish
 *
 */
public class AuthorizedComponentFactory extends ComponentFactory {
	
	private static AuthorizationProvider authorizationProvider = new AuthorizationProvider();

	/**
	 * Creates a ConnectedAssetAdministrationShellManager connected to the Authorized AASServer 
	 * 
	 */
	@Override
	protected ConnectedAssetAdministrationShellManager getManager() {
		IAASRegistry registry = new AuthorizedAASRegistryProxy(AuthorizedRegistryScenario.REGISTRY_ENDPOINT, authorizationProvider.getAuthorizationSupplier());
		
		return new ConnectedAssetAdministrationShellManager(registry);
	}
}
