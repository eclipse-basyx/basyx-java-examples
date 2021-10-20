/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples;

import org.eclipse.basyx.aas.aggregator.api.IAASAggregator;
import org.eclipse.basyx.aas.aggregator.proxy.AASAggregatorProxy;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.CustomId;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;

public class ConstantinTest {
	public static void main(String[] args) {
		IAASAggregator aggregator = new AASAggregatorProxy("http://localhost:5080");

		AssetAdministrationShell aas = new AssetAdministrationShell("aasIdShort", new CustomId("AASCustomId"), new Asset("assetIdShort", new CustomId("AssetCustomId"), AssetKind.INSTANCE));

		try {
			aggregator.deleteAAS(aas.getIdentification());
		} catch (ResourceNotFoundException e) {
			System.out.println("AAS not on server");
		}

		aggregator.createAAS(aas);

		IAssetAdministrationShell shell = aggregator.getAAS(aas.getIdentification());
		
		Submodel sm = new Submodel("smIdShort", new CustomId("SMCustomId"));
		Property p1 = new Property("prop1", "HelloWorld!");
		sm.addSubmodelElement(p1);
		shell.addSubmodel(sm);
		// ISubmodel connectedSm = shell.getSubmodel(sm.getIdentification());
		// connectedSm.addSubmodelElement(p1);
	}
}
