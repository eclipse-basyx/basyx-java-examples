/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.wrapper.provider.streamsheets;

import org.eclipse.basyx.wrapper.provider.GenericWrapperProvider;

/**
 * Streamsheets-specific proxy provider
 * 
 * @author espen
 */
public class StreamsheetsProvider extends GenericWrapperProvider {
	public static final String TYPE = "STREAMSHEETS";

	public StreamsheetsProvider() {
		this.providerPath = "/streamsheets";
	}
}
