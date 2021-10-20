/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.snippets.submodel;


import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.aas.metamodel.map.descriptor.CustomId;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubmodel;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.vab.coder.json.connector.JSONConnector;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnector;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxHTTPServer;
import org.junit.After;
import org.junit.Test;

/**
 * Test for the HostPreconfiguredSubmodel snippet
 * 
 * @author schnicke
 *
 */
public class TestHostPreconfiguredSubmodel {

	// Used for test case tear down
	private BaSyxHTTPServer server;

	@Test
	public void testHostPreconfiguredSubmodel() {
		Submodel sm = new Submodel("testSubmodelIdShort", new CustomId("testSubmodel"));
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration(4040, "");
		server = HostPreconfiguredSubmodel.hostPreconfiguredSubmodel(contextConfig, sm);
		String smPath = "http://localhost:4040/" + sm.getIdShort() + "/submodel";

		// Here, for simplicity reason of the test, the ConnectedSubmodel is created by hand. 
		// In a real-world application, the AASManager would be used instead.
		ConnectedSubmodel cSm = new ConnectedSubmodel(new VABElementProxy("", new JSONConnector(new HTTPConnector(smPath))));
		assertEquals(sm.getIdentification(), cSm.getIdentification());
	}

	// Ensures that the started server is shut down regardless of test result
	@After
	public void tearDown() {
		if (server != null) {
			server.shutdown();
		}
	}
}
