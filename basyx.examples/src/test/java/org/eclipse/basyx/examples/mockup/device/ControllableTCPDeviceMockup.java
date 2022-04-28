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
package org.eclipse.basyx.examples.mockup.device;

import org.eclipse.basyx.components.device.BaseTCPControllableDeviceAdapter;

/**
 * This class implements a mockup of a controllable manufacturing device.
 * 
 * The device is controlled by an external control component that issues
 * commands to this device via TCP strings
 * 
 * @author kuhn
 *
 */
public class ControllableTCPDeviceMockup extends BaseTCPControllableDeviceAdapter {

	/**
	 * Constructor
	 */
	public ControllableTCPDeviceMockup(int port) {
		// Invoke base implementation
		super(port);
	}

	/**
	 * Indicate device status change to device manager
	 */
	@Override
	protected void statusChange(String newStatus) {
		// Invoke base implementation
		super.statusChange(newStatus);

		// Write bytes to device manager
		communicationClient.sendMessage("status:" + newStatus + "\n");
	}
}
