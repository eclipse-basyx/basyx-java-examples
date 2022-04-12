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
