# General Information

basyx.rosbridge contains two packages with software libraries that allow the communication between BaSyx and ROS,
as well as one packages that highlights the usage of both.

With the help of the libraries it is possible to:

- load data published on ROS topics on a ROS device into SubmodelElementsCollections
- publish messages to ROS from SubmodelElementCollections
- invoke services offered in ROS through a SubmodelElementCollection
- offer services inside a SubmodelElementCollection to ROS (and of course to BaSyx too)


# Prerequisites

ROS-Device(s): Have rosbridge_suite (see http://wiki.ros.org/rosbridge_suite) installed. To connect to the ROS device, it is necessary to 
run rosbridge_websocket from this package

BaSyx-Device: Eclipse with Maiven.

For testing purposes, it is highly recommended to use the AAS Web GUI provided by BaSyx.

# Setup

All 3 packages can be directly imported into Eclipse:

File --> Import… --> Choose “Existing Maven Project” --> Select project root folder

Afterwards, run "Maven install" on "java_rosbridge_client" first, then on "basyx_ros_bridge". Last, run Maven --> Update Project on BasyxRosBridgeDemo

For your own Maven project, add the local snapshots that are created as dependencies to your pom.xml. An example on how to do this can be found in the pom.xml of "BasyxRosBridgeDemo". 



# Package Explanations

**java_rosbridge_client:** This package is completly independent of BaSyx and focuses on the implementing
a client for rosbridge protocol in Java. The core functionalities for recieving and sending messages/interacting with services
are bundled in "java_rosbridge_client.core". The package "java_rosbridge_client.communication" includes Java versions
of the typical ROS message and service packages (like std_msgs, std_srvs, ...). It is not necessary when using custom
messages/services only.

**basyx_ros_bridge:** This package builds upon java_rosbridge_client and makes ROS-functionalities available to BaSyx.
Specifically, it adds functionalities to create SubmodelElementCollections that represent Message Subscribers/Senders
as well as Service Providers/Callers.



# Usage 

The package  delivers an extensive example on how to use the different parts of the packages to bring ROS and BaSyx together.
Together with documentation, it is found in the "Main.java" file of "BasyxRosBridgeDemo". To execute the code, it is necessary to enter the empty adresses
and ports at the top of the Main-class, and to run a ROS device to connect to as well. The ROS device should
publish messages of the type "JointState" on the topic called "/joint_states" and offer a Service of type "SetBool" on "/SetBoolTestSrv". If you
want to use other message/service types or topics just replace the corresponding arguments.

Examples for necessary changes when using other messages/services and topics in the Demo file:

Message Subscriber:
```
MessageSubscriptionSMC<JointState> smcTest = new MessageSubscriptionSMC<JointState>(JointState.class, "/joint_states", "JointSub", handler, "VT1445211")
```
to
```
MessageSubscriptionSMC<Header> smcTest = new MessageSubscriptionSMC<Header>(Header.class, "/headers", "JointSub", handler, "VT1445211")
```

Service Caller:
```
ServiceCallerSMC<SetBool, SetBoolArgs, SetBoolResp> smcSrvCaller =  new ServiceCallerSMC<SetBool, SetBoolArgs, SetBoolResp>(SetBool.class, "/SetBoolTestSrv", "ServiceCaller", handler, "PSNAZZZWA123")
```
to
```
ServiceCallerSMC<Trigger, TriggerArgs, TriggerResp> smcSrvCaller =  new ServiceCallerSMC<Trigger, TriggerArgs, TriggerResp>(Trigger.class, "/trigger_this", "ServiceCaller", handler, "PSNAZZZWA123")
```

If you run the code, you can use the AAS Web GUI and a tool that implements HTTP REST like postman to evaluate if everything is working fine.
First, launch the GUI and connect it to the registry. Connecting is done by entering "http://[your ip]:[your port]/aasServer/registry/" as the Registry Server URL
in the dropdown main menu of the GUI, and then by pressing connect. In the left column, a field with the name "ExemplaryAAS" should appear now. Click on it,
and in the second column, 2 Submodels should appear, "MessageCommunication" and "ServiceCommunication". Message Communication can be checked directly from the GUI.
Click on it, and the two SubmodelElementCollections created by the demo code should appear. "JointSub" should contain joint data published from ROS. "HeaderPublisher"
has several elements that can be filled out, and by invoking the operation "publishSMC", the entered data (from the fields seq, ...) will be sent to ROS.
ServiceCommunication can not be directly tested from the GUI, as it currently only allows to send no value or a single numerical value as argument. However, it can be
invoked using the HTTP-REST API. "ServicePublisher" publishes its service to ROS, hence it can be accessed from ROS - call it from your ROS device, using the
topic name defined in the demo code (standard is "/SetBoolSecondTestSrv"). "ServiceCaller" makes a service from ROS available. To test it, use a HTTP-REST POST command
at the following adress
```
http://[your ip]:[your port]/aasServer/shells/ExemplaryAAS/aas/submodels/ServiceCommunication/submodel/submodelElements/ServiceCaller/callService/invoke
```
with the following body
```
{
    "requestId": "1",
    "timeout": 100,
    "inputArguments": [
    {
        "value": {
            "modelType": {
                "name": "SubmodelElementCollection"
            },
            "value": [ 
                {
                    "modelType": {
                        "name": "Property"
                    },
                    "idShort": "data",
                    "valueType": "boolean",
                    "value": true
                }
            ],

            "idShort": "in_args"
        }
    }
        
        
]
}
```


# FAQ:

**How do I define a custom message/service?**

For ROS, the answer can be found in the official ROS documentation (see http://wiki.ros.org/ROS/Tutorials/CreatingMsgAndSrv). For using basyx_ros_bridge, examples 
can be found in "java_rosbridge_client.communication", where Java variants of all common ROS messages and services are available. Important notes on creating
new message/service files in Java can be found below. 
Important Point 1: The files should be located inside a package that is equivalently named to ROS.
If ROS messages are e.g. found under std_msgs and in Java under all_msgs/std_msgs, the pipeline will not work and the
error message "[...] is not a valid type string" can be observed in the terminal under ROS executing rosbridge_websocket.
Important Point 2: Unsigned integers in ROS messages/services are always treated as signed ones of the next higher size in Java when using
the pipeline --> uint8 becomes Short, and so on. Furthermore, not the pimitive datatypes, but the corresponding classes (e.g. Float instead of float) must be used.
Important Point 3: A Java implementation of a ROS message must always inherit from "RosMessage" and be named like the message in ROS.
Important Point 4: A Java implementation of a ROS service always consist of 3 parts: One named like the service itself and inheriting from "RosService", and two more
representing the arguments/response of the service, inheriting from "RosServiceArgs"/"RosServiceResp". The class inheriting from "RosService" also needs to have a custom constructor,
where the classes implementing the arguments/response need to be set. Orient yourself on the examples, e.g. in "std_srv" of "java_rosbridge_client.communication".
Important Point 5: Name all elements of a message/service exactly like in ROS. Do not follow Java naming conventions here.


**Class XYZ (from some of the packages) is not found, import does not work. What to do?**

Typically, if imports are correct, running Maven --> Update Project basyx_ros_bridge, java_rosbridge_client, java_rosbridge_client.communication,
java_rosbridge_client.core and your Maven project should resolve the issue.

# Author

Patrick Schlosser, IAR-IPR, Karlsruhe Institute of Technology

This work was funded by the German Federal Ministry for Economic
Affairs and Climate Action in the research project ’FabOS’