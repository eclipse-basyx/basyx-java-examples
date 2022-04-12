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
package org.eclipse.basyx.wrapper.provider;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.basyx.aas.registration.api.IAASRegistry;
import org.eclipse.basyx.vab.exception.provider.MalformedRequestException;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.wrapper.receiver.IPropertyWrapperService;
import org.eclipse.basyx.wrapper.receiver.configuration.PropertyConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wraps any connector API with a ModelProvider
 * 
 * @author espen
 *
 */
public class GenericWrapperProvider implements IWrapperProvider {
	public static final String INDEX = "providers";
	private static final Logger logger = LoggerFactory.getLogger(GenericWrapperProvider.class);

	protected IPropertyWrapperService proxyService;
	protected String providerPath = "provider";
	protected Set<String> passiveProperties = new HashSet<>();

	@Override
	public void initialize(IPropertyWrapperService wrapperService, IAASRegistry registry,
			Collection<PropertyConfiguration> configs) {
		logger.info(
				"Initializing provider '" + this.getClass().getSimpleName() + "' on path " + this.getProviderPath());
		this.proxyService = wrapperService;
		for (PropertyConfiguration config : configs) {
			if (!config.getActive()) {
				passiveProperties.add(config.getId());
			}
		}
	}

	@Override
	public Object get(String path) {
		path = preparePath(path);
		if ( passiveProperties.contains(path) ) {
			proxyService.generatePassiveValue(path);
		}
		return proxyService.getPropertyValue(path);
	}

	@Override
	public Object post(String path, Object newValue) {
		path = preparePath(path);
		proxyService.setPropertyValue(path, newValue);
		return newValue;
	}

	protected String preparePath(String path) {
		VABPathTools.checkPathForNull(path);
		path = VABPathTools.stripSlashes(path);
		return path;
	}

	@Override
	public String getProviderPath() {
		return providerPath;
	}

	@Override
	public void setProviderPath(String path) {
		this.providerPath = path;
	}

	@Override
	public Object delete(String path) {
		throw new MalformedRequestException("Deleting elements is not supported");
	}

	@Override
	public Object put(String path, Object data) {
		throw new MalformedRequestException("Setting elements is not supported");
	}

	@Override
	public Object patch(String path, Object data) {
		throw new MalformedRequestException("Patching elements is not supported");
	}
}
