/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.wrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.basyx.aas.registration.api.IAASRegistry;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.components.configuration.BaSyxConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.eclipse.basyx.wrapper.provider.GenericHTTPInterface;
import org.eclipse.basyx.wrapper.provider.GenericWrapperProvider;
import org.eclipse.basyx.wrapper.provider.IWrapperProvider;
import org.eclipse.basyx.wrapper.provider.aas.AASWrapperProvider;
import org.eclipse.basyx.wrapper.provider.grafana.GrafanaProvider;
import org.eclipse.basyx.wrapper.provider.streamsheets.StreamsheetsProvider;
import org.eclipse.basyx.wrapper.receiver.IPropertyWrapperService;
import org.eclipse.basyx.wrapper.receiver.SeparateAASService;
import org.eclipse.basyx.wrapper.receiver.configuration.AASPropertyConfiguration;
import org.eclipse.basyx.wrapper.receiver.configuration.PropertyConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Runs the AAS wrapper for the given configuration. The configuration is loaded
 * from the wrapper.properties by default, but can also be given by an external file.
 * For this, the path has to be specified in the BASYX_WRAPPER java property or system
 * environment variable.
 * 
 * @author espen
 *
 */
public class AASWrapperExecutable {
	private static final Logger logger = LoggerFactory.getLogger(AASWrapperExecutable.class);
	public static final int PORT = 6500;
	public static final String HOST = "aas-wrapper";

	private static String registryAddress;
	private static Map<String, PropertyConfiguration> propertyConfigs;
	private static Map<String, String> activeProviders = new HashMap<>();
	private static Map<String, String> providerPaths = new HashMap<>();

	public static void main(String[] args) throws Exception {
		logger.info("Wrapper started, waiting for other components");
		// Use docker-compose health check for registry and aas instead
		Thread.sleep(6000);

		logger.info("Loading configuration...");
		loadConfigs("wrapper.properties");

		logger.info("Creating proxy for " + propertyConfigs.size() + " properties...");
		IAASRegistry registry = new AASRegistryProxy(registryAddress);
		IPropertyWrapperService connector = new SeparateAASService(registry, propertyConfigs.values());

		logger.info("Creating " + activeProviders.size() + " providers...");
		List<IWrapperProvider> providers = createProxyProviders();
		providers.forEach(p -> p.initialize(connector, registry, propertyConfigs.values()));

		if (!providers.isEmpty()) {
			logger.info("Starting property proxy service...");
			connector.start();
			logger.info("Starting proxy providers...");
			createServlet(providers);
		} else {
			logger.info("No providers have been created!");
		}

		logger.info("Finished");
	}

	private static List<IWrapperProvider> createProxyProviders() {
		List<IWrapperProvider> providers = new ArrayList<>();
		for (Entry<String, String> entry : activeProviders.entrySet()) {
			String providerId = entry.getKey();
			String providerType = entry.getValue();
			IWrapperProvider newProvider;
			switch (providerType) {
				case (AASWrapperProvider.TYPE):
					newProvider = new AASWrapperProvider(HOST, PORT);
					break;
				case (GrafanaProvider.TYPE):
					newProvider = new GrafanaProvider();
					break;
				case (StreamsheetsProvider.TYPE):
					newProvider = new StreamsheetsProvider();
					break;
				default:
					newProvider = new GenericWrapperProvider();
			}
			String newPath = providerPaths.get(providerId);
			if (newPath != null) {
				newProvider.setProviderPath(newPath);
			}
			providers.add(newProvider);
		}
		return providers;
	}

	private static void loadConfigs(String resourceFileName) {
		// Load resource file
		logger.info("Loading proxy property file " + resourceFileName);
		Map<String, String> configMap = new HashMap<>();
		BaSyxConfiguration config = new BaSyxConfiguration(configMap);
		config.loadFileOrDefaultResource("BASYX_WRAPPER", "wrapper.properties");

		loadRegistryConfig(config);
		propertyConfigs = loadPropertyConfigs(configMap);
		loadProviderConfigs(configMap);
	}

	private static void loadRegistryConfig(BaSyxConfiguration config) {
		registryAddress = config.getProperty("registry.endpoint");
		logger.info("Registry address: " + registryAddress);
	}

	private static Map<String, PropertyConfiguration> loadPropertyConfigs(Map<String, String> configMap) {
		Map<String, PropertyConfiguration> configs = new HashMap<>();
		for (Entry<String, String> entry : configMap.entrySet()) {
			String key = entry.getKey();
			String[] propKeys = key.split("\\.");
			if (propKeys.length != 3 || !propKeys[0].equals(PropertyConfiguration.INDEX)) {
				continue;
			}

			String propId = propKeys[1];
			String parameter = propKeys[2];
			String value = entry.getValue();
			if (!configs.containsKey(propId)) {
				logger.info("Creating property " + propId);
				PropertyConfiguration propConfig = new PropertyConfiguration();
				propConfig.setId(propId);
				configs.put(propId, propConfig);
			}
			setPropertyParameter(configs, propId, parameter, value);
		}
		return configs;
	}

	private static void loadProviderConfigs(Map<String, String> configMap) {
		for (Entry<String, String> entry : configMap.entrySet()) {
			String key = entry.getKey();
			String[] propKeys = key.split("\\.");
			if (propKeys.length != 3 || !propKeys[0].equals(GenericWrapperProvider.INDEX)) {
				continue;
			}

			String providerId = propKeys[1];
			String parameter = propKeys[2];
			String value = entry.getValue();

			switch (parameter) {
				case ("type"):
					activeProviders.put(providerId, value);
					logger.info("Setting provider '" + providerId + "': type=" + value);
					break;
				case ("path"):
					providerPaths.put(providerId, value);
					logger.info("Setting provider '" + providerId + "': path=" + value);
					break;
				default:
					logger.info("Invalid config for provider '" + providerId + "': " + parameter + "=" + value);
			}

		}
	}

	private static void setPropertyParameter(Map<String, PropertyConfiguration> configs, String propId,
			String parameter, String value) {
		logger.info("Setting property '" + propId + "': " + parameter + "=" + value);
		PropertyConfiguration prop = configs.get(propId);
		prop.put(parameter, value);
		if (parameter.equals(PropertyConfiguration.TYPE)) {
			// Possibility to add more property types
			switch (value) {
				case (AASPropertyConfiguration.TYPE):
					configs.put(propId, new AASPropertyConfiguration(prop));
					break;
				default:
					logger.error("Unknown property type: " + value);
			}
		}
	}

	private static void createServlet(List<IWrapperProvider> providers) {
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration();
		contextConfig.loadFromDefaultSource();
		BaSyxContext context = contextConfig.createBaSyxContext();
		for (IWrapperProvider provider : providers) {
			context.addServletMapping(provider.getProviderPath() + "/*",
					new GenericHTTPInterface(provider));
		}
        BaSyxHTTPServer server = new BaSyxHTTPServer(context);
		server.start();
	}
}

