/*******************************************************************************
 * Copyright (C) 2023 the Eclipse BaSyx Authors
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

package main;

import java.io.IOException;

import javax.servlet.http.HttpServlet;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.registration.api.IAASRegistry;
import org.eclipse.basyx.aas.registration.memory.InMemoryRegistry;
import org.eclipse.basyx.aas.registration.restapi.AASRegistryModelProvider;
import org.eclipse.basyx.aas.restapi.AASModelProvider;
import org.eclipse.basyx.aas.restapi.MultiSubmodelProvider;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.restapi.SubmodelProvider;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;

import basyx_ros_bridge.components.MessagePublisherSMC;
import basyx_ros_bridge.components.MessageSubscriptionSMC;
import basyx_ros_bridge.components.ServiceCallerSMC;
import basyx_ros_bridge.components.ServicePublisherSMC;
import java_rosbridge_client.core.rosbridge.RosbridgeClient;
import java_rosbridge_client.core.rosbridge.RosbridgeMessageHandler;
import sensor_msgs.JointState;
import std_msgs.Header;
import std_srvs.SetBool;
import std_srvs.SetBoolArgs;
import std_srvs.SetBoolResp;






public class Main {
	public static void main(String[] args) throws SecurityException, IOException {
	
		//Add your own ip and port over which you want to make the AAS available here
		String ownIpAdress = "";
		int ownPort = 9301;
		
		//enter IP of the system running rosbridge_server as well as its port here
		String ROSBRIDGE_IP = "";
		String ROSBRIDGE_PORT = "";
		
		
		//Initializes an instance of RosbridgeClient. Needs to be connected to the system
		//running rosbridge_server (websocket variant) by using the start-Method
		RosbridgeClient client = new RosbridgeClient();
		
		
		//Using the start-Method to connect the RosbridgeClient to the rosbridge_server
		//Can fail, e.g. when IP/Port is erroneous or the server can not be accessed from the current machine
		try {
			client.start("ws://" + ROSBRIDGE_IP + ":" + ROSBRIDGE_PORT);
		} catch (Exception e) {
			System.out.println("Error while connecting ro rosbridge server");
		}
		
		
		//Getting the RosbridgeMessageHandler from the current RosbridgeClient
		//The handler will be used for initializing SubmodelElementCollections that
		//encapsulate ROS communication. Use the same instance for all SubmodelElementCollections
		//that will be connected to the same rosbridge_server. 
		//Use a different RosbridgeClient/RosbridgeMessageHandler for each rosbridge_server that
		//you need to connect to.
		RosbridgeMessageHandler handler = client.getMsgHandler();
		
		
		//This command creates a SubmodelElementCollection that displays the latest message of type JointState published
		//on the topic /joint_states. Within the AssetAdministrationShell, the SubmodelElementCollection will be named JointSub (id short)
		//Regarding Signature:
		//1. Argument: The kind of message class that is published on the topic. In Java, the message types are named similar to ROS. Make sure
		//to use use the class of the Message here by adding .class after class name
		//2. The name of the topic on which the messages are published in ROS
		//3. The name (id Short) that the SubmodelElementCollection shall use when added to an AssetAdministrationShell
		//4. The message handler that shall be used --> this also determines the RosbridgeClient that will be used and therefore the rosbridge_server from
		//which data will be retrieved. Only use RosbridgeMessageHandlers retrieved from RosbridgeClients that have been started 
		//5. A unique ID that is necessary for RodbridgeMessageHandler functionalities
		MessageSubscriptionSMC<JointState> smcTest = new MessageSubscriptionSMC<JointState>(JointState.class, "/joint_states", "JointSub", handler, "VT1445211");
		
		
		//This command creates a SubmodelElementCollection that can be used to publish messages to ROS using BaSyx. For each element of the message, the SMC contains
		//a property/other SMC. Their values can be filled in by arbitrary sources. Furthermore, the created SMC contains a publish-Operation. Upon invoking,
		//all values written to the SMC will be used to create a ROS message (using the Message class the SMC was created for), which will be publish under ROS
		//using the given topic
		//Regarding Signature:
		//1. Argument: The kind of message class that will be used for creating ROS messages. In Java, the message types are named similar to ROS. Make sure
		//to use use the class of the Message here by adding .class after class name
		//2. The name of the topic where the message will be published upon invoking publish-Operation (in the SMC)
		//3. The name (id Short) that the SubmodelElementCollection shall use when added to an AssetAdministrationShell
		//4. The message handler that shall be used --> Also determines to which rosbridge_server the message will be sent (based on associated
		//RosbridgeClient)
		//5. A unique ID that is necessary for RodbridgeMessageHandler functionalities
		MessagePublisherSMC<Header> smcPublisher = new MessagePublisherSMC<Header>(Header.class, "/header_test", "HeaderPublisher", handler, "HeadPub165445211");
		
		
		//This command creates a SubmodelElementCollection that allows to invoke ROS-Services from BaSyx. The created SubmodelElementCollection contains only
		//a single operation called callService. This operation expects as input variables a SubmodelElementCollection that contains the variables of the ROS service request,
		//and produces output variables as SMC that represent the ROS service response. 
		//Regarding Signature:
		//1. Argument: The kind of service class that will be used for creating operation. Make sure to use the class of the Service here by adding .class after class name
		//2. The name of the ROS service that will be called when invoking the callService-Operation (in the SMC)
		//3. The name (id Short) that the SubmodelElementCollection shall use when added to an AssetAdministrationShell
		//4. The message handler that shall be used --> Also determines to which rosbridge server the service call will be sent (based on associated
		//RosbridgeClient)
		//5. A unique ID that is necessary for RodbridgeMessageHandler functionalities
		ServiceCallerSMC<SetBool, SetBoolArgs, SetBoolResp> smcSrvCaller =  new ServiceCallerSMC<SetBool, SetBoolArgs, SetBoolResp>(SetBool.class, "/SetBoolTestSrv", "ServiceCaller", handler, "PSNAZZZWA123");
	
		
		
		//To make ROS-style services that are published under BaSyx available under ROS, several steps are necessary. First, the functionality of the services (which it performs when getting invoked)
		//needs to be defined. This can be done in two ways: By implementing the abstract class RosbridgeServicePublisher from the JavaRosbridgeClient-Library, or by implementing a class that contains
		//a method for processing a specific implementation of RosServiceArgs and returning the corresponding implementation of RosServiceResp (Background: Each implementation of RosService has an implementation
		//of RosServiceArgs and RosServiceResp, corresponding to the respective Service Arguments/Service Response. For an example have a look at SetBool, SetBoolArgs and SetBoolResp classes in java_rosbridge_client.communication --> std_srvs)
		//In this example, the second option has been chosen: TestSrvProcessor is a class that implements a method that takes takes a SetBoolArgs-instance as input and creates a SetBoolResp, hence processing the service call.
		TestSrvProcessor srvProc = new TestSrvProcessor();	
		
		//The following commands create the SubmodelElementCollection that publishes a service by itself and makes it available to BaSyx as well as ROS. Here, it is created for the SetBool-Service, along its
		//associated argument SetBoolArgs and response SetBoolResp. The used arguments for construction an instance are the following:
		//1. Class of that service for which the SMC is being constructed
		//2. The "Service-Topic" under which the service will be available in ROS
		//3. Name (idShort) of the SMC in BaSyx
		//4 The message handler instance responsible for ROS communication
		//5. Unique ID
		//6. The method that will be used to process Service calls. Takes SetBoolArgs-instances as input.
		//7. The instance on which the Method from 6 will be invoked on
		ServicePublisherSMC<SetBool, SetBoolArgs, SetBoolResp> pubSrvSmc = null;
		try {
			pubSrvSmc = new ServicePublisherSMC<SetBool, SetBoolArgs, SetBoolResp>(SetBool.class, "/SetBoolSecondTestSrv", "ServicePublisher", handler, "PASQQSNAZZZWA123", srvProc.getClass().getMethod("processBool", SetBoolArgs.class),srvProc);
		} catch (NoSuchMethodException | SecurityException | IOException e1) {
			e1.printStackTrace();
		}
		
		// ##########################################################################################################################################################################
		// #### From here on, only BaSyx-Specific code will be presented. This includes adding the SMCs to AAS, and publishing making them available using an AAS Server/Registry ###
		// ##########################################################################################################################################################################
		
		
		//Creating an AssetAdministrationShell with custom identifier (unimportant for the example)
		AssetAdministrationShell aas = new AssetAdministrationShell();
		aas.setIdentification(new CustIdentifier("ExemplaryAAS"));
		aas.setIdShort("ExemplaryAAS");
		
		//Creating one submodel with dummy identifier for all message related SMCs
		Submodel sub = new Submodel();
		sub.setIdentification(IdentifierType.IRI, "https://example.com/ids/sm/" + ((int) Math.random() * 9999.0) + "_" + ((int) Math.random() * 9999.0) + "_" + ((int) Math.random() * 9999.0) + "_" + ((int) Math.random() * 9999.0));
		sub.setIdShort("MessageCommunication");
		
		//adding the message-related SMCs to the submodel
		sub.addSubmodelElement(smcTest);
		sub.addSubmodelElement(smcPublisher);
		
		//Creating one submodel with dummy identifier for all service related SMCs
		Submodel sub2 = new Submodel();
		sub2.setIdentification(IdentifierType.IRI, "https://example.com/ids/sm/" + ((int) Math.random() * 9999.0) + "_" + ((int) Math.random() * 9999.0) + "_" + ((int) Math.random() * 9999.0) + "_" + ((int) Math.random() * 9999.0));
		sub2.setIdShort("ServiceCommunication");
		
		//Adding the service-related SMCs to the submodel
		sub2.addSubmodelElement(smcSrvCaller);
		sub2.addSubmodelElement(pubSrvSmc);
		
		
		//BaSyx context needed for publishing AAS Server
		BaSyxContext context = new BaSyxContext("/aasServer", "", ownIpAdress, ownPort);
		context.setAccessControlAllowOrigin("*");
		
		
		//Adding the Submodels to the AAS
		aas.addSubmodel(sub);
		aas.addSubmodel(sub2);
		
		//Creating Provider classes for AAS and Submodels; adding the AAS and Submodels
		AASModelProvider aasProvider = new AASModelProvider(aas);
		MultiSubmodelProvider fullProvider = new MultiSubmodelProvider();
		fullProvider.setAssetAdministrationShell(aasProvider);
		fullProvider.addSubmodel(new SubmodelProvider((Submodel) sub));
		fullProvider.addSubmodel(new SubmodelProvider((Submodel) sub2));
		
		
		/*
		 * Creating the Servlets, Descriptors and Mapping needed to publish the AAS and the Submodels from the Provider
		 */
		HttpServlet aasServlet = new VABHTTPInterface<IModelProvider>(fullProvider);
		AASDescriptor aasDescriptor = new AASDescriptor(aas, "http://" + ownIpAdress + ":" + ownPort + "/aasServer/shells/" + aas.getIdentification().getId() +"/aas");		
		context.addServletMapping("/shells/" + aas.getIdentification().getId() +  "/*", (HttpServlet) aasServlet);
		
		//Creating an AAS Registry, will be launched together with the AAS using BaSyxHTTPServer
		IAASRegistry registry = new InMemoryRegistry();
		IModelProvider registryProvider = new AASRegistryModelProvider(registry);
		HttpServlet registryServlet = new VABHTTPInterface<IModelProvider>(registryProvider);
		//registers AAS at registry
		registry.register(aasDescriptor);
		context.addServletMapping("/registry/*", registryServlet);
		
		//Creating the BaSyx Server
		BaSyxHTTPServer server = new BaSyxHTTPServer(context);
		
		//Starting the BaSyx Server
		server.start();
		
		
		//Spins the thread, preventing exit
		while (true) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
				
		
		
	}

}
