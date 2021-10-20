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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.api.IAASRegistry;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.ConnectedProperty;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.restapi.MultiSubmodelElementProvider;
import org.eclipse.basyx.vab.factory.java.ModelProxyFactory;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorFactory;
import org.eclipse.basyx.wrapper.exception.WrapperRequestException;
import org.eclipse.basyx.wrapper.receiver.configuration.AASPropertyConfiguration;
import org.eclipse.basyx.wrapper.receiver.configuration.PropertyConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wrapper service for seperately querying the configured properties.
 * 
 * @author espen
 *
 */
public class SeparateAASService implements IPropertyWrapperService {
	private static final Logger logger = LoggerFactory.getLogger(SeparateAASService.class);

	// Registry
	protected IAASRegistry registry;

	// Property configurations
	protected Map<String, String> aasIds = new HashMap<>();
	protected Map<String, String> submodelIds = new HashMap<>();
	protected Map<String, String> shortIds = new HashMap<>();
	protected Map<String, Integer> maxValues = new HashMap<>();
	protected Set<String> activeProperties = new HashSet<>();

	// Current data
	protected Map<String, PropertyResult> propertyResults = new HashMap<>();

	// Additional
	protected Map<String, String> submodelCachedEndpoints = new HashMap<>();
	protected Map<String, Thread> threads = new HashMap<>();
	protected List<IWrapperListener> listeners = new ArrayList<>();

	public SeparateAASService(IAASRegistry registry, Collection<PropertyConfiguration> configs) {
		this.registry = registry;
		configs.forEach(this::addPropertyConfig);
	}

	@Override
	public void addPropertyConfig(PropertyConfiguration config) {
		if (config instanceof AASPropertyConfiguration) {
			AASPropertyConfiguration aasConfig = (AASPropertyConfiguration) config;

			String propId = config.getId();
			logger.info("Initialize " + propId);
			aasIds.put(propId, aasConfig.getAAS());
			submodelIds.put(propId, aasConfig.getSubmodel());
			shortIds.put(propId, aasConfig.getShortId());
			propertyResults.put(propId, new PropertyResult(propId, config.getMaxValues()));
			if (config.getActive()) {
				activeProperties.add(propId);
				int interval = config.getInterval(); // => in ms
				threads.put(propId, createPropertyThread(propId, interval));
			}
		}
	}

	private AASDescriptor getAASDescriptorFromRegistry(String aasId) {
		// Assume custom AAS identifier => aas identifier type doesn't really matter for lookup
		IIdentifier aasIdentifier = new Identifier(IdentifierType.CUSTOM, aasId);
		return registry.lookupAAS(aasIdentifier);
	}

	private String getSubmodelEndpoint(String aasId, String submodelId) {
		String aasSmId = aasId + "::" + submodelId;
		String smEndpoint = submodelCachedEndpoints.get(aasSmId);

		if (smEndpoint == null) {
			// Retrieve AAS descriptor from the registry to parse the submodel endpoint
			try {
				AASDescriptor aasDescriptor = getAASDescriptorFromRegistry(aasId);
				SubmodelDescriptor smDescriptor = aasDescriptor.getSubmodelDescriptorFromIdShort(submodelId);

				if (smDescriptor == null) {
					throw new WrapperRequestException("Could not look up descriptor for SubModel '" + submodelId
							+ "' since it does not exist in AAS '" + aasId + "'");
				}

				smEndpoint = smDescriptor.getFirstEndpoint();

				// Cache retrieved submodel endpoints
				submodelCachedEndpoints.put(aasSmId, smEndpoint);
			} catch (Exception e) {
				throw new WrapperRequestException("Could not look up descriptor for AAS '" + aasId
						+ "' since it does not exist or is not available");
			}
		}

		return smEndpoint;
	}

	private IProperty getConnectedProperty(String smEndpoint, String shortId) {
		// Build the property address from that (and assume it's there)
		String propEndpoint = VABPathTools.concatenatePaths(smEndpoint, MultiSubmodelElementProvider.ELEMENTS, shortId);

		// Create a "ConnectedProperty" that gives access to the property
		ModelProxyFactory mpf = new ModelProxyFactory(new HTTPConnectorFactory());
		return new ConnectedProperty(mpf.createProxy(propEndpoint));
	}

	private Object getSinglePropertyValue(String propId) {
		try {
			String aasId = aasIds.get(propId);
			String submodelId = submodelIds.get(propId);
			String shortId = shortIds.get(propId);
			String smEndpoint = getSubmodelEndpoint(aasId, submodelId);
			IProperty property = getConnectedProperty(smEndpoint, shortId);
			return property.getValue();
		} catch (Exception e) {
			throw new WrapperRequestException(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public PropertyResult getPropertyValue(String propId) {
		// Clone a result to prevent changes in the returned value
		return new PropertyResult(propertyResults.get(propId));
	}

	@Override
	public void setPropertyValue(String propId, Object newValue) {
		try {
			String aasId = aasIds.get(propId);
			String submodelId = submodelIds.get(propId);
			String shortId = shortIds.get(propId);
			String smEndpoint = getSubmodelEndpoint(aasId, submodelId);
			IProperty property = getConnectedProperty(smEndpoint, shortId);
			property.setValue(newValue);
		} catch (Exception e) {
			throw new WrapperRequestException(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void generatePassiveValue(String propId) {
		if (activeProperties.contains(propId)) {
			throw new WrapperRequestException("Property with id '" + propId + "' is active");
		}
		retrieveValue(propId);
	}

	private Thread createPropertyThread(final String propId, final int intervalTime) {
		return new Thread(() -> {
			Thread thisThread = Thread.currentThread();
			while (thisThread == threads.get(propId)) {
				retrieveValue(propId);
				try {
					Thread.sleep(intervalTime);
				} catch (InterruptedException e) {
				}
			}
		});
	}

	private void retrieveValue(String propId) {
		PropertyResult values = propertyResults.get(propId);
		if (values == null) {
			throw new WrapperRequestException("Property with id '" + propId + "' does not exist");
		}
		Object newValue = getSinglePropertyValue(propId);
		DataPoint dataPoint = new DataPoint(newValue);
		values.addDataPoint(dataPoint);

		// inform listeners
		for (IWrapperListener listener : listeners) {
			listener.newValue(propId, new PropertyResult(values));
		}
	}

	@Override
	public void start() {
		logger.info("Starting " + threads.size() + " threads");
		for (Thread t : threads.values()) {
			t.start();
		}
	}

	@Override
	public void stop() {
		threads.clear();
	}

	@Override
	public void addProxyListener(IWrapperListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public Set<String> getValidProperties() {
		return propertyResults.keySet();
	}
}
