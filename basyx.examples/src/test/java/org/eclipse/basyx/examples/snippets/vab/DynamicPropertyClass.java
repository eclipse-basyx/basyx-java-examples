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

import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Supplier;

import org.eclipse.basyx.components.servlet.vab.VABLambdaServlet;
import org.eclipse.basyx.examples.contexts.BaSyxExamplesContext;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.examples.support.directory.ExamplesPreconfiguredDirectory;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProviderHelper;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorFactory;
import org.junit.ClassRule;
import org.junit.Test;


/**
 * The use of a Supplier class is an alternative approach for defining dynamic operations
 * 
 * Supplier classes implement get operations, but support more complex implementations
 * compared to Lambda expressions.
 * 
 * @author kuhn
 *
 */
class TailoredBaSyxSupplier implements Supplier<Object>, Serializable {

	/**
	 * Version number of serialized instances
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Getter operation - return property value
	 */
	@Override
	public Object get() {
		// Delegate call to tailored BaSyx supplier base class
		return getInternal();
	}

	
	/**
	 * Example function of tailored BaSyx supplier base class
	 */
	protected String getInternal() {
		// Return constant value
		return "BaSyxSupplier!";
	}
}




/**
 * Code snippet that illustrates the dynamic deployment of VAB object operations. This 
 * example defines the dynamic operations as a Class. This enables the operation implementations 
 * to define and maintain properties.
 * 
 * The snippet communicates with a VAB element that is deployed to a VABLambdaServlet on a
 * Apache Tomcat HTTP server instance. The VABLambdaServlet provides an empty container that
 * is able to host any VAB object. It supports dynamic properties with lambda expressions, 
 * i.e. all get/set/create/delete operations for every property may be replaced with a 
 * lambda operation.
 * 
 * @author kuhn
 *
 */
public class DynamicPropertyClass {

	
	/**
	 * VAB connection manager backend
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
	 * Run code snippet. This code snippet illustrates the creation and uploading 
	 * of dynamic operations on a EmptyVABLambdaElementServlet servlet. 
	 */
	@Test
	public void snippet() throws Exception {

		// Server connections
		// - Connect to device (VAB object)
		VABElementProxy connSubmodel1 = this.connManager.connectToVABElement("urn:de.FHG:devices.es.iese:statusSM:1.0:3:x-509#003");

		
		// Create dynamic get/set operation. Instantiate class TailoredBaSyxSupplier as getter, 
		// no setter (null) is provided.
		Map<String, Object> dynamicPropertyVal = VABLambdaProviderHelper.createSimple(new TailoredBaSyxSupplier(), null);
		// - Update property properties/dynamicExample with dynamic get/set operation
		connSubmodel1.createValue("dynamicExampleProperty", dynamicPropertyVal);

		// Read dynamicExample property
		Object propertyValue = connSubmodel1.getValue("dynamicExampleProperty");

		
		// Compare returned to expected values
		assertTrue(propertyValue.equals("BaSyxSupplier!"));
	}
}

