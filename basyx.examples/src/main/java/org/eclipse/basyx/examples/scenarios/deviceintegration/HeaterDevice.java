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

import org.eclipse.basyx.components.IComponent;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * This class represents a legacy heater being configured with a temperature in
 * Fahrenheit <br>
 * The heater updates the temperature every second in increments/decrements of
 * one to achieve the targetTemperature
 * 
 * @author schnicke
 *
 */
public class HeaterDevice implements IComponent {
	private volatile int targetTemperature = 1;
	private volatile int currentTemperature;

	private volatile boolean stopHeaterThread;

	private Thread heaterThread;

	private HeaterChangeListener listener;

	public HeaterDevice() throws MqttException {
		heaterThread = createHeaterThread();
	}

	public void setChangeListener(HeaterChangeListener listener) {
		this.listener = listener;
	}

	/**
	 * 
	 * @return the temperature in Fahrenheit
	 */
	public int getTargetTemperature() {
		return targetTemperature;
	}

	/**
	 * 
	 * @param targetTemperature
	 *            the temperature in Fahrenheit
	 */
	public void setTargetTemperature(int targetTemperature) {
		this.targetTemperature = targetTemperature;
	}

	private Thread createHeaterThread() {
		return new Thread(this::heatingRunner);
	}

	private void heatingRunner() {
			while (!stopHeaterThread) {
				sleep(1000);
				updateTemperature();
				notifyTemperature();
			}
	}

	private void notifyTemperature() {
		if (listener == null) return;
		
		listener.currentTemperature(currentTemperature);
	}

	private void sleep(int sleep) {
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException dontCare) {
		}
	}

	private void updateTemperature() {
		if (currentTemperature > targetTemperature) {
			currentTemperature--;
		} else if (currentTemperature < targetTemperature) {
			currentTemperature++;
		}
	}

	@Override
	public void startComponent() {
			heaterThread.start();
	}

	@Override
	public void stopComponent() {
		stopHeaterThread = true;
	}
}
