/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
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
package org.eclipse.basyx.hello_world;

import java.io.IOException;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.CustomId;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.components.aas.AASServerComponent;
import org.eclipse.basyx.components.aas.configuration.BaSyxAASServerConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.registry.RegistryComponent;
import org.eclipse.basyx.components.registry.configuration.BaSyxRegistryConfiguration;
import org.eclipse.basyx.components.registry.configuration.RegistryBackend;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

import io.moquette.broker.Server;
import io.moquette.broker.config.ClasspathResourceLoader;
import io.moquette.broker.config.IConfig;
import io.moquette.broker.config.IResourceLoader;
import io.moquette.broker.config.ResourceLoaderConfig;

/**
 * This class starts an AAS server and a Registry server
 * 
 * An AAS and a Submodel containing a Property "maxTemp"
 * is uploaded to the AAS server and registered in the Registry server.
 * 
 * Additionally, MQTT eventing is configured and an MQTT broker is started at tcp://localhost:1884
 * 
 * @author schnicke, conradi
 *
 */
public class HelloWorldServer {
	// HelloWorldServer URLs
	public static final String REGISTRYPATH = "http://localhost:4000/registry";
	public static final String AASSERVERPATH = "http://localhost:4001/aasServer";

	// AAS/Submodel/Property Ids
	public static final IIdentifier OVENAASID = new CustomId("eclipse.basyx.aas.oven");
	public static final IIdentifier DOCUSMID = new CustomId("eclipse.basyx.submodel.documentation");
	public static final String MAXTEMPID = "maxTemp";
	private static Server mqttBroker;

	public static void main(String[] args) throws IOException {
		startMqttBroker();
		startAASRegistry();
		startAASServer();

		// Create Manager - This manager is used to interact with an AAS server
		ConnectedAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(new AASRegistryProxy(REGISTRYPATH));

		// Create AAS and push it to server
		Asset asset = new Asset("ovenAsset", new CustomId("eclipse.basyx.asset.oven"), AssetKind.INSTANCE);
		AssetAdministrationShell shell = new AssetAdministrationShell("oven", OVENAASID, asset);

		// The manager uploads the AAS and registers it in the Registry server
		manager.createAAS(shell, AASSERVERPATH);

		// Create submodel
		Submodel documentationSubmodel = new Submodel("documentationSm", DOCUSMID);

		// - Create property
		Property maxTemp = new Property(MAXTEMPID, 1000);

		// Add the property to the Submodel
		documentationSubmodel.addSubmodelElement(maxTemp);

		// - Push the Submodel to the AAS server
		manager.createSubmodel(shell.getIdentification(), documentationSubmodel);
	}

	/**
	 * Starts an empty registry at "http://localhost:4000"
	 */
	private static void startAASRegistry() {
		RegistryComponent registry = configureAASRegistry();

		registry.startComponent();
	}

	private static RegistryComponent configureAASRegistry() {
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration(4000, "/registry");
		contextConfig.setAccessControlAllowOrigin("*");
		
		BaSyxRegistryConfiguration registryConfig = new BaSyxRegistryConfiguration(RegistryBackend.INMEMORY);
		
		return new RegistryComponent(contextConfig, registryConfig);
	}

	/**
	 * Startup an empty server at "http://localhost:4001/"
	 */
	private static void startAASServer() {
		AASServerComponent aasServer = configureAASServer();

		aasServer.startComponent();
	}

	private static AASServerComponent configureAASServer() {
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration(4001, "/aasServer");
		contextConfig.setAccessControlAllowOrigin("*");

		BaSyxAASServerConfiguration aasServerConfig = new BaSyxAASServerConfiguration();
		aasServerConfig.loadFromDefaultSource();

		return new AASServerComponent(contextConfig, aasServerConfig);
	}

	protected static void startMqttBroker() throws IOException {
		mqttBroker = new Server();
		IResourceLoader classpathLoader = new ClasspathResourceLoader();
		final IConfig classPathConfig = new ResourceLoaderConfig(classpathLoader);
		mqttBroker.startServer(classPathConfig);
	}
}
