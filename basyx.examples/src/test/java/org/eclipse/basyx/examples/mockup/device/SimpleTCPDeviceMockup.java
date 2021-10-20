/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.mockup.device;

import org.eclipse.basyx.components.device.BaseTCPDeviceAdapter;




/**
 * This class implements a mockup of a simple manufacturing device. 
 * 
 * All device interface functions that are called from native code in real deployments are to be 
 * called from the test script.
 * 
 * @author kuhn
 *
 */
public class SimpleTCPDeviceMockup extends BaseTCPDeviceAdapter {

	
	/**
	 * Constructor
	 */
	public SimpleTCPDeviceMockup(int port) {
		// Invoke base implementation
		super(port);
	}
}
