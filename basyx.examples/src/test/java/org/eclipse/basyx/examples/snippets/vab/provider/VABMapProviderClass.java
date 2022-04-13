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
import java.util.function.Function;

import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.modelprovider.map.VABMapProvider;
import org.junit.Test;

/**
 * Code snippet showing the use of the VABMapProvider. It can be used as a basic
 * low level model provider for arbitrary models based on HashMaps. The provider
 * encapsulates the actual data and implements the abstract interface
 * IModelProvider so that the contained model can be accessed via the five VAB
 * primitives create, retrieve, update, delete and invoke.
 * 
 * @author espen
 *
 */
public class VABMapProviderClass {

	/**
	 * Snippet showing the programmatic approach for creating arbitrary local,
	 * static models using HashMaps
	 */
	@Test
	public void snippet() throws Exception {
		// Create a HashMap as the root for the data structure
		HashMap<String, Object> rootElement = new HashMap<>();

		// Add a simple string element
		rootElement.put("name", "myElement");

		// Create a function and then add it as another element to the root
		Function<Object[], Object> myFunction = (param) -> {
			return (int) param[0] + (int) param[1];
		};
		rootElement.put("operation", myFunction);

		// Hierarchical structures are possible by nesting multiple HashMaps
		// => Create a new HashMap with two elements and add it as a child to the root
		// Map
		HashMap<String, Object> childMap = new HashMap<>();
		childMap.put("type", "boolean");
		childMap.put("value", true);
		rootElement.put("data", childMap);

		// The provider then encapsulates the data and realizes the abstract
		// IModelProvider interface
		IModelProvider provider = new VABMapProvider(rootElement);

		// Child elements can now be accessed with a path that is mapped to actual data
		// structure
		assertEquals("myElement", provider.getValue("/name"));
		assertEquals("boolean", provider.getValue("/data/type"));
		assertEquals(true, provider.getValue("/data/value"));

		// Future modifications are now applied using the IModelProvider interface.
		provider.setValue("/name", "yourElement");
		assertEquals("yourElement", provider.getValue("/name"));

		// The creation of nested elements within an existing provider is also possible
		// HashMaps and Collections are supported for this purpose
		HashMap<String, Object> description = new HashMap<>();
		description.put("DE", "Dies ist ein generisches Element für den VAB");
		description.put("EN", "This is a generic VAB element");
		provider.createValue("/description", description);

		// The path is again mapped to the HashMap structure
		assertEquals("This is a generic VAB element", provider.getValue("/description/EN"));
	}
}
