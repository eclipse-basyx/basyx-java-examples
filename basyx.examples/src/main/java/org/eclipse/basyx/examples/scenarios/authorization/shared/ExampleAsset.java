package org.eclipse.basyx.examples.scenarios.authorization.shared;

import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.descriptor.CustomId;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;

public class ExampleAsset extends Asset {
  public ExampleAsset() {
    super("buildingAsset", new CustomId("odh.asset.building1"), AssetKind.INSTANCE);
  }
}
