/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.snippets.property;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.aas.metamodel.map.descriptor.CustomId;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.examples.snippets.submodel.HostPreconfiguredSubmodel;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubmodel;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetype.ValueType;
import org.eclipse.basyx.vab.coder.json.connector.JSONConnector;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnector;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxHTTPServer;
import org.junit.After;
import org.junit.Test;

/**
 * Test for the CreateLambdaProperty snippet
 * 
 * @author schnicke
 *
 */
public class TestCreateLambdaProperty {

	// Value to be facaded by the lambda property
	private int testValue = 15;

	// Used for test case tear down
	private BaSyxHTTPServer server;

	@Test
	public void testCreateLambdaProperty() {
		// The type cast in the setter is necessary due to the Consumer<Object> definition
		Property lambdaProp = CreateLambdaProperty.createLambdaProperty("lambdaProp", ValueType.Integer, () -> testValue, (v) -> testValue = (int) v);

		// Package it in a test submodel and host it
		Submodel sm = new Submodel("testSubmodelIdShort", new CustomId("testSubmodelId"));
		sm.addSubmodelElement(lambdaProp);
		
		// Create the context configuration and host the submodel containg the lambda property
		BaSyxContextConfiguration config = new BaSyxContextConfiguration(4040, "");
		server = HostPreconfiguredSubmodel.hostPreconfiguredSubmodel(config, sm);

		// Here, for simplicity reason of the test, the ConnectedSubmodel is created by hand.
		// In a real-world application, the AASManager would be used instead.
		String smPath = "http://localhost:4040/" + sm.getIdShort() + "/submodel";

		ConnectedSubmodel cSm = new ConnectedSubmodel(new VABElementProxy("", new JSONConnector(new HTTPConnector(smPath))));
		IProperty cLambdaProp = (IProperty) cSm.getSubmodelElement(lambdaProp.getIdShort());

		// Test getting
		assertEquals(testValue, cLambdaProp.getValue());

		// Test setting
		int newVal = 100;
		cLambdaProp.setValue(newVal);
		assertEquals(newVal, cLambdaProp.getValue());
		assertEquals(newVal, testValue);
	}

	// Ensures that the started server is shut down regardless of test result
	@After
	public void tearDown() {
		if (server != null) {
			server.shutdown();
		}
	}
}
