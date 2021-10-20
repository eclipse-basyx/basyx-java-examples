/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.mockup.servers;



import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Mockup of a supplier status servlet. The servlet returns the availability status of supplier parts.
 * 
 * @author kuhn
 *
 */
public class SupplierStatusServlet extends HttpServlet {

	
	/**
	 * Version number of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor
	 */
	public SupplierStatusServlet() {
		// Invoke base constructor
		super();
	}

	
	/**
	 * Response to 'get' requests
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Set the response message's MIME type
		response.setContentType("text/html;charset=UTF-8");

		// Return a predefined supplier data
		response.getWriter().append(""+12);
		response.getWriter().close();
	}
}
