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
import org.eclipse.basyx.components.configuration.BaSyxSecurityConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxSecurityConfiguration.AuthorizationStrategy;
import org.eclipse.basyx.examples.scenarios.authorization.shared.SharedConfig.UserCredentials;
import org.eclipse.basyx.extensions.shared.authorization.internal.KeycloakService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A client that is authenticated but unauthorized regarding
 * {@link org.eclipse.basyx.examples.scenarios.authorization.combined.AuthorizedAASServerAndRegistryUsingComponentsExecutable}
 * and
 * {@link org.eclipse.basyx.examples.scenarios.authorization.combined.AuthorizedAASServerAndRegistryUsingSDKExecutable}.
 *
 * @author wege
 */
public class UnauthorizedClient {
	private static Logger logger = LoggerFactory.getLogger(UnauthorizedClient.class);

	private final BaSyxSecurityConfiguration securityConfig;
	private final KeycloakService keycloakService;
	private final String clientId;
	private final String clientSecret;
	private final UserCredentials userCredentials;

	public UnauthorizedClient() {
		securityConfig = SharedConfig.getSecurityConfig();
		final AuthorizationStrategy strategy = AuthorizationStrategy.valueOf(securityConfig.getAuthorizationStrategy());
		switch (strategy) {
		case SimpleRbac: {
			clientId = SharedConfig.KEYCLOAK_CLIENT_ID;
			clientSecret = SharedConfig.KEYCLOAK_CLIENT_SECRET;
			break;
		}
		case GrantedAuthority:
			clientId = SharedConfig.KEYCLOAK_SCOPELESS_CLIENT_ID;
			clientSecret = SharedConfig.KEYCLOAK_SCOPELESS_CLIENT_SECRET;
			break;
		default:
			throw new UnsupportedOperationException("no handler");
		}
		userCredentials = SharedConfig.UNAUTHORIZED_USER_CREDENTIALS;

		keycloakService = createKeycloakClient();
	}

	private KeycloakService createKeycloakClient() {
		final String serverUrl = securityConfig.getAuthorizationStrategyJwtBearerTokenAuthenticationConfigurationProviderKeycloakServerUrl();
		final String realm = securityConfig.getAuthorizationStrategyJwtBearerTokenAuthenticationConfigurationProviderKeycloakRealm();

		logger.info("use keycloak: serverUrl={} realm={}", serverUrl, realm);

		final KeycloakService keycloak = new KeycloakService(serverUrl, realm);

		keycloak.setCredentials(clientId, clientSecret, userCredentials.getUserName(), userCredentials.getPassword());

		return keycloak;
	}

	public void tryGET(final String targetUrl) {
		try {
			logger.info("get access token using credentials clientId={} clientSecret={} userName={} password={}", clientId, clientSecret, userCredentials.getUserName(), userCredentials.getPassword());

			final String bearerToken = keycloakService.getTokenAsBearer().orElseThrow(() -> new RuntimeException("could not get token"));

			logger.info("retrieved access token {}", bearerToken);

			final HttpClient httpClient = HttpClients.createDefault();
			final HttpGet httpGet = new HttpGet(targetUrl);

			httpGet.setHeader("Authorization", bearerToken);

			logger.info("requesting GET {} with access token {}", targetUrl, bearerToken);

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
