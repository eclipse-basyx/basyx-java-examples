/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.mockup.devicemanager;

import java.util.HashMap;

import org.eclipse.basyx.aas.aggregator.restapi.AASAggregatorProvider;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.components.configuration.CFGBaSyxProtocolType;
import org.eclipse.basyx.components.devicemanager.TCPDeviceManagerComponent;
import org.eclipse.basyx.examples.contexts.BaSyxExamplesContext;
import org.eclipse.basyx.examples.support.directory.ExamplesPreconfiguredDirectory;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.restapi.MultiSubmodelElementProvider;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorFactory;

/**
 * Example manufacturing device manager code
 * 
 * This example code illustrates a basic device manager component. It implements the interaction between a device and the BaSyx infrastructure.
 * This code is for example deployed on the device (in case of availability of a Java runtime environment) or to an explicit connector device.
 * The Asset Administration Shell is not kept on the device, but transferred to an AAS server during registration. This ensures its presence also
 * if the device itself is not available, e.g. due to a failure. Important asset data, such as manufacturer, and support contacts remain available
 * in this case.
 * 
 * This code implements the following:
 * - Registration of device the AAS and sub models with the BaSyx infrastructure
 * - Updating of sub model properties to reflect the device status
 * - TCP connection to legacy device
 * 
 * 
 * @author kuhn
 *
 */
public class ManufacturingDeviceManager extends TCPDeviceManagerComponent {

	
	/**
	 * AAS server connection
	 */
	protected VABElementProxy aasServerConnection = null;






	/**
	 * Constructor
	 */
	public ManufacturingDeviceManager(int port) {
		// Invoke base constructor
		super(port);
		
		
		// Configure this device manager
		configure()
				.registryURL("http://localhost:8080/" + BaSyxExamplesContext.REGISTRYURL)
			.connectionManagerType(CFGBaSyxProtocolType.HTTP)
				.directoryService(new ExamplesPreconfiguredDirectory())
			.end();
		
		// Set registry that will be used by this service
		setRegistry(new AASRegistryProxy("http://localhost:8080/" + BaSyxExamplesContext.REGISTRYURL));
		
		
		// Set service connection manager and create AAS server connection
		setConnectionManager(new VABConnectionManager(new ExamplesPreconfiguredDirectory(), new HTTPConnectorFactory()));
		// - Create AAS server connection
		aasServerConnection = getConnectionManager().connectToVABElement("AASServer");
		
		
		// Set AAS server VAB object ID, AAS server URL, and AAS server path prefix
		setAASServerObjectID("AASServer");
		setAASServerURL("http://localhost:8080/" + BaSyxExamplesContext.AASSERVERURL);
	}



	/**
	 * Initialize the device, and register it with the backend
	 */
	@Override 
	public void start() {
		// Base implementation
		super.start();
		
		// Create the device AAS and sub model structure
		createDeviceAASAndSubmodels();
		
		// Register AAS and sub model descriptors in directory (push AAS descriptor to server)
		getRegistry().register(getAASDescriptor());
	}
	
	
	/**
	 * Get AAS descriptor for managed device
	 */
	@Override 
	protected AASDescriptor getAASDescriptor() {
		// Create AAS and sub model descriptors
		AASDescriptor aasDescriptor = new AASDescriptor(lookupURN("AAS"), getAASEndpoint(lookupURN("AAS")));
		addSubmodelDescriptorURI(aasDescriptor, lookupURN("Status"), "Status");
		
		// Return AAS and sub model descriptors
		return aasDescriptor;
	}

	
	
	/**
	 * Create the device AAS and sub model structure
	 */
	protected void createDeviceAASAndSubmodels() {
		// Register URNs of managed VAB objects
		addShortcut("AAS",        new ModelUrn("urn:de.FHG:devices.es.iese:aas:1.0:3:x-509#001"));
		addShortcut("Status",     new ModelUrn("urn:de.FHG:devices.es.iese:statusSM:1.0:3:x-509#001"));

		// Create device AAS
		AssetAdministrationShell aas = new AssetAdministrationShell();
		// - Populate AAS
		aas.setIdShort("DeviceIDShort");
		aas.setIdentification(lookupURN("AAS"));
		// - Transfer device AAS to server
		aasServerConnection.setValue(VABPathTools.concatenatePaths(AASAggregatorProvider.PREFIX, VABPathTools.encodePathElement(aas.getIdentification().getId())), aas);

	
		// The device also brings a sub model structure with an own ID that is being pushed on the server
		// - Create generic sub model and add properties
		Submodel statusSM = new Submodel();
		// - Set submodel ID
		statusSM.setIdShort("Status");
		//   - Property status: indicate device status
		Property statusProp = new Property("offline");
		statusProp.setIdShort("status");
		statusSM.addSubmodelElement(statusProp);
		//   - Property statistics: export invocation statistics for every service
		//     - invocations: indicate total service invocations. Properties are not persisted in this example,
		//                    therefore we start counting always at 0.
		Property invocationsProp = new Property(0);
		invocationsProp.setIdShort("invocations");
		statusSM.addSubmodelElement(invocationsProp);
		// - Transfer device sub model to server
		aasServerConnection.setValue(AASAggregatorProvider.PREFIX + "/" + VABPathTools.encodePathElement(lookupURN("AAS").getId()) + "/aas/submodels/" + statusSM.getIdShort(), statusSM);
	}


	
	
	
	
	/**
	 * Received a string from network
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void onReceive(byte[] rxData) {
		// Do not process null values
		if (rxData == null) return;
		
		String aasPath = "/" + AASAggregatorProvider.PREFIX + "/" + VABPathTools.encodePathElement(lookupURN("AAS").getId());

		// Convert received data to string
		String rxStr = new String(rxData); 
		// - Trim string to remove possibly trailing and leading white spaces
		rxStr = rxStr.trim();
		
		// Check what was being received. This check is performed based on a prefix that he device has to provide);
		// - Update of device status
		if (hasPrefix(rxStr, "status:"))
			aasServerConnection.setValue(aasPath + "/aas/submodels/Status/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "/status/value", removePrefix(rxStr, "status"));
		// - Device indicates service invocation
		if (hasPrefix(rxStr, "invocation:")) {
			// Start of process
			if (hasPrefix(rxStr, "invocation:start")) {
				// Read and increment invocation counter
				HashMap<String, Object> property = (HashMap<String, Object>) aasServerConnection.getValue(aasPath + "/aas/submodels/Status/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "/invocations");
				int invocations = (int) property.get("value");
				aasServerConnection.setValue(aasPath + "/aas/submodels/Status/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "/invocations/value", ++invocations);
			} 
			// End of process
			if (hasPrefix(rxStr, "invocation:end")) {
				// Do nothing for now
			}
		}
	}
}
