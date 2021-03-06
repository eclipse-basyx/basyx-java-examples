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
package org.eclipse.basyx.examples.scenarios.device.controllable;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.examples.contexts.BaSyxExamplesContext;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.examples.mockup.application.ReceiveDeviceDashboardStatusApplication;
import org.eclipse.basyx.examples.mockup.device.ControllableTCPDeviceMockup;
import org.eclipse.basyx.examples.mockup.devicemanager.BaSyxTCPControlManufacturingDeviceManager;
import org.eclipse.basyx.examples.scenarios.device.BaSyxExampleScenario;
import org.eclipse.basyx.examples.support.directory.ExamplesPreconfiguredDirectory;
import org.eclipse.basyx.models.controlcomponent.ControlComponent;
import org.eclipse.basyx.models.controlcomponent.ExecutionState;
import org.eclipse.basyx.vab.coder.json.connector.JSONConnector;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.protocol.basyx.connector.BaSyxConnector;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorFactory;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * Run example for controllable device
 * 
 * @author kuhn
 *
 */
public class RunExampleSimpleControllableTCPDevice extends BaSyxExampleScenario {

	/**
	 * VAB connection manager backend
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(new ExamplesPreconfiguredDirectory(), new HTTPConnectorFactory());

	/**
	 * BaSyx connector instance
	 */
	protected BaSyxConnector basyxConnector = null;

	/**
	 * Communication stream to connected device control component
	 */
	protected JSONConnector toControlComponent = null;

	/**
	 * Instantiate and start context elements for this example. BaSyxDeployment
	 * contexts instantiate all components on the IP address of the host. Therefore,
	 * all components use the same IP address.
	 */
	@ClassRule
	public static BaSyxDeployment context = new BaSyxDeployment(
			// BaSyx infrastructure
			// - BaSys topology with one AAS Server and one SQL directory
			new BaSyxExamplesContext(),

			// Simulated runnables
			// - Manufacturing device manager, e.g. deployed to additonal device
			new BaSyxTCPControlManufacturingDeviceManager(9998, 9997).setName("DeviceManager"),

			// Device mockups
			new ControllableTCPDeviceMockup(9998).setName("Device"),

			// Application mockups
			new ReceiveDeviceDashboardStatusApplication().setName("Application"));

	/**
	 * Test sequence: - Device status update - Read device status from AAS
	 */
	@Test
	public void test() throws Exception {
		// Create connection to device control component on smart device
		// - Create BaSyx connector to connect with the device manager
		basyxConnector = new BaSyxConnector("localhost", 9997);
		// - Create connection to device control component
		toControlComponent = new JSONConnector(basyxConnector);

		// Device finishes initialization and moves to idle state
		((ControllableTCPDeviceMockup) context.getRunnable("Device")).deviceInitialized();

		// Application waits for status change
		waitfor(() -> ((ReceiveDeviceDashboardStatusApplication) context.getRunnable("Application")).getDeviceStatus().equals("IDLE"));
		// - Validate device status
		waitfor(() -> ((ControllableTCPDeviceMockup) context.getRunnable("Device")).getExState() == ExecutionState.IDLE);

		// Change device operation mode
		toControlComponent.setValue(VABPathTools.concatenatePaths(ControlComponent.STATUS, ControlComponent.OP_MODE), "RegularMilling");
		// - Validate device operation mode
		waitfor(() -> ((ControllableTCPDeviceMockup) context.getRunnable("Device")).getOpMode().equals("RegularMilling"));

		// Application checks invocation counter
		assertTrue(((ReceiveDeviceDashboardStatusApplication) context.getRunnable("Application")).getDeviceInvocationCounter() == 0);

		// Start device service
		toControlComponent.setValue("cmd", "start");
		// - Application waits for status change
		waitfor(() -> ((ReceiveDeviceDashboardStatusApplication) context.getRunnable("Application")).getDeviceStatus().equals("EXECUTE"));
		// - Validate device status
		waitfor(() -> ((ControllableTCPDeviceMockup) context.getRunnable("Device")).getExState() == ExecutionState.EXECUTE);

		// Device indicates service end
		((ControllableTCPDeviceMockup) context.getRunnable("Device")).serviceCompleted();
		// - Application waits for status change
		waitfor(() -> ((ReceiveDeviceDashboardStatusApplication) context.getRunnable("Application")).getDeviceStatus().equals("COMPLETE"));
		// - Validate device status
		waitfor(() -> ((ControllableTCPDeviceMockup) context.getRunnable("Device")).getExState() == ExecutionState.COMPLETE);

		// Reset device to enable subsequent service calls
		toControlComponent.setValue("cmd", "reset");
		// - Application waits for status change
		waitfor(() -> ((ReceiveDeviceDashboardStatusApplication) context.getRunnable("Application")).getDeviceStatus().equals("RESETTING"));
		// - Validate device status
		waitfor(() -> ((ControllableTCPDeviceMockup) context.getRunnable("Device")).getExState() == ExecutionState.RESETTING);

		// Device finishes reset and moves to idle state
		((ControllableTCPDeviceMockup) context.getRunnable("Device")).resetCompleted();
		// - Application waits for status change
		waitfor(() -> ((ReceiveDeviceDashboardStatusApplication) context.getRunnable("Application")).getDeviceStatus().equals("IDLE"));
		// - Validate device status
		waitfor(() -> ((ControllableTCPDeviceMockup) context.getRunnable("Device")).getExState() == ExecutionState.IDLE);

		// Application checks invocation counter
		assertTrue(((ReceiveDeviceDashboardStatusApplication) context.getRunnable("Application")).getDeviceInvocationCounter() == 1);
	}
}
