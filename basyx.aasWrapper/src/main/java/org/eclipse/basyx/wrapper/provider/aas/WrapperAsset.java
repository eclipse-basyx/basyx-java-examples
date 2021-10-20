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

import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;

/**
 * Dummy asset for the wrapper AAS interface
 * 
 * @author espen
 *
 */
public class WrapperAsset extends Asset {
	public WrapperAsset() {
		setIdShort("WrapperAsset");
		setIdentification(IdentifierType.CUSTOM, "WrapperAsset");
		setAssetKind(AssetKind.INSTANCE);
	}
}
