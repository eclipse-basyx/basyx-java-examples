/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
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
		return operation.invoke(operationParameters);
	}
	
}
