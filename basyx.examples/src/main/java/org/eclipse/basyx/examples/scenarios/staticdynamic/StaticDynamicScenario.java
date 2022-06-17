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
package org.eclipse.basyx.examples.scenarios.staticdynamic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.eclipse.basyx.aas.aggregator.proxy.AASAggregatorProxy;
import org.eclipse.basyx.aas.bundle.AASBundle;
import org.eclipse.basyx.aas.bundle.AASBundleHelper;
import org.eclipse.basyx.aas.factory.aasx.AASXToMetamodelConverter;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.components.IComponent;
import org.eclipse.basyx.components.aas.AASServerComponent;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.registry.RegistryComponent;
import org.eclipse.basyx.components.registry.configuration.BaSyxRegistryConfiguration;
import org.eclipse.basyx.components.registry.configuration.RegistryBackend;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.xml.sax.SAXException;

/**
 * Example scenario showcasing how to enrich AAS data provided as AASX with
 * static data <br>
 * For this, a previously defined AASX package is loaded and a static submodel
 * is added to the already defined submodels <br>
 * For a detailed description of the scenario, see:<br>
 * https://wiki.eclipse.org/BaSyx_/_Scenarios_/_Static_Dynamic_Extension
 * 
 * @author schnicke, conradi
 *
 */
public class StaticDynamicScenario {

	public static final String REGISTRY_URL = "http://localhost:4000/registry";
	public static final String SERVER_URL = "http://localhost:4001/aasx/shells";

	public static final String AAS_ID = "smart.festo.com/demo/aas/1/1/454576463545648365874";
	public static final String AAS_ID_SHORT = "Festo_3S7PM0CP4BD";
	public static final String AAS_ENDPOINT = SERVER_URL + "shells/smart.festo.com%2Fdemo%2Faas%2F1%2F1%2F454576463545648365874/aas";

	private List<IComponent> startedComponents = new ArrayList<>();

	public static void main(String[] args) throws InvalidFormatException, IOException, ParserConfigurationException, SAXException {
		new StaticDynamicScenario();
	}

	public StaticDynamicScenario() throws InvalidFormatException, IOException, ParserConfigurationException, SAXException {

		// Startup the registry server
		startRegistry();

		// Startup the server
		startAASServer();

		// Load Bundles from .aasx file
		AASXToMetamodelConverter packageManager = new AASXToMetamodelConverter("aasx/01_Festo.aasx");
		Set<AASBundle> bundles = packageManager.retrieveAASBundles();

		// Create static Submodel
		Submodel sm = new ExampleDynamicSubmodel();

		// Get the correct Bundle from the Set
		AASBundle bundle = findBundle(bundles, AAS_ID_SHORT);

		// Add the new Submodel to the Bundle
		bundle.getSubmodels().add(sm);

		// Load the new Bundles to the Server
		AASBundleHelper.integrate(new AASAggregatorProxy(SERVER_URL), bundles);

		// Get a RegistryProxy and register all Objects contained in the Bundles
		AASRegistryProxy proxy = new AASRegistryProxy(REGISTRY_URL);
		AASBundleHelper.register(proxy, bundles, SERVER_URL);

	}

	/**
	 * Starts an empty registry at "http://localhost:4000"
	 */
	private void startRegistry() {
		// Load a registry context configuration using a .properties file
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration();
		contextConfig.loadFromResource("RegistryContext.properties");
		BaSyxRegistryConfiguration registryConfig = new BaSyxRegistryConfiguration(RegistryBackend.INMEMORY);
		RegistryComponent registry = new RegistryComponent(contextConfig, registryConfig);
		registry.startComponent();
		startedComponents.add(registry);
	}

	/**
	 * Startup an empty server at "http://localhost:4001/aasx/"
	 */
	private void startAASServer() {
		// Create a server at port 4001 with the endpoint "/aasx"
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration(4001, "/aasx");
		AASServerComponent aasServer = new AASServerComponent(contextConfig);

		// Start the created server
		aasServer.startComponent();
		startedComponents.add(aasServer);
	}

	/**
	 * Finds the Bundle containing the AAS with the specified IdShort
	 * 
	 * @param bundles
	 *            the Set of Bundles
	 * @param aasIdShort
	 *            the Id of the AAS of the wanted Bundle
	 * @return the Bundle containing the AAS with the specified Id or null if it
	 *         does not exist
	 */
	private AASBundle findBundle(Set<AASBundle> bundles, String aasIdShort) {
		for (AASBundle aasBundle : bundles) {
			if (aasBundle.getAAS().getIdShort().equals(aasIdShort))
				return aasBundle;
		}
		return null;
	}

	/**
	 * Shuts down all started IComponent servers
	 */
	public void stop() {
		startedComponents.stream().forEach(IComponent::stopComponent);
	}
}
