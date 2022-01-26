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
import org.eclipse.basyx.examples.scenarios.authorization.exception.AddClientException;
import org.eclipse.basyx.examples.scenarios.authorization.exception.RealmCreationException;
import org.eclipse.basyx.examples.scenarios.authorization.exception.RealmDeletionException;
import org.eclipse.basyx.extensions.aas.registration.authorization.AuthorizedAASRegistryProxy;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxHTTPServer;
import org.json.simple.parser.ParseException;
import javassist.NotFoundException;

import org.eclipse.basyx.vab.protocol.api.IConnectorFactory;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorFactory;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;

/**
 * Example scenario demonstrating a deployment with two servers with Authorized AAS Registry
 * 
 * Server A is created as an empty server in the cloud.
 * An AAS and a Submodel is pushed to it.
 * 
 * Server B is created as a server hosted near a machine.
 * It provides a Submodel containing sensor value.
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
		
		public static IIdentifier aasIdentifier = ComponentBuilder.getAAS().getIdentification();

		public static IIdentifier docuSubmodelIdentifier = ComponentBuilder.getDocuSMDescriptor().getIdentifier();

		public static IIdentifier edgeSubmodelIdentifier = ComponentBuilder.getEdgeSubmodelDescriptor().getIdentifier();

		private List<IComponent> startedComponents = new ArrayList<>();
		
		private BaSyxHTTPServer edgeServer;

		public static void main(String[] args) throws RealmCreationException, IOException, NotFoundException, 
							AddClientException, ParseException, RealmDeletionException {
			
			new AuthorizedRegistryScenario();
		}
		
		public AuthorizedRegistryScenario() throws RealmCreationException, IOException, NotFoundException, 
				AddClientException, ParseException, RealmDeletionException {
			startAuthorizedRegistryServer();
			
			createAuthorizedAASRegistryProxy();
			
			startAASAndSubmodelServer();

			createAssetAdministrationShellOnCloudServer();
			
			createSubmodelOnEdgeServer();
			
			registerAasIdentifierIntoAuthorizedRegistry();
		}

		private void registerAasIdentifierIntoAuthorizedRegistry() {
			registry.register(aasIdentifier, ComponentBuilder.getEdgeSubmodelDescriptor());
		}

		private void createAuthorizedAASRegistryProxy() throws RealmCreationException, IOException, NotFoundException,
				AddClientException, ParseException, RealmDeletionException {
			registry = new AuthorizedAASRegistryProxy(REGISTRY_ENDPOINT, authorizationProvider.getAuthorizationSupplier());
		}

		private void createSubmodelOnEdgeServer() {
			Submodel docuSubmodel = ComponentBuilder.getDocuSM();
			
			aasManager.createSubmodel(aasIdentifier, docuSubmodel);
		}

		private void createAssetAdministrationShellOnCloudServer() {
			IConnectorFactory connectorFactory = new HTTPConnectorFactory();
			aasManager = new ConnectedAssetAdministrationShellManager(registry, connectorFactory);
			
			aasManager.createAAS(ComponentBuilder.getAAS(), CLOUD_ENDPOINT);
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
			
			addStartedComponentToList(component);
		}

		private void addStartedComponentToList(IComponent component) {
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

			Submodel edgeSubmodel = ComponentBuilder.createEdgeSubmodel();
			
			SubmodelServlet smServlet = new SubmodelServlet(edgeSubmodel);
			
			context.addServletMapping("/oven/" + ComponentBuilder.EDGESM_ID_SHORT + "/*", smServlet);
			
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
			
			addStartedComponentToList(cloudServer);
		}

		public void stop() {
			startedComponents.stream().forEach(IComponent::stopComponent);
			
			edgeServer.shutdown();
		}
}
