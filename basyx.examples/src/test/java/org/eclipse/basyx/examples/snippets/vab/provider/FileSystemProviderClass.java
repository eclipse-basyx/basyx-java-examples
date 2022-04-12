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

import org.eclipse.basyx.vab.coder.json.serialization.DefaultTypeFactory;
import org.eclipse.basyx.vab.coder.json.serialization.GSONTools;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.modelprovider.filesystem.FileSystemProvider;
import org.eclipse.basyx.vab.modelprovider.filesystem.filesystem.FileSystem;
import org.eclipse.basyx.vab.modelprovider.filesystem.filesystem.GenericFileSystem;
import org.junit.Test;

/**
 * Code snippet showing the use of the VABFileSystemProvider. It can be used as a basic low level model provider for
 * arbitrary models based on HashMaps and a FileSystem. The provider encapsulates the actual data and implements the
 * abstract interface IModelProvider so that the contained model can be accessed via the five VAB primitives create,
 * retrieve, update, delete and invoke. The implementation abstracts the underlying file system, so it is possible to
 * provide different implementations.
 * 
 * @author espen
 *
 */
public class FileSystemProviderClass {
	private final GSONTools gsonTools = new GSONTools(new DefaultTypeFactory());

	/**
	 * Snippet showing the programmatic approach for creating arbitrary local, static models using the FileSystem
	 * provider
	 */
	@Test
	public void snippet() throws Exception {
		// Create a HashMap as the root for the data structure
		HashMap<String, Object> rootElement = new HashMap<>();

		// Additional properties can be directly added to the HashMap at this point
		rootElement.put("myInteger", 3);

		// Create a generic file system and specify the storage location
		// This exemplary file system is based on local files, but implementations are possible here
		FileSystem fs = new GenericFileSystem();
		String basePath = "regressiontest/HMDR/Test";

		// The provider then encapsulates the data and realizes the abstract IModelProvider interface
		// When initializing the provider, it is possible to optionally clear the provided directory
		IModelProvider provider = new FileSystemProvider(fs, basePath, rootElement, true);

		// The interface is equal to the interface used in e.g. the HashMapProvider
		int previouslyCreated = (Integer) provider.getValue("/myInteger");
		assertEquals(3, previouslyCreated);

		// When creating elements, the internal directory and file structure represent the changes
		provider.createValue("/name", "Max Mustermann");

		// Properties are serialized and stored in the file that corresponds to their path
		String expected = "Max Mustermann";
		String byProvider = (String) provider.getValue("/name");
		String byFileSystem = fs.readFile(basePath + "/name");
		String byFSDeserialized = (String) gsonTools.deserialize(byFileSystem);
		assertEquals(expected, byProvider);
		assertEquals(expected, byFSDeserialized);
	}
}
