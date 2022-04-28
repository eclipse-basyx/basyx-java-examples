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
package org.eclipse.basyx.examples.snippets.vab;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.eclipse.basyx.components.servlet.vab.VABLambdaServlet;
import org.eclipse.basyx.examples.contexts.BaSyxExamplesContext;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.examples.support.directory.ExamplesPreconfiguredDirectory;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorFactory;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * Code snippet that illustrates the use of CRUD Virtual Automation Bus (VAB)
 * operations
 * 
 * The snippet communicates with a VAB element that is deployed to a
 * VABLambdaServlet on a Apache Tomcat HTTP server instance. The
 * VABLambdaServlet provides an empty container that is able to host any VAB
 * object.
 * 
 * @author kuhn
 *
 */
public class CRUDOperations {

	/**
	 * Create VAB connection manager backend
	 * 
	 * The connection manager uses a preconfigured directory for resolving IDs to
	 * network addresses, and a HTTP connector to connect to VAB objects.
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(new ExamplesPreconfiguredDirectory()
			// Add example specific mappings
			.addMapping("urn:de.FHG:devices.es.iese:statusSM:1.0:3:x-509#003", "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/devicestatusVAB/"), new HTTPConnectorFactory());

	/**
	 * The BaSyx Deployment instantiates and starts context elements for this
	 * example.
	 * 
	 * This example instantiates the
	 * BaSyxExamplesContext_1MemoryAASServer_1SQLDirectory example context that
	 * creates one AAS server, and one SQL based AAS registry.
	 * 
	 * BaSyxDeployment contexts instantiate all components on the IP address of the
	 * host. Therefore, all components use the same IP address.
	 */
	@ClassRule
	public static BaSyxDeployment context = new BaSyxDeployment(
			// Simulated servlets
			// - BaSys topology with one AAS Server and one SQL directory
			new BaSyxExamplesContext().
			// Deploy example specific servlets to Tomcat server in this context
					addServletMapping("/Testsuite/components/BaSys/1.0/devicestatusVAB/*", new VABLambdaServlet()));

	/**
	 * Run code snippet. This code snippet illustrates the use of CRUD operations to
	 * access Virtual Automation Bus objects
	 */
	@Test
	public void snippet() throws Exception {

		// Server connections
		// - Connect to VAB object by ID. The connection manager looks up this ID in
		// its directory
		VABElementProxy connSubmodel1 = this.connManager.connectToVABElement("urn:de.FHG:devices.es.iese:statusSM:1.0:3:x-509#003");

		// Create properties on AAS
		// - This code creates a simple container "properties" and two contained
		// properties "prop1" and "prop2". Container and properties lack the
		// required properties for AAS and AAS sub models. They are therefore
		// not compliant to Asset Administration Shells.
		connSubmodel1.createValue("properties", new HashMap<String, Object>());
		connSubmodel1.createValue("properties/prop1", 7);
		connSubmodel1.createValue("properties/prop2", "myStr");

		// Read property values
		int prop1Val = (int) connSubmodel1.getValue("properties/prop1");
		String prop2Val = (String) connSubmodel1.getValue("properties/prop2");

		// Update property values
		connSubmodel1.setValue("properties/prop1", 8);
		connSubmodel1.setValue("properties/prop2", "stillMine");

		// Read property values again
		int prop1Val_2 = (int) connSubmodel1.getValue("properties/prop1");
		String prop2Val_2 = (String) connSubmodel1.getValue("properties/prop2");

		// Delete property values
		connSubmodel1.deleteValue("properties/prop1");
		connSubmodel1.deleteValue("properties/prop2");

		// Read property values again
		try {
			connSubmodel1.getValue("properties/prop1");
			fail();
		} catch (ResourceNotFoundException e) {
		}
		try {
			connSubmodel1.getValue("properties/prop2");
			fail();
		} catch (ResourceNotFoundException e) {
		}

		// Check expected values from CRUD operations
		assertTrue(prop1Val == 7);
		assertTrue(prop2Val.equals("myStr"));
		assertTrue(prop1Val_2 == 8);
		assertTrue(prop2Val_2.equals("stillMine"));
	}
}
