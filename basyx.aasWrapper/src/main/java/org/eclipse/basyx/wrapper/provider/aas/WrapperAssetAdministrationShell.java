/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.wrapper.provider.aas;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;

/**
 * Dummy AAS for the wrapper AAS interface
 * 
 * @author espen
 *
 */
public class WrapperAssetAdministrationShell extends AssetAdministrationShell {
	public WrapperAssetAdministrationShell() {
		setIdShort("WrapperAAS");
		setIdentification(IdentifierType.CUSTOM, "WrapperAAS");
	}
}
