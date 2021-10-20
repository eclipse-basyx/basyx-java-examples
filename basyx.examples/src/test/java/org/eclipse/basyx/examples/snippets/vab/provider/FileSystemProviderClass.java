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
