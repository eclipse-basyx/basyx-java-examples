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
package org.eclipse.basyx.examples.snippets.aas.active;

import org.eclipse.basyx.examples.snippets.aas.active.tasks.InfluxDBTask;
import org.eclipse.basyx.submodel.restapi.SubmodelProvider;
import org.eclipse.basyx.tools.aas.active.ActiveModel;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProviderHelper;
import org.junit.Ignore;
import org.junit.Test;

/**
 * FIXME: This should also run on the postgres backend. Until then, this test is
 * disabled <br/>
 * 
 * Uses a simple influxDB instance to store temperature values - docker run
 * --name=influxdb -d -p 8086:8086 influxdb:latest (or: docker start influxdb) -
 * docker exec -it influxdb influx > CREATE DATABASE mydb > USE mydb *run this
 * snippet* > SELECT * FROM temperature
 * 
 * @author espen
 *
 */
public class RunInfluxDBActiveModelSnippet {
	/**
	 * Test active model writing value to an influxDB database
	 */
	@Ignore
	@Test
	public void snippet() throws Exception {
		// Create the model provider for the active model
		IModelProvider modelProvider = new SubmodelProvider();
		modelProvider.createValue("temperature", VABLambdaProviderHelper.createSimple(() -> {
			return 30d + (Math.random() * 10d - 5d);
		}, null));

		// Create the active model
		ActiveModel activeModel = new ActiveModel(modelProvider);

		// Add a task to the model that writes the temperature value to a database every 500ms
		InfluxDBTask writeTemperatureTask = new InfluxDBTask("/temperature", "http://localhost:8086/", "mydb",
				"temperature");
		activeModel.runTask(50, writeTemperatureTask);

		// Stop the model and clean it up
		Thread.sleep(225);
		activeModel.clear();
	}
}
