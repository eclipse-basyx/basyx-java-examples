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
package org.eclipse.basyx.examples.snippets.submodel;

import org.eclipse.basyx.examples.snippets.manager.RetrieveSubmodelFromAAS;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;

/**
 * This snippet showcases how add to a SubmodelElement to a Submodel, that
 * already exists on a server
 * 
 * @author conradi
 *
 */
public class AddSubmodelElement {

	/**
	 * Adds a SubmodelElement to a remote Submodel
	 * 
	 * @param smElement
	 *            the SubmodelElement to be added
	 * @param smIdentifier
	 *            the Identifier of the Submodel the element should be added to
	 * @param aasIdentifier
	 *            the Identifier of the AAS the Submodel belongs to
	 * @param registryServerURL
	 *            the URL of the registry server
	 */
	public static void addSubmodelElement(SubmodelElement smElement, IIdentifier smIdentifier, IIdentifier aasIdentifier, String registryServerURL) {

		// Get the ConnectedSubmodel
		ISubmodel submodel = RetrieveSubmodelFromAAS.retrieveSubmodelFromAAS(smIdentifier, aasIdentifier, registryServerURL);

		// Add the element to it
		submodel.addSubmodelElement(smElement);

	}

}
