package org.eclipse.basyx.examples.scenarios.authorization.shared;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.CustomId;

public class ExampleShell extends AssetAdministrationShell {
  public ExampleShell() {
    super("building", new CustomId("odh.aas.building1"), new ExampleAsset());
  }
}
