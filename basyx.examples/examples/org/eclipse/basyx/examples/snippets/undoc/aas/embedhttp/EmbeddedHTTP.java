/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.snippets.undoc.aas.embedhttp;


import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.api.resources.ISingleProperty;
import org.eclipse.basyx.aas.api.resources.ISubmodel;
import org.eclipse.basyx.aas.backend.connected.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.aas.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.Submodel;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.examples.support.directory.ExamplesPreconfiguredDirectory;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.eclipse.basyx.vab.provider.lambda.VABLambdaProvider;
import org.junit.Test;




/**
 * Illustrate AAS hosted by embedded HTTP server
 * 
 * This example illustrates hosting of an AAS using the Embedded Webserver component. Even if this component is
 * in com.sun namespace, it is tagged jdoExport and therefore part of the official JDK API. 
 * 
 * @author kuhn
 *
 */
public class EmbeddedHTTP {

	
	/**
	 * Status sub model ID
	 */
	private static final String STATUS_SM = "de.FHG:devices.es.iese:statusSM:1.0:3:x-509#003";

	
	
	/**
	 * VAB connection manager backend
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(
			new ExamplesPreconfiguredDirectory()
				// Add example specific mappings
			    .addMapping(STATUS_SM,  "http://localhost:8000/BaSys/1.0/embedHTTP/"),
			new HTTPConnectorProvider());

	
	
	/**
	 * Example sub model. This example sub model is created with the BaSyx SDK factory and defines the AAS meta model properties
	 */
	static class SampleSubmodel extends Submodel {
		
		/**
		 * Version number of serialized instance
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor - create sub model
		 * 
		 * This sub model contains static properties, i.e. properties that have a static value assigned.
		 */
		public SampleSubmodel() {
			// Create factory that helps with property creation
			// - This factory creates sub model properties and ensures presence of all meta data
			MetaModelElementFactory fac = new MetaModelElementFactory();

			// Set sub model ID
			setId(STATUS_SM);
			
			// Create sub model properties
			getProperties().put(fac.create(new Property(),       7, "prop1"));
			getProperties().put(fac.create(new Property(), "myStr", "prop2"));
		}
	}

	
	
	/**
	 * Create sub model
	 */
	public void createSubmodel() {
		// Server connections
		// - Connect to sub model. Connecting to a sub model by its ID is discouraged, because a sub 
		//   model ID is not necessarily unique outside the scope of its AAS. If users want to connect
		//   directly to sub models, the registry needs to support this, and unique identifies (as here)
		//   must be used. For portability, users should connect to sub models instead via an AAS ID and 
		//   sub model ID tuple, as illustrated in the registry examples. 
		VABElementProxy connSubmodel1 = this.connManager.connectToVABElement(STATUS_SM);

		// Instantiate sub model
		Submodel submodel = new SampleSubmodel();

		// Transfer sub model to server
		connSubmodel1.createElement("aas/submodels/" + STATUS_SM, submodel);		
	}
	
	
	/**
	 * Test CRUD AAS access pattern
	 *   
	 * - Retrieve sub model with SDK connector
	 */
	public void testCRUDAAS() throws Exception {
		// Create and connect SDK connector
		ConnectedAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(connManager);
		// - Retrieve sub model
		ISubmodel subModel = manager.retrieveSM(STATUS_SM);

		// Read sub model properties
		String smId     = subModel.getId();
		String prop1Id  = subModel.getProperties().get("prop1").getId();
		int    prop1Val = (int) ((ISingleProperty) subModel.getProperties().get("prop1")).get();
		String prop2Id  = subModel.getProperties().get("prop2").getId();
		String prop2Val = (String) ((ISingleProperty) subModel.getProperties().get("prop2")).get();

		// Compare sub model property values
		assertTrue(smId.equals(STATUS_SM));
		assertTrue(prop1Id.equals("prop1"));
		assertTrue(prop1Val == 7);
		assertTrue(prop2Id.equals("prop2"));
		assertTrue(prop2Val.equals("myStr"));
	}
	
	
	
	/**
	 * Test AAS Invoke
	 */

	

	/**
	 * Main function
	 */
	@Test
	public void runTest() throws Exception {
		// Create and start embedded HTTP server
		EmbeddedHTTPServer server = new EmbeddedHTTPSubmodelServer("/BaSys/1.0/embedHTTP", new VABLambdaProvider(new SampleSubmodel()));
		// - Start server
		server.start();
		
		// Connect to server, create sub model, and test access to sub model
		createSubmodel();
		testCRUDAAS();

		// Stop HTTP server
		server.stop();
	}
}


