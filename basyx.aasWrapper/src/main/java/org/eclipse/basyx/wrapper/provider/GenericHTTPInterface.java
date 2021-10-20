/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.wrapper.provider;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringJoiner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.basyx.vab.exception.provider.MalformedRequestException;
import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.protocol.http.server.BasysHTTPServlet;
import org.eclipse.basyx.vab.protocol.http.server.ExceptionToHTTPCodeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.ByteSource;

/**
 * Implements a generic HTTP interface based on a JSONProvider that implements
 * GET, PUT, POST, PATCH and DELETE and supports returning objects for each of the
 * primitive. For PUT, POST and PATCH, it also supports passing objects.
 * 
 * @author espen
 *
 */
public class GenericHTTPInterface extends BasysHTTPServlet {
	private static Logger logger = LoggerFactory.getLogger(GenericHTTPInterface.class);
	
	private static final String CHARSET = "UTF-8";
	private static final String MIMETYPE = "application/json";

	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	private final JSONProvider provider;

	/**
	 * Constructor
	 */
	public GenericHTTPInterface(IWrapperProvider backend) {
		this.provider = new JSONProvider(backend);
	}

	/**
	 * Implement "Get" operation
	 * 
	 * Process HTTP get request - get sub model property value
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String path = extractPath(req);
			prepareResponse(resp);
			provider.processGet(path, resp.getOutputStream());
		} catch (IOException e) {
			logger.warn("Exception in HTTP-GET", e);
		} catch (ProviderException e) {
			int httpCode = ExceptionToHTTPCodeMapper.mapFromException(e);
			resp.setStatus(httpCode);
			logger.debug("Exception in GET request - response-code: " + httpCode, e);
		}
	}
	
	/**
	 * Implement "Set" operation
	 */
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String path = extractPath(req);
			String serValue = extractSerializedValue(req);
			prepareResponse(resp);
			provider.processPut(path, serValue, resp.getOutputStream());
		} catch (IOException e) {
			logger.warn("Exception in HTTP-PUT", e);
		} catch (ProviderException e) {
			int httpCode = ExceptionToHTTPCodeMapper.mapFromException(e);
			resp.setStatus(httpCode);
			logger.debug("Exception in PUT request - response-code: " + httpCode, e);
		}
	}

	
	/**
	 * <pre>
	 * Handle HTTP POST operation. Creates a new Property, Operation, Event,
	 * Submodel or AAS or invokes an operation.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String path = extractPath(req);
			String serValue = extractSerializedValue(req);
			prepareResponse(resp);
			provider.processPost(path, serValue, resp.getOutputStream());
		} catch (IOException e) {
			logger.warn("Exception in HTTP-POST", e);
		} catch (ProviderException e) {
			int httpCode = ExceptionToHTTPCodeMapper.mapFromException(e);
			resp.setStatus(httpCode);
			logger.debug("Exception in POST request - response-code: " + httpCode, e);
		}
	}

	
	/**
	 * Handle a HTTP PATCH operation. Updates a map or collection
	 * 
	 */
	@Override
	protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String path = extractPath(req);
			String serValue = extractSerializedValue(req);
			prepareResponse(resp);
			provider.processPatch(path, serValue, resp.getOutputStream());
		} catch (IOException e) {
			logger.warn("Exception in HTTP-PATCH", e);
		} catch (ProviderException e) {
			int httpCode = ExceptionToHTTPCodeMapper.mapFromException(e);
			resp.setStatus(httpCode);
			logger.debug("Exception in PATCH request - response-code: " + httpCode, e);
		}
	}

	
	/**
	 * Implement "Delete" operation. Deletes any resource under the given path.
	 */
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String path = extractPath(req);
			prepareResponse(resp);
			provider.processDelete(path, resp.getOutputStream());
		} catch (IOException e) {
			logger.warn("Exception in HTTP-DELETE", e);
		} catch (ProviderException e) {
			int httpCode = ExceptionToHTTPCodeMapper.mapFromException(e);
			resp.setStatus(httpCode);
			logger.debug("Exception in DELETE request - response-code: " + httpCode, e);
		}
	}

	
	private String extractPath(HttpServletRequest req) {
		// Extract path
		String uri = req.getRequestURI();

		// Normalizes URI
		String nUri = "/" + VABPathTools.stripSlashes(uri);
		String contextPath = req.getContextPath();
		if (nUri.startsWith(contextPath) && nUri.length() > getEnvironmentPathSize(req) - 1) {
			String path = nUri.substring(getEnvironmentPathSize(req));
			String extractedParameters = extractParameters(req);

			path = VABPathTools.stripSlashes(path);

			if (extractedParameters.isEmpty()) {
				path += "/";
			} else {
				path += extractedParameters;
			}
			return path;
		}
		throw new MalformedRequestException("The passed path " + uri + " is not a possbile path for this server.");
	}

	/**
	 * Extracts request parameters from the request
	 * 
	 * @param req
	 * @return
	 */
	private String extractParameters(HttpServletRequest req) {
		Enumeration<String> parameterNames = req.getParameterNames();
		
		// Collect list of parameters
		List<String> parameters = new ArrayList<>();
		while (parameterNames.hasMoreElements()) {

			StringBuilder ret = new StringBuilder();
			String paramName = parameterNames.nextElement();
			ret.append(paramName);
			ret.append("=");

			String[] paramValues = req.getParameterValues(paramName);
			for (int i = 0; i < paramValues.length; i++) {
				ret.append(paramValues[i]);
			}
			parameters.add(ret.toString());

		}

		// If no parameter is existing, return an empty string. Else join the parameters
		// and return them prefixed with a ?
		if (parameters.isEmpty()) {
			return "";
		} else {
			StringJoiner joiner = new StringJoiner("&");
			parameters.stream().forEach(joiner::add);
			return "?" + joiner.toString();
		}
	}

	private int getEnvironmentPathSize(HttpServletRequest req) {
		return req.getContextPath().length() + req.getServletPath().length();
	}


	/**
	 * Read serialized value 
	 * @param req
	 * @return
	 * @throws IOException
	 */
	private String extractSerializedValue(HttpServletRequest req) throws IOException {
		// https://www.baeldung.com/convert-input-stream-to-string#guava
		ByteSource byteSource = new ByteSource() {
	        @Override
	        public InputStream openStream() throws IOException {
	            return req.getInputStream();
	        }
	    };
	 
		return byteSource.asCharSource(StandardCharsets.UTF_8).read();
	}

	private void prepareResponse(HttpServletResponse resp) {
		resp.setContentType(MIMETYPE);
		resp.setCharacterEncoding(CHARSET);
		resp.setStatus(200);
	}
}
