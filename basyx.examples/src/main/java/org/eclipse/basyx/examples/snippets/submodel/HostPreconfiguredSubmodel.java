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

import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.servlet.submodel.SubmodelServlet;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;

/**
 * This snippet showcases how host a submodel on a standalone server, i.e. a
 * server that is preconfigured with a submodel and does not allow upload of
 * additional AAS/Submodels<br>
 * For instance, this can be used by a device manufacturer to provide a submodel
 * containing live sensor data in combination with a dynamic property (cf.
 * {@link org.eclipse.basyx.examples.snippets.property.CreateLambdaProperty
 * CreateDynamicProperty}). <br>
 * 
 * @author schnicke
 *
 */
public class HostPreconfiguredSubmodel {

	/**
	 * Creates, configures and starts an HTTP Server providing a preconfigured
	 * submodel
	 * 
	 * @param serverContext
	 *            the context configuration of the server
	 * @param sm
	 *            the submodel to be hosted
	 * @return the started server
	 */
	public static BaSyxHTTPServer hostPreconfiguredSubmodel(BaSyxContextConfiguration serverContext, Submodel sm) {
		// Create the BaSyx context from the context configuration
		BaSyxContext context = serverContext.createBaSyxContext();

		// Create a new SubmodelServlet containing the passed submodel
		SubmodelServlet smServlet = new SubmodelServlet(sm);

		// Add the SubmodelServlet mapping to the context at the path "$SmIdShort"
		// Here, it possible to add further submodels using the same pattern
		context.addServletMapping("/" + sm.getIdShort() + "/*", smServlet);

		// Create and start a HTTP server with the context created above
		BaSyxHTTPServer preconfiguredSmServer = new BaSyxHTTPServer(context);
		preconfiguredSmServer.start();

		return preconfiguredSmServer;
	}

}
