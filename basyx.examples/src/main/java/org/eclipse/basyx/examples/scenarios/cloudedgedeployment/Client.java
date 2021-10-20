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

import java.util.Set;

import org.eclipse.basyx.aas.aggregator.api.IAASAggregator;
import org.eclipse.basyx.aas.aggregator.proxy.AASAggregatorProxy;
import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.CustomId;
import org.eclipse.basyx.extensions.aas.directory.tagged.api.IAASTaggedDirectory;
import org.eclipse.basyx.extensions.aas.directory.tagged.api.TaggedAASDescriptor;
import org.eclipse.basyx.extensions.aas.directory.tagged.proxy.TaggedDirectoryProxy;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;

public class Client {
	public static String directoryPath = "http://localhost:8080/registry";
	public static String aggregatorPath = "http://localhost:8080/registry";

	private static ConnectedAssetAdministrationShellManager aasManager;

	public static void main(String[] args) {
		// Create the directory proxy
		IAASTaggedDirectory directory = new TaggedDirectoryProxy(directoryPath);

		// Create a ConnectedAASManager with the registry created above
		aasManager = new ConnectedAssetAdministrationShellManager(directory);

		// Get all descriptors and filter for newly added descriptors
		Set<TaggedAASDescriptor> descriptors = directory.lookupTag("SensorDevice");
		Set<TaggedAASDescriptor> newDescriptors = checkForNewDescriptors(descriptors);

		// ...

		TaggedAASDescriptor ovenDescriptor = null;
		// Exemplary processing of the newly added oven descriptor
		IAssetAdministrationShell oven = aasManager.retrieveAAS(ovenDescriptor.getIdentifier());

		// Retrieve the documentation submodel
		ISubmodel docuSM = oven.getSubmodel(new CustomId("icsa21.docuSM"));

		// Retrieve the maxTemp Property
		int maxTemp = (int) docuSM.getSubmodelElement("maxTemp").getValue();

		// ... further processing of the AAS and its data

	}

	private static TaggedAASDescriptor selectFromNewDescriptors(Set<TaggedAASDescriptor> newDescriptors) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void server() {
		// Create the proxy to the Tagged AAS Directory
		IAASTaggedDirectory directory = new TaggedDirectoryProxy(directoryPath);
		
		// Create the proxy to the AAS Cloud Server
		IAASAggregator cloudServer = new AASAggregatorProxy(aggregatorPath);

		AssetAdministrationShell ovenAAS = null;
		
		// Register the newly added device with the "sensorDevice" tag
		TaggedAASDescriptor desc = new TaggedAASDescriptor(ovenAAS, aggregatorPath);
		desc.addTag("sensorDevice");
		directory.register(desc);
		
	}

	private static Set<TaggedAASDescriptor> checkForNewDescriptors(Set<TaggedAASDescriptor> descriptors) {
		return descriptors;
	}
}
