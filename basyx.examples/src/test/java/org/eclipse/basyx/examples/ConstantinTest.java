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
