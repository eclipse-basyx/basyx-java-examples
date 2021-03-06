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
package org.eclipse.basyx.wrapper.provider;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.eclipse.basyx.vab.coder.json.metaprotocol.Result;
import org.eclipse.basyx.vab.coder.json.serialization.DefaultTypeFactory;
import org.eclipse.basyx.vab.coder.json.serialization.GSONTools;
import org.eclipse.basyx.vab.exception.provider.MalformedRequestException;
import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation variant of the JSONProvider for a generic HTTPModelProvider backend
 * 
 * @author espen
 *
 */
public class JSONProvider {
	
	private static Logger logger = LoggerFactory.getLogger(JSONProvider.class);

	/**
	 * Reference to serializer / deserializer
	 */
	protected GSONTools serializer = null;

	private HTTPModelProvider backend;
	
	/**
	 * Constructor
	 */
	public JSONProvider(HTTPModelProvider backend) {
		this.backend = backend;
		serializer = new GSONTools(new DefaultTypeFactory());
	}

	/**
	 * Marks success as false and delivers exception cause messages 
	 * @param e
	 * @return
	 */
	private String serialize(Exception e) {
		// Create Ack
		Result result = new Result(e);
		
		// Serialize the whole thing
		return serialize(result);
	}
	
	
	/**
	 * Serialize IResult (HashMap)
	 * @param string
	 * @return
	 */
	private String serialize(Result string) {
		// Serialize the whole thing
		return serializer.serialize(string);
	}
	

	/**
	 * Send Error
	 * @param e
	 * @param path
	 * @param resp
	 */
	private void sendException(OutputStream resp, Exception e) {
		
		// Serialize Exception
		String jsonString = serialize(e);
		try {
			resp.write(jsonString.getBytes(StandardCharsets.UTF_8));
		} catch(IOException innerE) {
			throw new ProviderException("Failed to send Exception '" + e.getMessage() + "' to client", innerE);
		}
		
		//If the Exception is a ProviderException, just rethrow it
		if(e instanceof ProviderException) {
			throw (ProviderException) e;
		}
		
		//If the Exception is not a ProviderException encapsulate it in one and log it
		logger.error("Unknown Exception in JSONProvider", e);
		throw new ProviderException(e);
	}

	
	/**
	 * Extracts parameter from JSON and handles de-serialization errors
	 */
	private Object extractParameter(String serializedJSONValue) {
		// Return value
		Object result = null;
		try {
			// Deserialize json body
			result = serializer.deserialize(serializedJSONValue);
		} catch (Exception e) {
			//JSON could not be deserialized
			throw new MalformedRequestException(e);
		}
		
		return result;
	}
	

	public void processGet(String path, OutputStream outputStream) {
		try {
			logger.info("GET '" + path + "'");
			Object value = backend.get(path);
			String jsonString = serializer.serialize(value);
			outputStream.write(jsonString.getBytes(StandardCharsets.UTF_8));
		} catch (Exception e) {
			sendException(outputStream, e);
		}
	}

	public void processDelete(String path, OutputStream outputStream) {
		try {
			logger.info("DELETE '" + path + "'");
			Object value = backend.delete(path);
			String jsonString = serializer.serialize(value);
			outputStream.write(jsonString.getBytes(StandardCharsets.UTF_8));
		} catch (Exception e) {
			sendException(outputStream, e);
		}
	}

	public void processPost(String path, String serializedJSONValue, OutputStream outputStream) {
		try {
			Object parameter = extractParameter(serializedJSONValue);
			Object value = backend.post(path, parameter);
			String jsonString = serializer.serialize(value);
			outputStream.write(jsonString.getBytes(StandardCharsets.UTF_8));
		} catch (Exception e) {
			sendException(outputStream, e);
		}
	}

	public void processPatch(String path, String serializedJSONValue, OutputStream outputStream) {
		try {
			logger.info("PATCH " + path + "'");
			Object parameter = extractParameter(serializedJSONValue);
			Object value = backend.patch(path, parameter);
			String jsonString = serializer.serialize(value);
			outputStream.write(jsonString.getBytes(StandardCharsets.UTF_8));
		} catch (Exception e) {
			sendException(outputStream, e);
		}
	}

	public void processPut(String path, String serializedJSONValue, OutputStream outputStream) {
		try {
			logger.info("PUT " + path + "'");
			Object parameter = extractParameter(serializedJSONValue);
			Object value = backend.put(path, parameter);
			String jsonString = serializer.serialize(value);
			outputStream.write(jsonString.getBytes(StandardCharsets.UTF_8));
		} catch (Exception e) {
			sendException(outputStream, e);
		}
	}
}
