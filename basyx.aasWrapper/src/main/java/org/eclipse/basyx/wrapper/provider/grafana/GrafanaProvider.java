/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.wrapper.provider.grafana;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.basyx.wrapper.provider.GenericWrapperProvider;
import org.eclipse.basyx.wrapper.receiver.PropertyResult;

/**
 * Grafana-specific proxy provider. Is based on a SimpleJson interface for grafana
 * 
 * @author espen
 */
public class GrafanaProvider extends GenericWrapperProvider {
	public static final String TYPE = "GRAFANA";

	public GrafanaProvider() {
		this.providerPath = "/grafana";
	}

	@Override
	public Object get(String path) {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object post(String path, Object value) {
		path = preparePath(path);

		String idShort = null;
		if (path.equals("search")) {
			Map<String, Object> request = (Map<String, Object>) value;
			idShort = (String) request.get("target");
			Set<String> results = getSearchResults(idShort);
			return results;
		} else if (path.equals("query")) {
			Map<String, Object> request = (Map<String, Object>) value;
			List<Object> targets = (List<Object>) request.get("targets");
			Map<String, Object> targetMap = (Map<String, Object>) targets.get(0);
			idShort = (String) targetMap.get("target");
		}

		if (idShort == null || idShort.isEmpty()) {
			return false;
		}

		PropertyResult result = (PropertyResult) super.get(idShort);
		GrafanaTimeseries response = new GrafanaTimeseries();
		response.addTarget(idShort, result);
		return response;
	}

	private Set<String> getSearchResults(String term) {
		Set<String> validProperties = proxyService.getValidProperties();
		return validProperties.stream()
				.filter(s -> s.toLowerCase().contains(term.toLowerCase()))
				.collect(Collectors.toSet());
	}
}
