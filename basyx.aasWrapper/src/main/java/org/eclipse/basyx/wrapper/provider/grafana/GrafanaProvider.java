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
