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

import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;

/**
 * Snippet that showcases how to execute an Operation contained in a Submodel
 * 
 * @author conradi
 *
 */
public class ExecuteOperation {

	/**
	 * Executes an Operation with the given parameters and return the result.
	 * The execution itself is run on the remote server
	 * 
	 * @param operationId the idShort of the Operation to be executed
	 * @param operationParameters the parameters for the execution
	 * @param smIdentifier the Identifier of the Submodel the Operation belongs to
	 * @param aasIdentifier the Identifier of the AAS the Submodel belongs to
	 * @param registryServerURL the URL of the registry server
	 * @return the result of the execution
	 * @throws Exception thrown if the execution of the Operation threw an Exception
	 */
	public static Object executeOperation(String operationId, Object[] operationParameters, IIdentifier smIdentifier, IIdentifier aasIdentifier, String registryServerURL) throws Exception {

		// Get the Operation from the Submodel
		ISubmodelElement element = RetrieveSubmodelElement.retrieveSubmodelElement(operationId, smIdentifier, aasIdentifier, registryServerURL);
		
		// Check if the element is really an Operation
		if( ! (element instanceof IOperation)) {
			// The element with the given Id is not an Operation
			throw new IllegalArgumentException("The SubmodelElement '" + operationId + "' is not an Operation");
		}
		
		// Cast the retrieved ISubmodelElement to an IOperation
		IOperation operation = (IOperation) element;
		
		// Invoke the Operation and return the Result
		return operation.invokeSimple(operationParameters);
	}
	
}
