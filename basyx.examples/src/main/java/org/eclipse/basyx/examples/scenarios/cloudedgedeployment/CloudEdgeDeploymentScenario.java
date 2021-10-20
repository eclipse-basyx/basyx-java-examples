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


import java.util.ArrayList;
import java.util.List;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.registration.api.IAASRegistry;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.components.IComponent;
import org.eclipse.basyx.components.aas.AASServerComponent;
import org.eclipse.basyx.components.aas.configuration.AASServerBackend;
import org.eclipse.basyx.components.aas.configuration.BaSyxAASServerConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.registry.RegistryComponent;
import org.eclipse.basyx.components.registry.configuration.BaSyxRegistryConfiguration;
import org.eclipse.basyx.components.registry.configuration.RegistryBackend;
import org.eclipse.basyx.components.servlet.submodel.SubmodelServlet;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;


/**
 * Example scenario demonstrating a deployment with two servers
 * 
 * Server A is created as an empty server in the cloud.
 * An AAS and a Submodel is pushed to it.
 * 
 * Server B is created as a server hosted near a machine.
 * It provides a Submodel containing sensor value.
 * 
 * @author conradi, schnicke
 *
 */
public class CloudEdgeDeploymentScenario {
	
	/**
	 * The registry used in the manager
	 */
	private IAASRegistry registry;
	public static String registryPath = "http://localhost:8080/registry";
	
	/**
	 * AASManager used to handle registration and server communication
	 */
	private ConnectedAssetAdministrationShellManager aasManager;
	
	/**
	 * Identifier of the AAS hosted in the cloud.
	 */
	public static IIdentifier aasIdentifier = ComponentBuilder.getAAS().getIdentification();

	/**
	 * Identifier of the SM hosted in the cloud.
	 * It contains never changing properties of the machine.
	 */
	public static IIdentifier docuSmIdentifier = ComponentBuilder.getDocuSMDescriptor().getIdentifier();

	/**
	 * Identifier of the SM hosted near the machine.
	 * It contains constantly changing sensor data.
	 */
	public static IIdentifier edgeSmIdentifier = ComponentBuilder.getEdgeSubmodelDescriptor().getIdentifier();

	// Used for shutting down the scenario
	private List<IComponent> startedComponents = new ArrayList<>();
	private BaSyxHTTPServer edgeServer;

	/**
	 * Main method to start the scenario
	 * 
	 */
	public static void main(String[] args) {
		new CloudEdgeDeploymentScenario();
	}
	
	/**
	 * Setup the scenario
	 * 
	 */
	public CloudEdgeDeploymentScenario() {
		
		startupRegistryServer();
		
		// Create a InMemoryRegistry to be used by the manager
		registry = new AASRegistryProxy(registryPath);
		
		startupEdgeServer();
		startupCloudServer();

		// Create a ConnectedAASManager with the registry created above
		aasManager = new ConnectedAssetAdministrationShellManager(registry);
		
		
		// Push the AAS to the cloud server
		// The manager automatically registers it in the registry
		aasManager.createAAS(ComponentBuilder.getAAS(), "http://localhost:8081/cloud");
		
		
		// Get the docuSubmodel from the ComponentBuilder
		Submodel docuSubmodel = ComponentBuilder.getDocuSM();
		
		// Push the docuSubmodel to the cloud
		// The manager automatically registers it in the registry
		aasManager.createSubmodel(aasIdentifier, docuSubmodel);
		

		// Add the already existing edgeSM to the descriptor of the aas
		registry.register(aasIdentifier, ComponentBuilder.getEdgeSubmodelDescriptor());
	}
	
	/**
	 * Startup an empty registry at "http://localhost:8080/registry"
	 * 
	 */
	private void startupRegistryServer() {
		// Start an InMemory registry server with a direct configuration
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration(8080, "registry");
		BaSyxRegistryConfiguration registryConfig = new BaSyxRegistryConfiguration(RegistryBackend.INMEMORY);
		IComponent component = new RegistryComponent(contextConfig, registryConfig);
		component.startComponent();
		startedComponents.add(component);
	}

	/**
	 * Startup a server responsible for hosting the "current_temp" edgeSubmodel
	 * at the endpoint "http://localhost:8082/oven/current_temp"
	 * 
	 * In this example this server is hosted close to the machine
	 * and the values it provides are constantly updated.
	 * 
	 */
	private void startupEdgeServer() {
		
		// Create a BaSyxConetxt for port 8082 with an empty endpoint
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration(8082, "");
		BaSyxContext context = contextConfig.createBaSyxContext();
		
		// Get the edgeSubmodel from the ComponentBuilder
		Submodel edgeSubmodel = ComponentBuilder.createEdgeSubmodel();
		
		// Create a new SubmodelServlet containing the edgeSubmodel
		SubmodelServlet smServlet = new SubmodelServlet(edgeSubmodel);
		
		// Add the SubmodelServlet mapping to the context at the path "/oven/curr_temp"
		context.addServletMapping("/oven/" + ComponentBuilder.EDGESM_ID_SHORT + "/*", smServlet);
		
		
		// Create and start a HTTP server with the context created above
		edgeServer = new BaSyxHTTPServer(context);
		edgeServer.start();
	}
	
	/**
	 * Startup an empty server at "http://localhost:8081/cloud"
	 * 
	 * In this example this server is hosted in the cloud.
	 * It holds the AAS and the Submodels which are containing static non changing values.
	 * 
	 */
	private void startupCloudServer() {
		// Load the server context from a .properties file resource
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration();
		contextConfig.loadFromResource("CloudEdgeDeploymentScenarioAASContext.properties");

		// Create the AAS - Can alternatively also be loaded from a .property file
		BaSyxAASServerConfiguration aasServerConfig = new BaSyxAASServerConfiguration(AASServerBackend.INMEMORY, "", registryPath);

		// Create a server according to this configuration
		AASServerComponent cloudServer = new AASServerComponent(contextConfig, aasServerConfig);
		
		// Start the created server
		cloudServer.startComponent();
		startedComponents.add(cloudServer);
	}

	public void stop() {
		startedComponents.stream().forEach(IComponent::stopComponent);
		edgeServer.shutdown();
	}

}
