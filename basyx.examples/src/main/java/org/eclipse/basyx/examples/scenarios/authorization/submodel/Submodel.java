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
