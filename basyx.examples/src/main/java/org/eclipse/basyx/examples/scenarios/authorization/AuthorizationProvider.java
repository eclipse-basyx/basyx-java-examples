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
package org.eclipse.basyx.examples.scenarios.authorization;

import java.util.HashSet;
import java.util.Set;

import java.util.function.Predicate;
import org.eclipse.basyx.examples.scenarios.authorization.exception.AddClientException;
import org.eclipse.basyx.examples.scenarios.authorization.exception.RealmCreationException;
import org.eclipse.basyx.examples.scenarios.authorization.exception.RealmDeletionException;
import org.eclipse.basyx.examples.snippets.configuration.KeycloakConfiguration;
import org.eclipse.basyx.vab.protocol.http.connector.IAuthorizationSupplier;
import org.eclipse.basyx.vab.protocol.http.connector.OAuth2ClientCredentialsBasedAuthorizationSupplier;

/**
 * Class for providing OAuth based Authorization.
 *
 * @author danish, fischer
 *
 */
public class AuthorizationProvider {
	private static final String REGISTRY_AUTHORITY_READ = "urn:org.eclipse.basyx:scope:aas-registry:read";
	private static final String REGISTRY_AUTHORITY_WRITE = "urn:org.eclipse.basyx:scope:aas-registry:write";
	private static final String SERVER_AAS_API_AUTHORITY_READ = "urn:org.eclipse.basyx:scope:aas-api:read";
	private static final String SERVER_AAS_API_AUTHORITY_WRITE = "urn:org.eclipse.basyx:scope:aas-api:write";
	private static final String SERVER_AAS_AGGREGATOR_AUTHORITY_READ = "urn:org.eclipse.basyx:scope:aas-aggregator:read";
	private static final String SERVER_AAS_AGGREGATOR_AUTHORITY_WRITE = "urn:org.eclipse.basyx:scope:aas-aggregator:write";
	private static final String SERVER_SM_API_AUTHORITY_READ = "urn:org.eclipse.basyx:scope:sm-api:read";
	private static final String SERVER_SM_API_AUTHORITY_WRITE = "urn:org.eclipse.basyx:scope:sm-api:write";
	private static final String SERVER_SM_AGGREGATOR_AUTHORITY_READ = "urn:org.eclipse.basyx:scope:sm-aggregator:read";
	private static final String SERVER_SM_AGGREGATOR_AUTHORITY_WRITE = "urn:org.eclipse.basyx:scope:sm-aggregator:write";

	private static KeycloakConfiguration keycloakConfig;
	private static KeycloakServiceProvider restService;

	private static String tokenEndpointUrl;

	static {
		try {
			keycloakConfig = new KeycloakConfiguration();

			keycloakConfig.loadFromResource(KeycloakConfiguration.KEYCLOAK_CONTEXT_FILE_PATH);

			tokenEndpointUrl = keycloakConfig.getServerUrl() + "/realms/" + keycloakConfig.getRealm() + "/protocol/openid-connect/token";

			restService = new KeycloakServiceProvider(keycloakConfig);
		} catch (RealmCreationException | AddClientException | RealmDeletionException e) {
			e.printStackTrace();
		}
	}

	public static Predicate makePredicate(String issuer) {
		return (claimValue) -> {
			return claimValue != null && issuer.equals(claimValue.toString());
		};
	}

	public IAuthorizationSupplier getAuthorizationSupplier() {
		Set<String> scopes = prepareClientScopes();

		return new OAuth2ClientCredentialsBasedAuthorizationSupplier(tokenEndpointUrl, keycloakConfig.getRealm(), restService.getClientSecret(), scopes);
	}

	private Set<String> prepareClientScopes() {
		Set<String> scopes = new HashSet<>();
		scopes.add(REGISTRY_AUTHORITY_READ);
		scopes.add(REGISTRY_AUTHORITY_WRITE);
		scopes.add(SERVER_AAS_API_AUTHORITY_READ);
		scopes.add(SERVER_AAS_API_AUTHORITY_WRITE);
		scopes.add(SERVER_AAS_AGGREGATOR_AUTHORITY_READ);
		scopes.add(SERVER_AAS_AGGREGATOR_AUTHORITY_WRITE);
		scopes.add(SERVER_SM_API_AUTHORITY_READ);
		scopes.add(SERVER_SM_API_AUTHORITY_WRITE);
		scopes.add(SERVER_SM_AGGREGATOR_AUTHORITY_READ);
		scopes.add(SERVER_SM_AGGREGATOR_AUTHORITY_WRITE);

		return scopes;
	}

	public void deleteRealm() throws RealmDeletionException {
		restService.deleteRealmIfExists();
	}
}
