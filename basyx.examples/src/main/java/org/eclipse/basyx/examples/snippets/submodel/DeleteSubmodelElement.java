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

/**
 * Snippet that showcases how to delete a SubmodelElement from a Submodel
 * 
 * @author conradi
 *
 */
public class DeleteSubmodelElement {

	/**
	 * Removes a SubmodelElement from a Submodel
	 * 
	 * @param elementId
	 *            the Id of the Element to be deleted (can also be a path if the
	 *            Element is in a Collection)
	 * @param smIdentifier
	 *            the Identifier of the Submodel the Element belongs to
	 * @param aasIdentifier
	 *            the Identifier of the AAS the Submodel belongs to
	 * @param registryServerURL
	 *            the URL of the registry server (e.g.
	 *            http://localhost:8080/registry)
	 */
	public static void deleteSubmodelElement(String elementId, IIdentifier smIdentifier, IIdentifier aasIdentifier, String registryServerURL) {

		// Retrieve the Submodel from the server as a ConnectedSubmodel
		ISubmodel submodel = RetrieveSubmodelFromAAS.retrieveSubmodelFromAAS(smIdentifier, aasIdentifier, registryServerURL);

		// Delete the Element from the Submodel
		submodel.deleteSubmodelElement(elementId);
	}
}
