/*******************************************************************************
 * Copyright (C) 2022 the Eclipse BaSyx Authors
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
package org.eclipse.basyx.examples.scenarios.authorization.shared;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A client that is unauthenticated regarding {@link org.eclipse.basyx.examples.scenarios.authorization.combined.AuthorizedAASServerAndRegistryUsingComponentsExecutable} and {@link org.eclipse.basyx.examples.scenarios.authorization.combined.AuthorizedAASServerAndRegistryUsingSDKExecutable}.
 *
 * @author wege
 */
public class UnauthenticatedClient {
	private static Logger logger = LoggerFactory.getLogger(UnauthenticatedClient.class);

	public void tryGET(final String targetUrl) {
		try {
			final HttpClient httpClient = HttpClients.createDefault();
			final HttpGet httpGet = new HttpGet(targetUrl);

			logger.info("requesting GET {} without access token", targetUrl);

			final HttpResponse httpResponse = httpClient.execute(httpGet);

			logger.info("response status:");
			logger.info("{}", httpResponse.getStatusLine());

			final HttpEntity responseEntity = httpResponse.getEntity();
			final String responseEntityString = EntityUtils.toString(responseEntity);

			logger.info("response payload:");
			logger.info("{}", responseEntityString);
		} catch (final IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
