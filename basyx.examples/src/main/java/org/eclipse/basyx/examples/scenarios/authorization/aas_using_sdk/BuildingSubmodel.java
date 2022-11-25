package org.eclipse.basyx.examples.scenarios.authorization.aas_using_sdk;

import java.util.function.Function;
import org.eclipse.basyx.aas.metamodel.map.descriptor.CustomId;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetype.ValueType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;

public class BuildingSubmodel extends Submodel {
  public BuildingSubmodel(final Building building) {
    super("Building", new CustomId("odh.submodel.building"));

    //setIdShort("Building");
    //setIdentification(new ModelUrn("building1"));

    final Property consumptionProperty = new Property();
    consumptionProperty.setIdShort("consumption");
    consumptionProperty.set(400.123, ValueType.Float);
    addSubmodelElement(consumptionProperty);

    /*Function<Object[], Object> explodeFunction = (args) -> {
      System.out.println("hello explode function");
      return null;
    };

    final Operation explodeOperation = new Operation(explodeFunction);
    explodeOperation.setIdShort("explode");
    addSubmodelElement(explodeOperation);*/

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
    //activateOperation.setQualifiers(Arrays.asList(DelegatedInvocationManager.createDelegationQualifier("http://localhost:8000")));
    // Add an operation that activates the oven and implements a functional interface
    addSubmodelElement(activateOperation);
  }
}
