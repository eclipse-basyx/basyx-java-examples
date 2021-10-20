/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.snippets.vab.provider;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProvider;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProviderHelper;
import org.junit.Test;

/**
 * Code snippet showing the use of the VABLambdaProvider. It can be used as a basic low level model provider for
 * arbitrary models based on HashMaps. The provider encapsulates the actual data and implements the abstract interface
 * IModelProvider so that the contained model can be accessed via the five VAB primitives create, retrieve, update,
 * delete and invoke. In addition to the VABHashMapProvider, the VABLambdaProvider can proxy calls to properties and
 * operations to user-defined functions to enable dynamic properties.
 * 
 * @author espen
 *
 */
public class VABLambdaProviderClass {
	private String dynamicString = "myDynamicString";

	/**
	 * Snippet showing the programmatic approach for creating arbitrary local, static models using HashMaps
	 */
	@Test
	public void snippet() throws Exception {
		// Create a HashMap as the root for the data structure
		HashMap<String, Object> rootElement = new HashMap<>();

		// Create and add a dynamic string property by providing the necessary get and set operations.
		Map<String, Object> dynamicPropertyVal = VABLambdaProviderHelper.createSimple(() -> {
			// A user defined get-operation is provided here
			return dynamicString;
		}, (Object newValue) -> {
			// A user defined set-operation is provided here
			if (newValue instanceof String) {
				dynamicString = ((String) newValue);
			}
		});
		rootElement.put("dynamic", dynamicPropertyVal);

		// Since the VABLambdaProvider is also a HashMapProvider, static properties can be added as well
		rootElement.put("static", "myStaticString");

		// The provider encapsulates the data and realizes the abstract IModelProvider interface
		IModelProvider provider = new VABLambdaProvider(rootElement);

		// Static and dynamic properties are resolved to their primitive object value
		assertEquals("myStaticString", provider.getValue("/static"));
		assertEquals("myDynamicString", provider.getValue("/dynamic"));

		// Now each time the dynamic property is read, it resolves its value through the given get operation
		// - modify the source of the dynamic property
		dynamicString = "newValue";
		// - resolve the property
		assertEquals("newValue", provider.getValue("/dynamic"));

		// The custom setter of the dynamic property in this example only allows string types...
		provider.setValue("/dynamic", true);
		assertEquals("newValue", provider.getValue("/dynamic"));
	}
}
