/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.snippets.aas.active.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.eclipse.basyx.tools.aas.active.VABModelTask;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Task for writing a value to an influxDB
 * 
 * @author espen
 *
 */
public class InfluxDBTask implements VABModelTask {
	
	/**
	 * Initiates a logger using the current class
	 */
	private static final Logger logger = LoggerFactory.getLogger(InfluxDBTask.class);
	
	private String modelPath;
	private String dbUrl;
	private String dbName;
	private String valueName;

	public InfluxDBTask(String modelPath, String dbUrl, String dbName, String valueName) {
		this.modelPath = modelPath;
		this.dbUrl = dbUrl;
		this.dbName = dbName;
		this.valueName = valueName;
		clearData();
	}

	@Override
	public void execute(IModelProvider model) throws Exception {
		try {
			Object value = model.getValue(modelPath);
			String result = value == null ? "null" : value.toString();
			writeData(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeData(String value) {
		logger.debug("Writing " + value + " to " + dbName + "." + valueName);
		try {
			postInfluxDBCommand("write", valueName + " value=" + value);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void clearData() {
		logger.debug("Clearing previous InfluxDB entries for value: " + valueName);
		try {
			postInfluxDBCommand("query", "q=DROP MEASUREMENT " + valueName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void postInfluxDBCommand(String queryType, String queryCommand) throws IOException {
		URL url = new URL(dbUrl + queryType + "?db=" + dbName);
		HttpURLConnection con = (HttpURLConnection) (url).openConnection();
		con.setRequestMethod("POST");
		con.setDoInput(true);
		con.setDoOutput(true);
		con.connect();
		con.getOutputStream().write((queryCommand + "\n").getBytes());

		InputStream is = con.getInputStream();
		byte[] b = new byte[1024];
		String buffer = "";
		while (is.read(b) != -1) {
			buffer.concat(new String(b));
		}
		con.disconnect();
	}

}
