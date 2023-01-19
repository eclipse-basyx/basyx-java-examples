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

import java.io.IOException;

import org.eclipse.paho.client.mqttv3.MqttException;

import io.moquette.broker.Server;
import io.moquette.broker.config.ClasspathResourceLoader;
import io.moquette.broker.config.IConfig;
import io.moquette.broker.config.IResourceLoader;
import io.moquette.broker.config.ResourceLoaderConfig;

/**
 * This scenario starts an MQTT broker on <i>tcp://localhost:1884</i> and a
 * simulated heater device with its gateway component. For detailed
 * documentation of the heater and its HTTP/REST and MQTT interface, see
 * {@link HeaterGateway}.<br>
 * <br>
 * After this scenario is started, the <i>compose.yml</i> file in
 * src/main/resources/deviceintegration can be started via <i>docker compose
 * up</i>. After successful start, the AAS Web GUI available on
 * http://localhost:3000 can be used for interacting with the scenario. As
 * registry endpoint, choose <i>http://localhost:4000/registry</i><br>
 * <br>
 * For a detailed documentation, see the BaSyx wiki
 * 
 * @author schnicke
 *
 */
public class DeviceIntegrationScenario {
	public static void main(String[] args) throws IOException, MqttException {
		startMqttBroker();
		startGateway();
	}

	private static void startGateway() throws MqttException {
		HeaterDevice heater = new HeaterDevice();
		HeaterGateway gateway = new HeaterGateway(heater);

		gateway.startComponent();
	}

	private static void startMqttBroker() throws IOException {
		Server mqttBroker = new Server();
		IResourceLoader classpathLoader = new ClasspathResourceLoader();
		final IConfig classPathConfig = new ResourceLoaderConfig(classpathLoader);
		mqttBroker.startServer(classPathConfig);
	}

}
