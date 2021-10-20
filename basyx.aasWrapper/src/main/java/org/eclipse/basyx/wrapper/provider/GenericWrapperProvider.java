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
