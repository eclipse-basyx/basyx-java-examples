/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.snippets.undoc.aas.embedhttp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import org.eclipse.basyx.vab.backend.server.utils.JSONProvider;
import org.eclipse.basyx.vab.core.IModelProvider;
import org.eclipse.basyx.vab.core.tools.VABPathTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;





/**
 * Implements an embedded HTTP server
 * 
 * @author kuhn
 *
 */
public class EmbeddedHTTPServer implements HttpHandler {
	
	/**
	 * Initiates a logger using the current class
	 */
	private static final Logger logger = LoggerFactory.getLogger(EmbeddedHTTPServer.class);

	
	/**
	 * Reference to IModelProvider backend
	 */
	protected JSONProvider<IModelProvider> providerBackend = null;
	
	
	/**
	 * HTTP server instance
	 */
	protected HttpServer server = null;
	

	
	
	/**
	 * Constructor
	 */
	public EmbeddedHTTPServer(String context, IModelProvider provider) {
		// Create HTTP server instance
		try {
			// Create server and set context
			server = HttpServer.create(new InetSocketAddress(8000), 0);
			server.createContext(context, this);
			// - Create a default executor
			server.setExecutor(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Store provider reference
		providerBackend = new JSONProvider<IModelProvider>(provider);	
	}

	
	/**
	 * Handle a HTTP request
	 */
	@Override
	public void handle(HttpExchange t) throws IOException {
		// Switch based on request method
		switch (t.getRequestMethod()) {
		
			// Process HTTP GET request (implement "Get" operation)
			case "GET": {
				// Get path to element
				String path = extractPath(t);

				// Setup HTML response header
				Headers responseHeaders = t.getResponseHeaders();
				responseHeaders.set("Content-Type", "application/json; charset=utf-8");
				t.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

				// Process get request
				providerBackend.processBaSysGet(path, new PrintWriter(t.getResponseBody()));
				
				// End processing
				break;
			}
			
			
			// Process HTTP POST request (Creates a new Property, Operation, Event, Submodel or AAS or invokes an operation)
			case "POST": {
				// Get path to element and de-serialize value
				String path     = extractPath(t);
				String serValue = extractSerializedValue(t);
				
				// Setup HTML response header
				Headers responseHeaders = t.getResponseHeaders();
				responseHeaders.set("Content-Type", "application/json; charset=utf-8");
				t.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

				// Check if request is for property creation or operation invoke
				if (VABPathTools.isOperationPath(path)) {
					// Invoke BaSys VAB 'invoke' primitive
					providerBackend.processBaSysInvoke(path, serValue, new PrintWriter(t.getResponseBody()));
				} else {
					// Invoke the BaSys 'create' primitive
					providerBackend.processBaSysCreate(path, serValue, new PrintWriter(t.getResponseBody()));
				}

				// End processing
				break;					
			}
			

			// Process HTTP PUT operation (implement "Set" operation)
			case "PUT": {
				// Get path to element and de-serialize value
				String path     = extractPath(t);
				String serValue = extractSerializedValue(t);

				// Process put request
				providerBackend.processBaSysSet(path, serValue.toString(), new PrintWriter(t.getResponseBody()));

				// End processing
				break;					
			}

			
			// Process HTTP PATCH operation (Updates a map or collection)
			case "PATCH": {
				// Get path to element and de-serialize value
				String path     = extractPath(t);
				String serValue = extractSerializedValue(t);

				// Process patch request
				providerBackend.processBaSysDelete(path, serValue.toString(), new PrintWriter(t.getResponseBody()));

				// End processing
				break;					
			}

			
			// Process HTTP DELETE operation (Deletes any resource under the given path)
			case "DELETE": {
				// Get path to element
				String path     = extractPath(t);

				// No parameter to read! Provide serialized null
				String nullParam = "";

				// Process delete request
				providerBackend.processBaSysDelete(path, nullParam, new PrintWriter(t.getResponseBody()));

				// End processing
				break;					
			}

			
			// Handle unknown HTTP request methods
			default:
				logger.debug("  - Unknown Method:"+t.getRequestMethod());
				break;
		}
		
		
		// Close output stream
		OutputStream os = t.getResponseBody();
		os.close();
	}
	
	
	
	/**
	 * Extract a path from a HTTP request
	 */
	private String extractPath(HttpExchange req) throws UnsupportedEncodingException {
		// Extract path
		String uri         = req.getRequestURI().getPath();
		String contextPath = req.getHttpContext().getPath();
		String path        = uri.substring(contextPath.length() + 1);

		// Add leading "/" to path if necessary
		if (!path.startsWith("/")) {
			path = "/" + path;
		}

		// Decode URL
		path = java.net.URLDecoder.decode(path, "UTF-8");

		// Return path
		return path;
	}
			
	
	/**
	 * Read serialized value 
	 */
	private String extractSerializedValue(HttpExchange req) throws IOException {
		// Read request body
		InputStream is = req.getRequestBody();		
		StringBuilder serValue = new StringBuilder();

		// This seems kind of slow...
		while (is.available() > 0) {
			serValue.append(String.valueOf((char) (byte) is.read()));
		}
		return serValue.toString();
	}
	
	
	/**
	 * Start server
	 */
	public void start() {
		// Start HTTP server
		server.start();
	}
	
	
	/**
	 * Stop server
	 */
	public void stop() {
		// Stop HTTP server
		this.stop(0);		
	}


	/**
	 * Stop server
	 */
	public void stop(int endCode) {
		// Stop HTTP server
		server.stop(endCode);		
	}
}

