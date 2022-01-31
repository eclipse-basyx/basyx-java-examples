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

import java.util.ArrayList;
import java.util.List;
import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
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
import org.eclipse.basyx.extensions.aas.registration.authorization.AuthorizedAASRegistryProxy;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxHTTPServer;
import org.eclipse.basyx.vab.protocol.api.IConnectorFactory;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorFactory;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;

/**
 * Example scenario demonstrating a deployment with two servers with Authorized AAS Registry
 * 
 * <p>
 * Server A is created as an empty server in the cloud.
 * An AAS and a Submodel is pushed to it.
 * 
 * Server B is created as a server hosted near a machine.
 * It provides a Submodel containing sensor value.
 * </p>
 * 
 * <p>
 * Note :
 * Keycloak instance should be running before working with this scenario.
 * <br/>
 * 
 * Keycloak instance running on : 127.0.0.1:9006 <br/>
 * KEYCLOAK_USER=admin <br/>
 * KEYCLOAK_PASSWORD=admin <br/>
 * </p>
 * @see <a href="https://hub.docker.com/r/jboss/keycloak/">Keycloak Docker Hub</a>
 * @see <a href="https://www.keycloak.org/documentation">Keycloak Documentation</a>
 * 
 * @author danish
 *
 */
public class AuthorizedRegistryScenario {
		private static final String CLOUD_ENDPOINT = "http://localhost:8081/cloud";
		protected static final String REGISTRY_ENDPOINT = "http://localhost:8080/registry";
		private static final String AUTHORIZED_REGISTRY_CONTEXT_PATH = "AuthorizedRegistryContext.properties";
		private static final String CLOUD_EDGE_DEPLOYMENT_SCENARIO_CONTEXT_FILE_PATH = "CloudEdgeDeploymentScenarioAASContext.properties";
		
		private IAASRegistry registry;
		
		private static AuthorizationProvider authorizationProvider = new AuthorizationProvider();
		
		private ConnectedAssetAdministrationShellManager aasManager;
		
		private static AuthorizedComponentFactory componentFactory = new AuthorizedComponentFactory();
		
		public static final IIdentifier aasIdentifier = componentFactory.getAAS().getIdentification();

		public static final IIdentifier docuSubmodelIdentifier = componentFactory.getDocuSMDescriptor().getIdentifier();

		public static final IIdentifier edgeSubmodelIdentifier = componentFactory.getEdgeSubmodelDescriptor().getIdentifier();

		private List<IComponent> startedComponents = new ArrayList<>();
		
		private BaSyxHTTPServer edgeServer;

		public static void main(String[] args) {
			new AuthorizedRegistryScenario();
		}
		
		public AuthorizedRegistryScenario() {
			startAuthorizedRegistryServer();
			
			createAuthorizedAASRegistryProxy();
			
			startAASAndSubmodelServer();

			createAssetAdministrationShellOnCloudServer();
			
			createSubmodelOnAasCloudServer();
			
			registerEdgeSubmodelIdentifierIntoAuthorizedRegistry();
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
			IConnectorFactory connectorFactory = new HTTPConnectorFactory();
			aasManager = new ConnectedAssetAdministrationShellManager(registry, connectorFactory);
			
			aasManager.createAAS(componentFactory.getAAS(), CLOUD_ENDPOINT);
		}

		private void startAASAndSubmodelServer() {
			startEdgeServer();
			
			startCloudServer();
		}
		
		private void startAuthorizedRegistryServer() {
			BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration();
			
			contextConfig.loadFromResource(AUTHORIZED_REGISTRY_CONTEXT_PATH);
			
			BaSyxRegistryConfiguration registryConfig = configureAuthorizedBasyxRegistry();
			
			IComponent component = startRegistryComponent(contextConfig, registryConfig);
			
			startedComponents.add(component);
		}

		private IComponent startRegistryComponent(BaSyxContextConfiguration contextConfig,
				BaSyxRegistryConfiguration registryConfig) {
			IComponent registryComponent = new RegistryComponent(contextConfig, registryConfig);
			registryComponent.startComponent();
			
			return registryComponent;
		}

		private BaSyxRegistryConfiguration configureAuthorizedBasyxRegistry() {
			BaSyxRegistryConfiguration registryConfig = new BaSyxRegistryConfiguration(RegistryBackend.INMEMORY);
			registryConfig.setAuthorizationEnabled(true);
			
			return registryConfig;
		}

		private BaSyxContextConfiguration configureBasyxContext(int port, String registryPath) {
			return new BaSyxContextConfiguration(port, registryPath);
		}

		private void startEdgeServer() {
			BaSyxContextConfiguration contextConfig = configureBasyxContext(8082, "");
			
			BaSyxContext context = contextConfig.createBaSyxContext();

			Submodel edgeSubmodel = componentFactory.createEdgeSubmodel();
			
			SubmodelServlet smServlet = new SubmodelServlet(edgeSubmodel);
			
			context.addServletMapping("/oven/" + AuthorizedComponentFactory.EDGESM_ID_SHORT + "/*", smServlet);
			
			startEdgeServer(context);
		}

		private void startEdgeServer(BaSyxContext context) {
			edgeServer = new BaSyxHTTPServer(context);
			
			edgeServer.start();
		}
		
		private void startCloudServer() {
			BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration();
			
			contextConfig.loadFromResource(CLOUD_EDGE_DEPLOYMENT_SCENARIO_CONTEXT_FILE_PATH);

			BaSyxAASServerConfiguration aasServerConfig = new BaSyxAASServerConfiguration(AASServerBackend.INMEMORY, "", REGISTRY_ENDPOINT);

			AASServerComponent cloudServer = new AASServerComponent(contextConfig, aasServerConfig);
			
			cloudServer.startComponent();
			
			startedComponents.add(cloudServer);
		}

		public void stop() {
			startedComponents.stream().forEach(IComponent::stopComponent);
			
			edgeServer.shutdown();
		}
}
