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
