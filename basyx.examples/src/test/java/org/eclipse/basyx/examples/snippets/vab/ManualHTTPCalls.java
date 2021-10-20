/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.snippets.vab;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.eclipse.basyx.components.servlet.vab.VABLambdaServlet;
import org.eclipse.basyx.examples.contexts.BaSyxExamplesContext;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.examples.support.directory.ExamplesPreconfiguredDirectory;
import org.eclipse.basyx.tools.webserviceclient.WebServiceJSONClient;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorFactory;
import org.junit.ClassRule;

/**
 * Code snippet that illustrates the use of VABvia HTTP REST calls
 * 
 * The snippet communicates with a VAB element that is deployed to a VABLambdaServlet on a
 * Apache Tomcat HTTP server instance. The VABLambdaServlet provides an empty container that
 * is able to host any VAB object.
 * 
 * @author kuhn
 *
 */
public class ManualHTTPCalls {


	/**
	 * Create VAB connection manager backend
	 * 
	 * The connection manager uses a preconfigured directory for resolving IDs to 
	 * network addresses, and a HTTP connector to connect to VAB objects.
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(
			new ExamplesPreconfiguredDirectory()
				// Add example specific mappings
			    .addMapping("urn:de.FHG:devices.es.iese:statusSM:1.0:3:x-509#003",  "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/devicestatusVAB/"),
			new HTTPConnectorFactory());

	
	/**
	 * The BaSyx Deployment instantiates and starts context elements for this example. 
	 * 
	 * This example instantiates the BaSyxExamplesContext_1MemoryAASServer_1SQLDirectory
	 * example context that creates one AAS server, and one SQL based AAS registry.
	 * 
	 * BaSyxDeployment contexts instantiate all components on the IP address of the host. 
	 * Therefore, all components use the same IP address. 
	 */
	@ClassRule
	public static BaSyxDeployment context = new BaSyxDeployment(
				// Simulated servlets
				// - BaSys topology with one AAS Server and one SQL directory
				new BaSyxExamplesContext().
					// Deploy example specific servlets to Tomcat server in this context
					addServletMapping("/Testsuite/components/BaSys/1.0/devicestatusVAB/*", new VABLambdaServlet())
			);

	
	
	
	/**
	 * Run code snippet. This code snippet illustrates the use of HTTP operations to 
	 * access Virtual Automation Bus objects
	 */
	public void snippet() throws Exception {

		// Server connections
		// - Connect to VAB object by ID. The connection manager looks up this ID in
		//   its directory
		VABElementProxy connSubmodel1 = this.connManager.connectToVABElement("urn:de.FHG:devices.es.iese:statusSM:1.0:3:x-509#003");

		int prop1Val = 7;
		String prop2Val = "myStr";
		// Create properties in VAB object using connection
		// - This code creates a simple container "properties" and two contained
		//   properties "prop1" and "prop2". Container and properties lack the 
		//   required properties for AAS and AAS sub models. They are therefore
		//   not compliant to Asset Administration Shells.
		connSubmodel1.createValue("properties", new HashMap<String, Object>());
		connSubmodel1.createValue("properties/prop1", prop1Val);
		connSubmodel1.createValue("properties/prop2", prop2Val);
		
		
		// Web service client 
		// - This client enables the execution of generic HTTP queries that expect
		//   and return JSON encoded values. 
		WebServiceJSONClient jsonClient = new WebServiceJSONClient();
		
		
		// Read property values
		// - Use WebServiceJSONClient class.
		assertEquals(prop1Val, jsonClient.get("http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/devicestatusVAB/properties/prop1"));
		assertEquals(prop2Val, jsonClient.get("http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/devicestatusVAB/properties/prop2"));

	}
}

