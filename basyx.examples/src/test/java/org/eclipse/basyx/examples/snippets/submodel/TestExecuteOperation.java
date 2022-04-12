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

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;

import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.servlet.submodel.SubmodelServlet;
import org.eclipse.basyx.examples.snippets.AbstractSnippetTest;
import org.eclipse.basyx.examples.support.ExampleComponentBuilder;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;
import org.eclipse.basyx.submodel.restapi.SubmodelProvider;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxHTTPServer;
import org.junit.After;
import org.junit.Test;

/**
 * Test for the ExecuteOperation snippet
 * 
 * @author conradi
 *
 */
public class TestExecuteOperation extends AbstractSnippetTest {

	private static final String NEW_SM_ID_SHORT = "smIdShort_New";
	private static final String NEW_SM_ID = "smId_New";
	
	private static final String OPERATION_ID = "operation";
	
	private static final Object[] PARAMETERS = {2, 3};
	private static final int EXPECTED_RESULT = 5;
	
	private BaSyxHTTPServer server;
	
	@After
	public void shutdownServer() {
		server.shutdown();
	}
	
	@Test
	public void testExecuteOperation() throws Exception {
		
		Submodel submodel = ExampleComponentBuilder.buildExampleSubmodel(NEW_SM_ID_SHORT, NEW_SM_ID);
		
		// Add an Operation which calculates the sum of the two parameters
		Operation operation = new Operation((Function<Object[], Object>) v -> {
			return (int) v[0] + (int) v[1];
		});
		OperationVariable a = new OperationVariable(new Property("a", 1));
		OperationVariable b = new OperationVariable(new Property("b", 2));
		OperationVariable r = new OperationVariable(new Property("r", 3));
		operation.setInputVariables(Arrays.asList(a, b));
		operation.setOutputVariables(Collections.singletonList(r));
		operation.setIdShort(OPERATION_ID);
		submodel.addSubmodelElement(operation);
		
		// Startup a Server hosting this Submodel
		provideSubmodel(submodel);
		
		
		// Get the Identifiers of the AAS and Submodel
		IIdentifier aasIdentifier = new Identifier(IdentifierType.CUSTOM, AAS_ID);
		IIdentifier smIdentifier = new Identifier(IdentifierType.CUSTOM, NEW_SM_ID);
		
		// Execute the Operation and get the result
		Object result = ExecuteOperation.executeOperation(OPERATION_ID, PARAMETERS, smIdentifier, aasIdentifier, registryComponent.getRegistryPath());
		
		// Check if the result is the expected value
		assertEquals(EXPECTED_RESULT, result);
		
	}
	
	/**
	 * Starts a new server hosting a given Submodel
	 * This is necessary as an Operation can not be transfered to a server via serialization
	 * 
	 * @param submodel the Submodel to be hosted
	 */
	private void provideSubmodel(Submodel submodel) {
		// Create a BaSyxConetxt for port 8082 with an empty endpoint
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration(8082, "");
		BaSyxContext context = contextConfig.createBaSyxContext();
		
		// Create a new SubmodelServlet containing the submodel
		SubmodelServlet smServlet = new SubmodelServlet(submodel);
		
		// Add the SubmodelServlet mapping to the context at the path "/submodel"
		context.addServletMapping("/submodel/*", smServlet);
		
		
		// Create and start a HTTP server with the context created above
		server = new BaSyxHTTPServer(context);
		server.start();
		
		IIdentifier aasIdentifier = new Identifier(IdentifierType.CUSTOM, AAS_ID);
		SubmodelDescriptor descriptor = new SubmodelDescriptor(submodel, "http://localhost:8082/submodel/" + SubmodelProvider.SUBMODEL);
		
		// Register the new Submodel
		AASRegistryProxy registry = new AASRegistryProxy(registryComponent.getRegistryPath());
		registry.register(aasIdentifier, descriptor);
	}
	
}
