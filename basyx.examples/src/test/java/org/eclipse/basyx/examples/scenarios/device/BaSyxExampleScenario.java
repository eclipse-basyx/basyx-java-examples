/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.scenarios.device;

import java.util.function.Supplier;



/**
 * Base class for all BaSyx examples
 * 
 * @author kuhn
 */
public class BaSyxExampleScenario {

	
	
	/**
	 * Wait for a condition
	 */
	protected void waitfor(Supplier<Boolean> function) {
		while (!function.get()) Thread.yield();
	}
}
