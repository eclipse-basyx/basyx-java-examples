/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;

public class ShowcaseClient {
	public static void main(String[] args) {
		// Create Manager
		ConnectedAssetAdministrationShellManager manager = 
				new ConnectedAssetAdministrationShellManager(new AASRegistryProxy(ShowcaseServer.REGISTRYPATH));

		// Retrieve submodel
		ISubmodel submodel = manager.retrieveSubmodel(ShowcaseServer.OVENAASID, ShowcaseServer.DOCUSMID);

		// Retrieve MaxTemp Property
		ISubmodelElement maxTemp = submodel.getSubmodelElement(ShowcaseServer.MAXTEMPID);
		ISubmodelElement maintenance = submodel.getSubmodelElement(ShowcaseServer.MAINTENANCEID);

		// Print Values
		System.out.println(maxTemp.getIdShort() + " is " + maxTemp.getValue());
		System.out.println(maintenance.getIdShort() + " is " + maintenance.getValue());
	}
}
