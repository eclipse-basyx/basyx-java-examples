/*******************************************************************************
 * Copyright (C) 2022 the Eclipse BaSyx Authors
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
package org.eclipse.basyx.examples.scenarios.authorization.registry_using_components;

import static org.eclipse.basyx.examples.scenarios.authorization.ShutdownHookUtil.addShutdownHook;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.basyx.aas.registration.api.IAASRegistry;
import org.eclipse.basyx.components.IComponent;
import org.eclipse.basyx.components.aas.AASServerComponent;
import org.eclipse.basyx.components.aas.configuration.AASServerBackend;
import org.eclipse.basyx.components.aas.configuration.BaSyxAASServerConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.registry.RegistryComponent;
import org.eclipse.basyx.components.registry.configuration.BaSyxRegistryConfiguration;
import org.eclipse.basyx.components.registry.configuration.RegistryBackend;
import org.eclipse.basyx.components.servlet.submodel.SubmodelServlet;
import org.eclipse.basyx.examples.scenarios.authorization.AuthorizationProvider;
import org.eclipse.basyx.examples.scenarios.authorization.AuthorizedComponentFactory;
import org.eclipse.basyx.examples.scenarios.authorization.exception.RealmDeletionException;
import org.eclipse.basyx.examples.snippets.configuration.KeycloakConfiguration;
import org.eclipse.basyx.extensions.aas.manager.authorized.AuthorizedConnectedAASManager;
import org.eclipse.basyx.extensions.aas.registration.authorization.AuthorizedAASRegistryProxy;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxHTTPServer;

/**
 * Example scenario demonstrating a deployment with two servers with Authorized
 * AAS Registry
 * 
 * <p>
 * Server A is created as an empty server in the cloud. An AAS and a Submodel is
 * pushed to it.
 * 
 * Server B is created as a server hosted near a machine. It provides a Submodel
 * containing sensor value.
 * </p>
 * 
 * <p>
 * Note : Keycloak instance should be running before working with this scenario.
 * <br/>
 *
 * Keycloak instance running on : see KeycloakContext.properties <br/>
 * KEYCLOAK_USER=admin <br/>
 * KEYCLOAK_PASSWORD=admin <br/>
 * </p>
 * 
 * @see <a href="https://hub.docker.com/r/jboss/keycloak/">Keycloak Docker
 *      Hub</a>
 * @see <a href="https://www.keycloak.org/documentation">Keycloak
 *      Documentation</a>
 * 
 * @author danish
 *
 */
public class AuthorizedRegistryUsingComponentsScenario {
	private static final String CLOUD_ENDPOINT = "http://localhost:8081/cloud";
	protected static final String REGISTRY_ENDPOINT = "http://localhost:8080/registry";
	private static final String AUTHORIZED_REGISTRY_CONTEXT_PATH = "AuthorizedRegistryContext.properties";
	private static final String CLOUD_EDGE_DEPLOYMENT_SCENARIO_CONTEXT_FILE_PATH = "CloudEdgeDeploymentScenarioAASContext.properties";

	private IAASRegistry registry;

	private static AuthorizationProvider authorizationProvider = new AuthorizationProvider();

	private AuthorizedConnectedAASManager aasManager;

	private static AuthorizedComponentFactory componentFactory = new AuthorizedComponentFactory(AuthorizedRegistryUsingComponentsScenario.REGISTRY_ENDPOINT);

	public static final IIdentifier aasIdentifier = componentFactory.getAAS().getIdentification();

	public static final IIdentifier docuSubmodelIdentifier = componentFactory.getDocuSMDescriptor().getIdentifier();

	public static final IIdentifier edgeSubmodelIdentifier = componentFactory.getEdgeSubmodelDescriptor().getIdentifier();

	private List<IComponent> startedComponents = new ArrayList<>();

	private BaSyxHTTPServer edgeServer;

	public static void main(String[] args) {
		new AuthorizedRegistryUsingComponentsScenario();
	}

	public AuthorizedRegistryUsingComponentsScenario() {
		startAuthorizedRegistryServer();

		createAuthorizedAASRegistryProxy();

		createAssetAdministrationShellOnCloudServer();

		createSubmodelOnAasCloudServer();

		registerEdgeSubmodelIdentifierIntoAuthorizedRegistry();

		addShutdownHook(this::stop);
	}

	private void registerEdgeSubmodelIdentifierIntoAuthorizedRegistry() {
		registry.register(aasIdentifier, componentFactory.getEdgeSubmodelDescriptor());
	}

	private void createAuthorizedAASRegistryProxy() {
		registry = new AuthorizedAASRegistryProxy(REGISTRY_ENDPOINT, authorizationProvider.getAuthorizationSupplier());
	}

	private void createSubmodelOnAasCloudServer() {
		Submodel docuSubmodel = componentFactory.getDocuSM();

		aasManager.createSubmodel(aasIdentifier, docuSubmodel);
	}

	private void createAssetAdministrationShellOnCloudServer() {
		aasManager = new AuthorizedConnectedAASManager(registry, authorizationProvider.getAuthorizationSupplier());

		aasManager.createAAS(componentFactory.getAAS(), CLOUD_ENDPOINT);
	}

	private void startAuthorizedRegistryServer() {
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration();

		contextConfig.loadFromResource(AUTHORIZED_REGISTRY_CONTEXT_PATH);

		BaSyxRegistryConfiguration registryConfig = configureAuthorizedBasyxRegistry();

		IComponent component = startRegistryComponent(contextConfig, registryConfig);

		startedComponents.add(component);
	}

	private IComponent startRegistryComponent(BaSyxContextConfiguration contextConfig, BaSyxRegistryConfiguration registryConfig) {
		IComponent registryComponent = new RegistryComponent(contextConfig, registryConfig);
		registryComponent.startComponent();

		return registryComponent;
	}

	private BaSyxRegistryConfiguration configureAuthorizedBasyxRegistry() {
		BaSyxRegistryConfiguration registryConfig = new BaSyxRegistryConfiguration(RegistryBackend.INMEMORY);

		registryConfig.loadFromResource(AUTHORIZED_REGISTRY_CONTEXT_PATH);

		KeycloakConfiguration keycloakConfig = new KeycloakConfiguration();

		keycloakConfig.loadFromResource(KeycloakConfiguration.KEYCLOAK_CONTEXT_FILE_PATH);

		registryConfig.setAuthorizationStrategyJwtBearerTokenAuthenticationConfigurationProviderKeycloakRealm(keycloakConfig.getRealm());
		registryConfig.setAuthorizationStrategyJwtBearerTokenAuthenticationConfigurationProviderKeycloakServerUrl(keycloakConfig.getServerUrl());

		return registryConfig;
	}

	private BaSyxContextConfiguration configureBasyxContext(int port, String registryPath) {
		return new BaSyxContextConfiguration(port, registryPath);
	}

	public void stop() {
		startedComponents.stream().forEach(IComponent::stopComponent);

		edgeServer.shutdown();

		try {
			authorizationProvider.deleteRealm();
		} catch (RealmDeletionException e) {
			throw new RuntimeException(e);
		}
	}
}
