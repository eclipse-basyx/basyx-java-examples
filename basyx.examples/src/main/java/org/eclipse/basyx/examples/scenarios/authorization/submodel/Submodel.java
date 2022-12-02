/*******************************************************************************
 * Copyright (C) 2022 the Eclipse BaSyx Authors
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
package org.eclipse.basyx.examples.scenarios.authorization.submodel;

import java.util.Arrays;
import java.util.function.Function;
import org.eclipse.basyx.aas.metamodel.map.descriptor.CustomId;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetype.ValueType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.restapi.operation.DelegatedInvocationManager;

public class Submodel extends org.eclipse.basyx.submodel.metamodel.map.Submodel {
	public Submodel() {
		super("TheSubmodel", new CustomId("basyx.thesubmodel"));

		final Property consumptionProperty = new Property();
		consumptionProperty.setIdShort("consumption");
		consumptionProperty.set(400.123, ValueType.Float);
		addSubmodelElement(consumptionProperty);

		// Add a function that activates the oven and implements a functional interface
		Function<Object[], Object> activateFunction = (args) -> {
			System.out.println("hello activate function");
			return null;
		};
		// Encapsulate the function in an operation
		Operation activateOperation = new Operation(activateFunction);
		// Set the id of the operation
		activateOperation.setIdShort("activateOven");
		// https://wiki.eclipse.org/BaSyx_/_Documentation_/_Components_/_AAS_Server#Operation_Delegation
		activateOperation.setQualifiers(Arrays.asList(DelegatedInvocationManager.createDelegationQualifier("http://localhost:8000")));
		// Add an operation that activates the oven and implements a functional interface
		addSubmodelElement(activateOperation);
	}
}
