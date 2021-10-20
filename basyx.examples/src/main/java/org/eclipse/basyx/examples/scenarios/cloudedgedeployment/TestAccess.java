/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/


package org.eclipse.basyx.examples.scenarios.cloudedgedeployment;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.manager.api.IAssetAdministrationShellManager;
import org.eclipse.basyx.aas.registration.api.IAASRegistry;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;

public class TestAccess {
	public static void main(String[] args) {
		IAASRegistry registry = new AASRegistryProxy(CloudEdgeDeploymentScenario.registryPath);
		IAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(registry);
		ISubmodel edgeSm = manager.retrieveSubmodel(CloudEdgeDeploymentScenario.aasIdentifier, CloudEdgeDeploymentScenario.edgeSmIdentifier);
		IOperation op = (IOperation) edgeSm.getSubmodelElement("exampleOp");
		op.invoke();
	}
}
