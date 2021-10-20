/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.mockup.application;

import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.components.service.BaseBaSyxService;
import org.eclipse.basyx.examples.contexts.BaSyxExamplesContext;
import org.eclipse.basyx.examples.support.directory.ExamplesPreconfiguredDirectory;
import org.eclipse.basyx.submodel.restapi.MultiSubmodelElementProvider;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorFactory;

/**
 * Example BaSys 4.0 application that monitors device (execution) status changes and device service invocation counters
 * 
 * @author kuhn
 *
 */
public class ReceiveDeviceDashboardStatusApplication extends BaseBaSyxService {

	
	/**
	 * AAS server connection
	 */
	protected VABElementProxy aasServerConnection = null;

	
	
	/**
	 * Constructor
	 */
	public ReceiveDeviceDashboardStatusApplication() {
		// Create AAS registry for this service
		setRegistry(new AASRegistryProxy("http://localhost:8080/" + BaSyxExamplesContext.REGISTRYURL));
		
		// Service connection manager
		setConnectionManager(new VABConnectionManager(new ExamplesPreconfiguredDirectory(), new HTTPConnectorFactory()));

		// Register URNs of used objects
		addShortcut("AAS",        new ModelUrn("urn:de.FHG:devices.es.iese:aas:1.0:3:x-509#001"));
		addShortcut("Status",     new ModelUrn("urn:de.FHG:devices.es.iese:statusSM:1.0:3:x-509#001"));
	}

	
	/**
	 * Start application
	 */
	@Override
	public void start() {
		// Base implementation
		super.start();

		// Create connection to device sub model
		// - This code assumes that network location of device sub model does not change while application is running
		AASDescriptor      aasDescriptor = getRegistry().lookupAAS(lookupURN("AAS"));
		SubmodelDescriptor smDescriptor  = aasDescriptor.getSubmodelDescriptor(lookupURN("Status"));
		// - Connect to status sub model end point
		aasServerConnection = getConnectionManager().connectToVABElementByPath(smDescriptor.getFirstEndpoint());		
	}
	

	/**
	 * Receive device status
	 */
	@SuppressWarnings("unchecked")
	public String getDeviceStatus() {
		// Read the status property
		Map<String, Object> property = (Map<String, Object>) aasServerConnection
				.getValue(MultiSubmodelElementProvider.ELEMENTS + "/status");
		// Return the value of the property
		return property.get("value").toString();
	}


	/**
	 * Receive device invocation counter
	 */
	@SuppressWarnings("unchecked")
	public int getDeviceInvocationCounter() {
		// Read the invocation counter for device default service
		Map<String, Object> property = (Map<String, Object>) aasServerConnection
				.getValue(MultiSubmodelElementProvider.ELEMENTS + "/invocations");
		// Return the value of the property
		return (int) property.get("value");
	}
}

