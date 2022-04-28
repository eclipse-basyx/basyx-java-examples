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
package org.eclipse.basyx.dashboard;

import java.util.ArrayList;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.aas.registration.api.IAASRegistry;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.aas.restapi.AASModelProvider;
import org.eclipse.basyx.aas.restapi.MultiSubmodelProvider;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.restapi.SubmodelProvider;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Executable for a very basic, dynamic AAS, that is meant to be used in an example about how
 * to integrate properties of "real" AAS in a dashboard.
 * 
 * @author espen
 *
 */
public class AASExecutable {
	private static final Logger logger = LoggerFactory.getLogger(AASExecutable.class);

	public static final String HOST = "dashboard-aas";
	public static final int PORT = 6400;
	public static final String REGISTRY_ENDPOINT = "http://registry:4000/registry/";

	public static void main(String[] args) throws Exception {
		// Use docker-compose health check for registry instead
		Thread.sleep(3000);

		logger.info("Creating AAS...");
		AssetAdministrationShell aas = new DashboardAssetAdministrationShell();
		Asset asset = new DashboardAsset();
		aas.setAsset(asset);
		Submodel sm = new DashboardSubmodel();
		aas.setConceptDictionary(new ArrayList<>());
		aas.addSubmodel(sm);
		
		logger.info("Starting aas servlet...");
		createServlet(aas, sm);
		logger.info("Registering aas...");
		AASDescriptor descriptor = new AASDescriptor(aas, "http://" + HOST + ":" + PORT + "/aas");
		descriptor.addSubmodelDescriptor(
				new SubmodelDescriptor(sm, "http://" + HOST + ":" + PORT + "/aas/submodels/DashboardSubmodel/"));
		registerAAS(descriptor, REGISTRY_ENDPOINT);

		logger.info("Finished");
	}

	private static void registerAAS(AASDescriptor descriptor, String registryEndpoint) throws InterruptedException {
		IAASRegistry registry = new AASRegistryProxy(registryEndpoint);
		// Quick & dirty, try to register until registry is up
		for (int i = 0; i < 10; i++) {
			try {
				registry.register(descriptor);
				break;
			} catch (Exception e) {
				Thread.sleep(2000);
			}
		}
	}

	private static void createServlet(AssetAdministrationShell aas, Submodel... submodels) {
		MultiSubmodelProvider provider = new MultiSubmodelProvider();
		provider.setAssetAdministrationShell(new AASModelProvider(aas));
		for (Submodel sm : submodels) {
			provider.addSubmodel(new SubmodelProvider(sm));
		}
		BaSyxContextConfiguration config = new BaSyxContextConfiguration();
		config.loadFromDefaultSource();
		BaSyxContext context = config.createBaSyxContext();
		context.addServletMapping("/*", new VABHTTPInterface<IModelProvider>(provider));
        BaSyxHTTPServer server = new BaSyxHTTPServer(context);
		server.start();
	}
}
