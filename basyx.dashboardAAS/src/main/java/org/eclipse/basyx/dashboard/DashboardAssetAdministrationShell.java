/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.dashboard;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;

/**
 * A minimal AAS header for the dashboard AAS
 * 
 * @author espen
 *
 */
public class DashboardAssetAdministrationShell extends AssetAdministrationShell {
	public DashboardAssetAdministrationShell() {
		setIdShort("DashboardAAS");
		setIdentification(IdentifierType.CUSTOM, "DashboardAAS");
	}
}
