/*******************************************************************************
 * Copyright (C) 2022 the Eclipse BaSyx Authors
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.scenarios.authorization;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.basyx.examples.scenarios.authorization.exception.AddClientException;
import org.eclipse.basyx.examples.scenarios.authorization.exception.RealmCreationException;
import org.eclipse.basyx.examples.scenarios.authorization.exception.RealmDeletionException;
import org.eclipse.basyx.vab.protocol.http.connector.IAuthorizationSupplier;
import org.eclipse.basyx.vab.protocol.http.connector.OAuth2ClientCredentialsBasedAuthorizationSupplier;

/**
 * Class for providing OAuth based Authorization.
 *
 * @author danish, fischer
 *
 */
public class AuthorizationProvider {
	private static final String TOKEN_ENDPOINT_URL = KeycloakServiceProvider.SERVER_ADDRESS + "/auth/realms/" + KeycloakServiceProvider.REALM_NAME + "/protocol/openid-connect/token";
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

	private static KeycloakServiceProvider restService;

	static {
		try {
			restService = new KeycloakServiceProvider();
		} catch (RealmCreationException | AddClientException e) {
			e.printStackTrace();
		}
	}

	public IAuthorizationSupplier getAuthorizationSupplier() {
		Set<String> scopes = prepareClientScopes();

		return new OAuth2ClientCredentialsBasedAuthorizationSupplier(TOKEN_ENDPOINT_URL, KeycloakServiceProvider.REALM_NAME, restService.getClientSecret(), scopes);
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
		restService.deleteRealm();
	}
}
