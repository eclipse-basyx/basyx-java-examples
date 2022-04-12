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
package org.eclipse.basyx.examples.snippets.undoc.aas.embedhttp;

import java.util.HashMap;

import org.eclipse.basyx.vab.core.IModelProvider;





/**
 * Implements an embedded HTTP server that hosts BaSys sub models
 * 
 * @author kuhn
 *
 */
public class EmbeddedHTTPSubmodelServer extends EmbeddedHTTPServer {

	
	/**
	 * Constructor
	 */
	public EmbeddedHTTPSubmodelServer(String context, IModelProvider provider) {
		// Invoke base constructor
		super(context, provider);

		// Prepare model provider for hosting a sub model
		try {
			provider.createValue("", new HashMap<String, Object>());
			provider.createValue("aas", new HashMap<String, Object>());
			provider.createValue("aas/submodels", new HashMap<String, Object>());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

