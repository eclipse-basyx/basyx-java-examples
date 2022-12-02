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
package org.eclipse.basyx.examples.scenarios.authorization.submodel;

import java.net.URISyntaxException;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.extensions.shared.authorization.internal.AuthenticationContextProvider;
import org.eclipse.basyx.extensions.shared.authorization.internal.AuthenticationGrantedAuthorityAuthenticator;
import org.eclipse.basyx.extensions.shared.authorization.internal.KeycloakService;
import org.eclipse.basyx.extensions.submodel.authorization.internal.AuthorizedSubmodelAPI;
import org.eclipse.basyx.extensions.submodel.authorization.internal.GrantedAuthoritySubmodelAPIAuthorizer;
import org.eclipse.basyx.submodel.restapi.SubmodelProvider;
import org.eclipse.basyx.submodel.restapi.api.ISubmodelAPI;
import org.eclipse.basyx.submodel.restapi.vab.VABSubmodelAPI;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProvider;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthorizedSubmodelScenario {
	private static Logger logger = LoggerFactory.getLogger(AuthorizedSubmodelScenario.class);

	public static void main(String[] args) throws URISyntaxException {
		final AuthorizedSubmodelScenario executable = new AuthorizedSubmodelScenario();

		executable.setupOutpost();
	}

	private void setupOutpost() throws URISyntaxException {
		logger.info("Starting Building1 component...");
		// Load context configuration
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration();
		contextConfig.loadFromResource("context.properties");
		BaSyxContext context = contextConfig.createBaSyxContext();

		context.setJwtBearerTokenAuthenticationConfiguration(keycloakService.createJwtBearerTokenAuthenticationConfiguration());

		org.eclipse.basyx.submodel.metamodel.map.Submodel submodel = new Submodel();

		final ISubmodelAPI smAPI = new AuthorizedSubmodelAPI<>(new VABSubmodelAPI(new VABLambdaProvider(submodel)), new GrantedAuthoritySubmodelAPIAuthorizer<>(new AuthenticationGrantedAuthorityAuthenticator()),
				new AuthenticationContextProvider());

		context.addServletMapping("/*", new VABHTTPInterface<>(new SubmodelProvider(smAPI)));

		final BaSyxHTTPServer smServer = new BaSyxHTTPServer(context);

		smServer.start();

		logger.info("Output started");

		addShutdownHook(smServer);
	}

	private final KeycloakService keycloakService = new KeycloakService(System.getenv("KEYCLOAK_SERVER_URL"), System.getenv("KEYCLOAK_REALM"));

	{
		keycloakService.setCredentials(System.getenv("KEYCLOAK_CLIENT_ID"), System.getenv("KEYCLOAK_CLIENT_SECRET"), System.getenv("KEYCLOAK_ADMIN_USERNAME"), System.getenv("KEYCLOAK_ADMIN_PASSWORD"));
	}

	private static void addShutdownHook(BaSyxHTTPServer server) {
		Thread shutdownListener = new Thread(server::shutdown);
		Runtime.getRuntime().addShutdownHook(shutdownListener);
	}
}
