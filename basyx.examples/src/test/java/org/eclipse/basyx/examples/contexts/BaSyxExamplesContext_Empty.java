/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.contexts;

import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;

/**
 * BaSyx context that contains no infrastructure
 * 
 * @author kuhn
 *
 */
public class BaSyxExamplesContext_Empty extends BaSyxContext {

	
	/**
	 * Version of serialized instance
	 */
	private static final long serialVersionUID = 1L;

	public static final String CONTEXT = "/basys.examples";
	
	/**
	 * Constructor
	 */
	public BaSyxExamplesContext_Empty() {
		// Invoke base constructor to set up Tomcat server in basys.components context
		super(CONTEXT, "");
	}
}

