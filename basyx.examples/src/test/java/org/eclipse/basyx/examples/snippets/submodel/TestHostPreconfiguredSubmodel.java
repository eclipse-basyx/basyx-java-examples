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

		// Here, for simplicity reason of the test, the ConnectedSubmodel is created by
		// hand.
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
