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
package org.eclipse.basyx.examples.mockup.devicemanager;

import org.eclipse.basyx.aas.aggregator.restapi.AASAggregatorProvider;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.AASLambdaPropertyHelper;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.tools.aas.active.HTTPGetter;

/**
 * Example manufacturing device manager code
 * 
 * This manager extends class ManufacturingDeviceManager. It adds an active sub
 * model that dynamically queries the availability status of machine spare parts
 * from a supplier server.
 * 
 * 
 * @author kuhn
 *
 */
public class ManufacturingDeviceActiveAASManager extends ManufacturingDeviceManager {

	/**
	 * Constructor
	 */
	public ManufacturingDeviceActiveAASManager(int port) {
		// Invoke base constructor
		super(port);
	}

	/**
	 * Create the device AAS and sub model structure
	 */
	@Override
	protected void createDeviceAASAndSubmodels() {
		// Invoke base implementation
		super.createDeviceAASAndSubmodels();

		// Register URNs of managed VAB objects
		addShortcut("Supply", new ModelUrn("urn:de.FHG:devices.es.iese:supplySM:1.0:3:x-509#001"));

		// Create sub model
		Submodel supplySM = new Submodel();
		// - Set submodel ID
		supplySM.setIdShort("Supply");
		// - Property status: indicate device status
		Property availabililtyProp = new Property();
		// The device brings a sub model structure with an active AAS part
		// - Create dynamic get/set operation as lambda expression
		AASLambdaPropertyHelper.setLambdaValue(availabililtyProp, new HTTPGetter("http://localhost:8080/basys.examples/Mockup/Supplier"), null);
		availabililtyProp.setIdShort("partAvailability");
		supplySM.addSubmodelElement(availabililtyProp);

		// Transfer device sub model to server
		aasServerConnection.setValue("/" + AASAggregatorProvider.PREFIX + "/" + lookupURN("AAS").getEncodedURN() + "/aas/submodels/" + supplySM.getIdShort(), supplySM);
	}

	/**
	 * Get AAS descriptor for managed device
	 */
	@Override
	protected AASDescriptor getAASDescriptor() {
		// Create AAS and sub model descriptors
		AASDescriptor aasDescriptor = new AASDescriptor(lookupURN("AAS"), getAASEndpoint(lookupURN("AAS")));
		addSubmodelDescriptorURI(aasDescriptor, lookupURN("Status"), "Status");
		addSubmodelDescriptorURI(aasDescriptor, lookupURN("Supply"), "Supply");

		// Return AAS and sub model descriptors
		return aasDescriptor;
	}
}
