/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.deployment;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.vab.protocol.http.server.BaSyxHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.eclipse.basyx.vab.service.api.BaSyxService;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




/**
 * This class represents a BaSyx deployment with servers / servlets and assets for running BaSyx examples
 * 
 * @author kuhn
 *
 */
public class BaSyxDeployment extends ExternalResource {
	
	/**
	 * Initiates a logger using the current class
	 */
	private static final Logger logger = LoggerFactory.getLogger(BaSyxDeployment.class);

	
	/**
	 * Store context objects
	 */
	protected Object[] contextComponents = null;
	
	/**
	 * Map runnable names to runnables
	 */
	protected Map<String, BaSyxService> contectRunnablesByName = new HashMap<>();
	
	/**
	 * Deployment on HTTP server
	 */
	private BaSyxHTTPServer server;
	
	
	
	/**
	 * Constructor - accept parameter of type BaSyxContext or Runnable
	 */
	public BaSyxDeployment(Object... components) {
		// Store context objects
		contextComponents = components;
		
		// Store runnable objects by name
		for (Object obj: contextComponents) {
			// Check component type
			if (!(obj instanceof BaSyxService)) continue;
			
			// Add BaSyxContextRunnable to context
			// - Cast object
			BaSyxService contextRunnable = (BaSyxService) obj;
			// - Add context runnable to map if it is named
			if (contextRunnable.getName() == null) continue;
			// - Add context runnable to map
			contectRunnablesByName.put(contextRunnable.getName(), contextRunnable);
		}
	}
	
	
	
    /**
     * Execute before a test case starts
     */
    @Override
	protected void before() {
    	// Iterate context components
    	for (Object contextComponent: contextComponents) {
    		// Process BaSyx context objects that run in a tomcat server
    		if (contextComponent instanceof BaSyxContext) {
    			// Get HTTP server resource
				server = new BaSyxHTTPServer((BaSyxContext) contextComponent);
				// - Start the server
				server.start();
    			// - Continue loop
    			continue;
    		}
    		
    		// Process runnables
    		if (contextComponent instanceof BaSyxService) {
    			// Execute runnable
    			((BaSyxService) contextComponent).start();
    			// - Continue loop
    			continue;
    		}
    		
    		logger.error("Error:"+contextComponent);
    		
    		// Unknown deployment context
    		throw new UnknownContextComponentTypeException();
    	}
    }

    
    /**
     * Execute after test case ends
     */
    @Override
	protected void after() {
    	// Iterate context components
    	for (Object contextComponent: contextComponents) {
    		// Process BaSyx context objects that run in a tomcat server
    		if (contextComponent instanceof BaSyxContext) {
				// - Shutdown the server
				server.shutdown();
    			// - Continue loop
    			continue;
    		}
    		
    		// Process runnables
    		if (contextComponent instanceof BaSyxService) {
    			// Execute runnable
    			((BaSyxService) contextComponent).stop();
    			// - Continue loop
    			continue;
    		}
    		
    		// Unknown deployment context
    		throw new UnknownContextComponentTypeException();
    	}
    }
    
    
    /**
     * Get context runnable by name
     */
    public BaSyxService getRunnable(String name) {
    	return contectRunnablesByName.get(name);
    }
}

