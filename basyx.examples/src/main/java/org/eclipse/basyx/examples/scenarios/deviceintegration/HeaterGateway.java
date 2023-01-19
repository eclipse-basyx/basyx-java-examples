/*******************************************************************************
 * Copyright (C) 2023 the Eclipse BaSyx Authors
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

package org.eclipse.basyx.examples.scenarios.deviceintegration;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.components.IComponent;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.ModelingKind;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;
import org.eclipse.basyx.submodel.restapi.OperationProvider;
import org.eclipse.basyx.vab.coder.json.serialization.DefaultTypeFactory;
import org.eclipse.basyx.vab.coder.json.serialization.GSONTools;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProvider;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProviderHelper;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * This class represents a gateway to a legacy heater offering the following
 * data interfaces:<br>
 * <li>GET on <i>http://localhost:8082/heater/targetTemperature/last</i> to
 * retrieve the last configured target temperature in degree Fahrenheit</li>
 * <li>POST on <i>http://localhost:8082/heater/setTargetTemperature/invoke</i>
 * with an Integer to configure the target temperature in degree Fahrenheit</li>
 * <li>Regular MQTT events on <i>tcp://localhost:1884</i> on the
 * <i>heater/temperature</i> topic</li> The payload of the MQTT events is as
 * follows: <br>
 * <br>
 * <code>
 * {<br>
 * 		"temperature": $temperatureInFahrenheit, <br>
 * 		"timestamp": $timestampInUnixTime <br>
 * }<br>
 * </code>
 * 
 * @author schnicke
 *
 */
public class HeaterGateway implements IComponent, HeaterChangeListener {
	private BaSyxHTTPServer heaterServer;
	private IMqttClient mqttClient;

	private GSONTools gsonTools = new GSONTools(new DefaultTypeFactory());
	private HeaterDevice heater;

	public HeaterGateway(HeaterDevice heater) throws MqttException {
		this.heater = heater;
		heater.setChangeListener(this);

		heaterServer = createHeaterServer();
		mqttClient = createMqttClient();
	}

	private IMqttClient createMqttClient() throws MqttException {
		return new MqttClient("tcp://localhost:1884", "device-integration-heater-device");
	}

	private BaSyxHTTPServer createHeaterServer() {
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration(8082, "");
		BaSyxContext context = contextConfig.createBaSyxContext();

		OperationProvider opProvider = createOperationProvider();
		VABHTTPInterface<OperationProvider> operationServlet = new VABHTTPInterface<>(opProvider);

		IModelProvider temperatureProvider = createTemperatureProvider();
		VABHTTPInterface<IModelProvider> targetTemperatureServlet = new VABHTTPInterface<>(temperatureProvider);

		context.addServletMapping("/heater/setTargetTemperature/*", operationServlet);
		context.addServletMapping("/heater/targetTemperature/last", targetTemperatureServlet);

		return new BaSyxHTTPServer(context);
	}

	private IModelProvider createTemperatureProvider() {
		Map<String, Object> lastTemperature = VABLambdaProviderHelper.createSimple(() -> heater.getTargetTemperature(), null);

		return new VABLambdaProvider(lastTemperature);
	}

	private OperationProvider createOperationProvider() {
		Operation setTargetTemperature = new Operation("setTargetTemperature");
		setTargetTemperature.setInvokable(this::setTargetTemperature);
		setTargetTemperature.setInputVariables(createInputVariables());

		return new OperationProvider(new VABLambdaProvider(setTargetTemperature));
	}

	private void setTargetTemperature(Object[] targetTemperatureObject) {
		int targetTemperature = (Integer) targetTemperatureObject[0];
		heater.setTargetTemperature(targetTemperature);
	}

	private Collection<OperationVariable> createInputVariables() {
		Property temperature = new Property("targetTemperature", 0);
		temperature.setKind(ModelingKind.TEMPLATE);
		OperationVariable opVar = new OperationVariable(temperature);

		return Collections.singleton(opVar);
	}

	private MqttMessage createMqttMessage(int temperature, long timestamp) {
		Map<String, Object> payloadMap = new HashMap<>();
		payloadMap.put("temperature", temperature);
		payloadMap.put("timestamp", timestamp);

		String payload = gsonTools.serialize(payloadMap);

		return new MqttMessage(payload.getBytes());
	}

	@Override
	public void startComponent() {
		try {
			heater.startComponent();
			mqttClient.connect();
			heaterServer.start();
		} catch (MqttException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void stopComponent() {
		heaterServer.shutdown();

		try {
			mqttClient.close();
		} catch (MqttException e) {
			e.printStackTrace();
		}

		heater.stopComponent();
	}

	@Override
	public void currentTemperature(int temperature) {
		try {
			MqttMessage msg = createMqttMessage(temperature, System.currentTimeMillis());
			mqttClient.publish("heater/temperature", msg);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
}
