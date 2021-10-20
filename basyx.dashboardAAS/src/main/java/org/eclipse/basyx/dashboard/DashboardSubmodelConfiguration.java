/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.dashboard;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.components.configuration.BaSyxConfiguration;

/**
 * Simple configuration for setting minimal and maxmimal values for the dashboard submodel
 * 
 * @author espen
 *
 */
public class DashboardSubmodelConfiguration extends BaSyxConfiguration {
	// Prefix for environment variables
	public static final String ENV_PREFIX = "BaSyxDashboardSubmodel_";

	// Default BaSyx Context configuration
	public static final int DEFAULT_MIN = 10;
	public static final int DEFAULT_MAX = 30;

	public static final String MIN = "Min";
	public static final String MAX = "Max";

	// The default path for the context properties file
	public static final String DEFAULT_CONFIG_PATH = "dashboardsubmodel.properties";

	// The default key for variables pointing to the configuration file
	public static final String DEFAULT_FILE_KEY = "BASYX_DASHBOARDSUBMODEL";

	public static Map<String, String> getDefaultProperties() {
		Map<String, String> defaultProps = new HashMap<>();
		defaultProps.put(MIN, Integer.toString(DEFAULT_MIN));
		defaultProps.put(MAX, Integer.toString(DEFAULT_MAX));
		return defaultProps;
	}

	/**
	 * Empty Constructor - use default values
	 */
	public DashboardSubmodelConfiguration() {
		super(getDefaultProperties());
	}

	/**
	 * Constructor with predefined value map
	 */
	public DashboardSubmodelConfiguration(Map<String, String> values) {
		super(values);
	}

	/**
	 * Constructor with initial configuration - docBasePath and hostname are default values
	 * 
	 * @param min The minimal temperature value
	 * @param max The maximal temperature value
	 */
	public DashboardSubmodelConfiguration(int min, int max) {
		this();
		setMin(min);
		setMax(max);
	}

	public void loadFromEnvironmentVariables() {
		String[] properties = { MIN, MAX };
		loadFromEnvironmentVariables(ENV_PREFIX, properties);
	}

	public void loadFromDefaultSource() {
		loadFileOrDefaultResource(DEFAULT_FILE_KEY, DEFAULT_CONFIG_PATH);
		loadFromEnvironmentVariables();
	}

	public void setMin(int min) {
		setProperty(MIN, Integer.toString(min));
	}

	public void setMax(int max) {
		setProperty(MIN, Integer.toString(max));
	}

	public int getMin() {
		return Integer.parseInt(getProperty(MIN));
	}

	public int getMax() {
		return Integer.parseInt(getProperty(MAX));
	}
}
