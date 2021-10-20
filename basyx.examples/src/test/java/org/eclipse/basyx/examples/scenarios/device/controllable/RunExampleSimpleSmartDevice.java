/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.scenarios.device.controllable;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.examples.contexts.BaSyxExamplesContext;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.examples.mockup.application.ReceiveDeviceDashboardStatusApplication;
import org.eclipse.basyx.examples.mockup.device.SmartBaSyxTCPDeviceMockup;
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
 * Run example for smart device AAS
 * 
 * @author kuhn
 *
 */
public class RunExampleSimpleSmartDevice extends BaSyxExampleScenario {

	
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
	 * Instantiate and start context elements for this example. BaSyxDeployment contexts instantiate all
	 * components on the IP address of the host. Therefore, all components use the same IP address. 
	 */
	@ClassRule
	public static BaSyxDeployment context = new BaSyxDeployment(
				// BaSyx infrastructure
				// - BaSys topology with one AAS Server and one SQL directory
				new BaSyxExamplesContext(),
				
				// Device mockups
				new SmartBaSyxTCPDeviceMockup(9997).setName("Device"),
				
				// Application mockups
				new ReceiveDeviceDashboardStatusApplication().setName("Application")
			);



	/**
	 * Test sequence: 
	 * - Device status update
	 * - Read device status from AAS
	 */
	@Test 
	public void test() throws Exception {
		// Create connection to device control component on smart device
		// - Create BaSyx connector to connect with the device manager
		basyxConnector = new BaSyxConnector("localhost", 9997);
		// - Create connection to device control component
		toControlComponent = new JSONConnector(basyxConnector);


		
		// Device finishes initialization and moves to idle state
		((SmartBaSyxTCPDeviceMockup) context.getRunnable("Device")).deviceInitialized();
		
		// Application waits for status change
		waitfor( () -> ((ReceiveDeviceDashboardStatusApplication) context.getRunnable("Application")).getDeviceStatus().equals("IDLE") );
		
		
		// Change device operation mode
		toControlComponent.setValue(VABPathTools.concatenatePaths(ControlComponent.STATUS, ControlComponent.OP_MODE), "RegularMilling");
		// - Validate device control component operation mode
		waitfor( () -> ((SmartBaSyxTCPDeviceMockup) context.getRunnable("Device")).getControlComponent().getOperationMode().equals("RegularMilling") );

		// Application checks invocation counter
		assertTrue( ((ReceiveDeviceDashboardStatusApplication) context.getRunnable("Application")).getDeviceInvocationCounter() == 0 );		


		// Start device service
		toControlComponent.setValue("cmd", "start");
		// - Validate control component status
		waitfor( () -> ((SmartBaSyxTCPDeviceMockup) context.getRunnable("Device")).getControlComponent().getExecutionState().equals(ExecutionState.EXECUTE.getValue()) );
		// - Indicate service end
		((SmartBaSyxTCPDeviceMockup) context.getRunnable("Device")).serviceCompleted();
		// - Validate control component status
		waitfor( () -> ((SmartBaSyxTCPDeviceMockup) context.getRunnable("Device")).getControlComponent().getExecutionState().equals(ExecutionState.COMPLETE.getValue()) );

		
		// Reset device to enable subsequent service calls
		toControlComponent.setValue("cmd", "reset");
		// - Device finishes reset and moves to idle state
		((SmartBaSyxTCPDeviceMockup) context.getRunnable("Device")).resetCompleted();
		
		
		// Device indicates idle state
		waitfor( () -> ((SmartBaSyxTCPDeviceMockup) context.getRunnable("Device")).getControlComponent().getExecutionState().equals(ExecutionState.IDLE.getValue()) );
		// - Let application check device state, expect IDLE status
		assertTrue( ((ReceiveDeviceDashboardStatusApplication) context.getRunnable("Application")).getDeviceStatus().equals("IDLE") );


		// Application checks invocation counter
		assertTrue( ((ReceiveDeviceDashboardStatusApplication) context.getRunnable("Application")).getDeviceInvocationCounter() == 1 );
	}
}
