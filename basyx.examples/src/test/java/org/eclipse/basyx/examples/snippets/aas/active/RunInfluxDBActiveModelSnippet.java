/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
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
