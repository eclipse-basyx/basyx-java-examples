/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.support.directory;

import org.eclipse.basyx.vab.registry.memory.VABInMemoryRegistry;

/**
 * Implement the examples directory service with pre-configured directory entries
 * 
 * @author kuhn
 *
 */
public class ExamplesPreconfiguredDirectory extends VABInMemoryRegistry {

	
	/**
	 * Constructor - load all directory entries
	 */
	public ExamplesPreconfiguredDirectory() {
		// Populate with entries from base implementation
		super();

		// Define mappings
		// - AAS server mapping
		addMapping("AASServer", "http://localhost:8080/basys.examples/Components/AasServer/");
	}	
}
