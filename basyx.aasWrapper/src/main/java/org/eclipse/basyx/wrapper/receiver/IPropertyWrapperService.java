/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.wrapper.receiver;

import java.util.Set;

import org.eclipse.basyx.wrapper.receiver.configuration.PropertyConfiguration;

/**
 * Interface for a property service to get and set properties
 * 
 * @author espen
 *
 */
public interface IPropertyWrapperService {
	/**
	 * Adds a property configuration to the service
	 * 
	 * @param config
	 */
	public void addPropertyConfig(PropertyConfiguration config);

	/**
	 * Returns a list of valid properties that have been configured
	 */
	public Set<String> getValidProperties();

	/**
	 * Gets the current result for a property
	 * 
	 * @param propId
	 * @return
	 */
	public PropertyResult getPropertyValue(String propId);

	/**
	 * Explicitely generates a new value for a passive property.
	 * 
	 * @param propId
	 * @return
	 */
	public void generatePassiveValue(String propId);

	/**
	 * Sets a remote data point to a new value
	 * 
	 * @param propId
	 * @param newValue
	 */
	public void setPropertyValue(String propId, Object newValue);

	public void addProxyListener(IWrapperListener listener);

	/**
	 * Start collecting data for all active properties
	 */
	public void start();

	/**
	 * Stop collecting data for all active properties
	 */
	public void stop();
}
