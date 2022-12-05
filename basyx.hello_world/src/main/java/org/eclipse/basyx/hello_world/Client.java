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
package org.eclipse.basyx.hello_world;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;

/**
 * This class connects to the server created in HelloWorldServer
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
				new ConnectedAssetAdministrationShellManager(new AASRegistryProxy(HelloWorldServer.REGISTRYPATH));

		// Retrieve submodel
		ISubmodel submodel = manager.retrieveSubmodel(HelloWorldServer.OVENAASID, HelloWorldServer.DOCUSMID);

		// Retrieve MaxTemp Property
		IProperty maxTemp = (IProperty) submodel.getSubmodelElement(HelloWorldServer.MAXTEMPID);

		// Print value
		System.out.println(maxTemp.getIdShort() + " is " + maxTemp.getValue());

		// Set property value
		maxTemp.setValue(123);

		// Print value
		System.out.println(maxTemp.getIdShort() + " is " + maxTemp.getValue());
	}
}
