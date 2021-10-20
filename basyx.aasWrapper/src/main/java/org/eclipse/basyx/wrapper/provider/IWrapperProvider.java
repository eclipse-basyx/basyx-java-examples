/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.wrapper.provider;

import java.util.Collection;

import org.eclipse.basyx.aas.registration.api.IAASRegistry;
import org.eclipse.basyx.wrapper.receiver.IPropertyWrapperService;
import org.eclipse.basyx.wrapper.receiver.configuration.PropertyConfiguration;

/**
 * Interface for a generic connector that makes use of the HTTPModelProvider interface
 * 
 * @author espen
 *
 */
public interface IWrapperProvider extends HTTPModelProvider {

	/**
	 * Returns the path for this provider
	 * 
	 * @return
	 */
	public String getProviderPath();

	/**
	 * Sets the path for this provider
	 * 
	 * @param path
	 */
	public void setProviderPath(String path);

	/**
	 * Initialize the provider
	 * 
	 * @param proxyService
	 * @param registry
	 */
	public void initialize(IPropertyWrapperService proxyService, IAASRegistry registry,
			Collection<PropertyConfiguration> configs);
}
