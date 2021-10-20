/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.hello_world;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;

/**
 * This class connects to the server created in Server
 * 
 * It retrieves the Submodel and prints the
 * idShort and Value of the contained Property to the console
 * 
 * @author schnicke, conradi
 *
 */
public class Client {
	public static void main(String[] args) {
		// Create Manager
		ConnectedAssetAdministrationShellManager manager =
				new ConnectedAssetAdministrationShellManager(new AASRegistryProxy(Server.REGISTRYPATH));

		// Retrieve submodel
		ISubmodel submodel = manager.retrieveSubmodel(Server.OVENAASID, Server.DOCUSMID);

		// Retrieve MaxTemp Property
		ISubmodelElement maxTemp = submodel.getSubmodelElement(Server.MAXTEMPID);

		// Print value
		System.out.println(maxTemp.getIdShort() + " is " + maxTemp.getValue());
	}
}
