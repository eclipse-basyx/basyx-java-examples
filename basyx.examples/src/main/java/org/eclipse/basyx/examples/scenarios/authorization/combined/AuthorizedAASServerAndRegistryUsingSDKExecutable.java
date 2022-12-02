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
package org.eclipse.basyx.examples.scenarios.authorization.combined;

import org.eclipse.basyx.aas.aggregator.AASAggregator;
import org.eclipse.basyx.aas.aggregator.restapi.AASAggregatorProvider;
import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.registration.memory.InMemoryRegistry;
import org.eclipse.basyx.aas.registration.restapi.AASRegistryModelProvider;
import org.eclipse.basyx.aas.restapi.AASAPIFactory;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxSecurityConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxSecurityConfiguration.AuthorizationStrategy;
import org.eclipse.basyx.examples.scenarios.authorization.shared.ExampleShell;
import org.eclipse.basyx.examples.scenarios.authorization.shared.ExampleSubmodel;
import org.eclipse.basyx.examples.scenarios.authorization.shared.SharedConfig;
import org.eclipse.basyx.extensions.aas.aggregator.authorization.internal.AuthorizedAASAggregator;
import org.eclipse.basyx.extensions.aas.aggregator.authorization.internal.GrantedAuthorityAASAggregatorAuthorizer;
import org.eclipse.basyx.extensions.aas.aggregator.authorization.internal.SimpleRbacAASAggregatorAuthorizer;
import org.eclipse.basyx.extensions.aas.api.authorization.internal.AuthorizedDecoratingAASAPIFactory;
import org.eclipse.basyx.extensions.aas.api.authorization.internal.GrantedAuthorityAASAPIAuthorizer;
import org.eclipse.basyx.extensions.aas.api.authorization.internal.SimpleRbacAASAPIAuthorizer;
import org.eclipse.basyx.extensions.aas.registration.authorization.internal.AuthorizedAASRegistry;
import org.eclipse.basyx.extensions.aas.registration.authorization.internal.AuthorizedAASRegistryProxy;
import org.eclipse.basyx.extensions.aas.registration.authorization.internal.GrantedAuthorityAASRegistryAuthorizer;
import org.eclipse.basyx.extensions.aas.registration.authorization.internal.SimpleRbacAASRegistryAuthorizer;
import org.eclipse.basyx.extensions.shared.authorization.internal.AuthenticationContextProvider;
import org.eclipse.basyx.extensions.shared.authorization.internal.AuthenticationGrantedAuthorityAuthenticator;
import org.eclipse.basyx.extensions.shared.authorization.internal.IGrantedAuthorityAuthenticator;
import org.eclipse.basyx.extensions.shared.authorization.internal.IRbacRuleChecker;
import org.eclipse.basyx.extensions.shared.authorization.internal.IRoleAuthenticator;
import org.eclipse.basyx.extensions.shared.authorization.internal.ISubjectInformationProvider;
import org.eclipse.basyx.extensions.shared.authorization.internal.JWTAuthenticationContextProvider;
import org.eclipse.basyx.extensions.shared.authorization.internal.KeycloakRoleAuthenticator;
import org.eclipse.basyx.extensions.shared.authorization.internal.KeycloakService;
import org.eclipse.basyx.extensions.shared.authorization.internal.PredefinedSetRbacRuleChecker;
import org.eclipse.basyx.extensions.shared.authorization.internal.RbacRuleSet;
import org.eclipse.basyx.extensions.submodel.aggregator.authorization.internal.AuthorizedDecoratingSubmodelAggregatorFactory;
import org.eclipse.basyx.extensions.submodel.aggregator.authorization.internal.GrantedAuthoritySubmodelAggregatorAuthorizer;
import org.eclipse.basyx.extensions.submodel.aggregator.authorization.internal.SimpleRbacSubmodelAggregatorAuthorizer;
import org.eclipse.basyx.extensions.submodel.authorization.internal.AuthorizedDecoratingSubmodelAPIFactory;
import org.eclipse.basyx.extensions.submodel.authorization.internal.GrantedAuthoritySubmodelAPIAuthorizer;
import org.eclipse.basyx.extensions.submodel.authorization.internal.SimpleRbacSubmodelAPIAuthorizer;
import org.eclipse.basyx.submodel.aggregator.SubmodelAggregatorFactory;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.restapi.vab.VABSubmodelAPIFactory;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * This example constructs an SDK-based AAS Server and applies the security configuration to enable authorization on the AAS Server. To be able to push data to the AAS Server, the credentials of the {@link
 * SharedConfig#SCENARIO_SETUP_USER_CREDENTIALS} Keycloak user will be used to retrieve an access token from the Keycloak server first before making the request to the AAS server. This is done by passing a Keycloak-specific {@link
 * org.eclipse.basyx.vab.protocol.api.IConnectorFactory} to {@link ConnectedAssetAdministrationShellManager}, injecting the token retrieval logic.
 * <p>
 * This example also constructs an SDK-based Registry Server and applies security configuration analogously. The AAS is registered at the registry using the {@link AuthorizedAASRegistryProxy} class in the {@link
 * ConnectedAssetAdministrationShellManager} to populate the Authorization header for access.
 */
public class AuthorizedAASServerAndRegistryUsingSDKExecutable {
	private static Logger logger = LoggerFactory.getLogger(AuthorizedAASServerAndRegistryUsingSDKExecutable.class);

	private final BaSyxContextConfiguration aasServerContextConfig;
	private final BaSyxSecurityConfiguration securityConfig;

	public static void main(String[] args) {
		new AuthorizedAASServerAndRegistryUsingSDKExecutable();
	}

	public AuthorizedAASServerAndRegistryUsingSDKExecutable() {
		aasServerContextConfig = SharedConfig.getAasServerContextConfig();
		securityConfig = SharedConfig.getSecurityConfig();

		configureKeycloak();

		startRegistry();

		createAASServer();

		logger.info("Scenario started");
	}

	private KeycloakService scenarioSetupKeycloakClient;

	private void configureKeycloak() {
		final String keycloakRealm = securityConfig.getAuthorizationStrategyJwtBearerTokenAuthenticationConfigurationProviderKeycloakRealm();
		final String keycloakServerUrl = securityConfig.getAuthorizationStrategyJwtBearerTokenAuthenticationConfigurationProviderKeycloakServerUrl();

		scenarioSetupKeycloakClient = new KeycloakService(keycloakServerUrl, keycloakRealm);

		final String clientId;
		final String clientSecret;
		final AuthorizationStrategy strategy = AuthorizationStrategy.valueOf(securityConfig.getAuthorizationStrategy());
		switch (strategy) {
		case SimpleRbac: {
			clientId = SharedConfig.KEYCLOAK_CLIENT_ID;
			clientSecret = SharedConfig.KEYCLOAK_CLIENT_SECRET;
			break;
		}
		case GrantedAuthority:
			clientId = SharedConfig.KEYCLOAK_SCOPED_CLIENT_ID;
			clientSecret = SharedConfig.KEYCLOAK_SCOPED_CLIENT_SECRET;
			break;
		default:
			throw new UnsupportedOperationException("no handler");
		}

		scenarioSetupKeycloakClient.setCredentials(clientId, clientSecret, SharedConfig.SCENARIO_SETUP_USER_CREDENTIALS.getUserName(), SharedConfig.SCENARIO_SETUP_USER_CREDENTIALS.getPassword());
	}

	private BaSyxContextConfiguration registryContextConfig;

	private <T> void startRegistry() {
		registryContextConfig = SharedConfig.getRegistryContextConfig();
		final BaSyxContext basyxContext = registryContextConfig.createBaSyxContext();

		final org.eclipse.basyx.components.registry.authorization.Authorizers<T> authorizers = (org.eclipse.basyx.components.registry.authorization.Authorizers<T>) createRegistryAuthorizers();
		final ISubjectInformationProvider<T> subjectInformationProvider = (ISubjectInformationProvider<T>) createSubjectInformationProvider();

		basyxContext.addServletMapping("/*", new VABHTTPInterface<>(new AASRegistryModelProvider(new AuthorizedAASRegistry<>(new InMemoryRegistry(), authorizers.getAasRegistryAuthorizer(), subjectInformationProvider))));
		basyxContext.setJwtBearerTokenAuthenticationConfiguration(scenarioSetupKeycloakClient.createJwtBearerTokenAuthenticationConfiguration());

		BaSyxHTTPServer registryServer = new BaSyxHTTPServer(basyxContext);

		registryServer.start();
	}

	private org.eclipse.basyx.components.registry.authorization.Authorizers<?> createRegistryAuthorizers() {
		final AuthorizationStrategy strategy = AuthorizationStrategy.valueOf(securityConfig.getAuthorizationStrategy());
		switch (strategy) {
		case SimpleRbac: {
			return createRegistrySimpleRbacAuthorizers();
		}
		case GrantedAuthority:
			return createRegistryGrantedAuthorityAuthorizers();
		default:
			throw new UnsupportedOperationException("no handler");
		}
	}

	private org.eclipse.basyx.components.registry.authorization.Authorizers<Jwt> createRegistrySimpleRbacAuthorizers() {
		final RbacRuleSet rbacRuleSet = new org.eclipse.basyx.components.aas.authorization.SimpleRbacSecurityFeature(securityConfig).getRbacRuleSet();

		final IRbacRuleChecker rbacRuleChecker = new PredefinedSetRbacRuleChecker(rbacRuleSet);
		final IRoleAuthenticator<Jwt> roleAuthenticator = new KeycloakRoleAuthenticator();

		return new org.eclipse.basyx.components.registry.authorization.Authorizers<>(new SimpleRbacAASRegistryAuthorizer<>(rbacRuleChecker, roleAuthenticator), null);
	}

	private org.eclipse.basyx.components.registry.authorization.Authorizers<Authentication> createRegistryGrantedAuthorityAuthorizers() {
		final IGrantedAuthorityAuthenticator<Authentication> authenticator = new AuthenticationGrantedAuthorityAuthenticator();
		return new org.eclipse.basyx.components.registry.authorization.Authorizers<>(new GrantedAuthorityAASRegistryAuthorizer<>(authenticator), null);
	}

	private <T> void createAASServer() {
		final BaSyxContext basyxContext = aasServerContextConfig.createBaSyxContext();

		final org.eclipse.basyx.components.aas.authorization.Authorizers<T> authorizers = (org.eclipse.basyx.components.aas.authorization.Authorizers<T>) createAASAuthorizers();
		final ISubjectInformationProvider<T> subjectInformationProvider = (ISubjectInformationProvider<T>) createSubjectInformationProvider();

		basyxContext.addServletMapping("/*", new VABHTTPInterface<>(new AASAggregatorProvider(new AuthorizedAASAggregator<>(
				new AASAggregator(new AuthorizedDecoratingAASAPIFactory<>(new AASAPIFactory(), authorizers.getAasApiAuthorizer(), subjectInformationProvider), new AuthorizedDecoratingSubmodelAggregatorFactory<>(null,
						new SubmodelAggregatorFactory(new AuthorizedDecoratingSubmodelAPIFactory<>(null, new VABSubmodelAPIFactory(), authorizers.getSubmodelAPIAuthorizer(), subjectInformationProvider)),
						authorizers.getSubmodelAggregatorAuthorizer(), subjectInformationProvider)), authorizers.getAasAggregatorAuthorizer(), subjectInformationProvider))));
		basyxContext.setJwtBearerTokenAuthenticationConfiguration(scenarioSetupKeycloakClient.createJwtBearerTokenAuthenticationConfiguration());

		final BaSyxHTTPServer aasServer = new BaSyxHTTPServer(basyxContext);

		aasServer.start();

		final AssetAdministrationShell shell = new ExampleShell();

		final Submodel submodel = new ExampleSubmodel();

		pushAASAndSubmodel(shell, submodel);
	}

	private org.eclipse.basyx.components.aas.authorization.Authorizers<?> createAASAuthorizers() {
		final AuthorizationStrategy strategy = AuthorizationStrategy.valueOf(securityConfig.getAuthorizationStrategy());
		switch (strategy) {
		case SimpleRbac: {
			return createAASServerSimpleRbacAuthorizers();
		}
		case GrantedAuthority:
			return createAASServerGrantedAuthorityAuthorizers();
		default:
			throw new UnsupportedOperationException("no handler");
		}
	}

	private org.eclipse.basyx.components.aas.authorization.Authorizers<Jwt> createAASServerSimpleRbacAuthorizers() {
		final RbacRuleSet rbacRuleSet = new org.eclipse.basyx.components.aas.authorization.SimpleRbacSecurityFeature(securityConfig).getRbacRuleSet();

		final IRbacRuleChecker rbacRuleChecker = new PredefinedSetRbacRuleChecker(rbacRuleSet);
		final IRoleAuthenticator<Jwt> roleAuthenticator = new KeycloakRoleAuthenticator();

		return new org.eclipse.basyx.components.aas.authorization.Authorizers<>(new SimpleRbacAASAggregatorAuthorizer<>(rbacRuleChecker, roleAuthenticator), new SimpleRbacAASAPIAuthorizer<>(rbacRuleChecker, roleAuthenticator),
				new SimpleRbacSubmodelAggregatorAuthorizer<>(rbacRuleChecker, roleAuthenticator), new SimpleRbacSubmodelAPIAuthorizer<>(rbacRuleChecker, roleAuthenticator), null);
	}

	private org.eclipse.basyx.components.aas.authorization.Authorizers<Authentication> createAASServerGrantedAuthorityAuthorizers() {
		final IGrantedAuthorityAuthenticator<Authentication> authenticator = new AuthenticationGrantedAuthorityAuthenticator();
		return new org.eclipse.basyx.components.aas.authorization.Authorizers<>(new GrantedAuthorityAASAggregatorAuthorizer<>(authenticator), new GrantedAuthorityAASAPIAuthorizer<>(authenticator),
				new GrantedAuthoritySubmodelAggregatorAuthorizer<>(authenticator), new GrantedAuthoritySubmodelAPIAuthorizer<>(authenticator), null);
	}

	private ISubjectInformationProvider<?> createSubjectInformationProvider() {
		final AuthorizationStrategy strategy = AuthorizationStrategy.valueOf(securityConfig.getAuthorizationStrategy());
		switch (strategy) {
		case SimpleRbac: {
			return new JWTAuthenticationContextProvider();
		}
		case GrantedAuthority:
			return new AuthenticationContextProvider();
		default:
			throw new UnsupportedOperationException("no handler");
		}
	}

	private void pushAASAndSubmodel(final AssetAdministrationShell shell, final Submodel submodel) {
		// Create Manager - This manager is used to interact with an AAS server
		final String registryUrl = registryContextConfig.getUrl();

		final ConnectedAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(new AuthorizedAASRegistryProxy(registryUrl, scenarioSetupKeycloakClient::getTokenAsBearer),
				scenarioSetupKeycloakClient.createConnectorFactory());

		// Create AAS and push it to server
		manager.createAAS(shell, aasServerContextConfig.getUrl());

		manager.createSubmodel(shell.getIdentification(), submodel);
	}
}
