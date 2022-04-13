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

import java.io.IOException;
import java.io.InputStream;
import org.eclipse.basyx.examples.scenarios.authorization.exception.AddClientException;
import org.eclipse.basyx.examples.scenarios.authorization.exception.RealmCreationException;
import org.eclipse.basyx.examples.scenarios.authorization.exception.RealmDeletionException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.util.JsonSerialization;

/**
 * This class provides Keycloak services such as creating Realm, adding clients
 * to realm, getting client secrets.
 * 
 * @author danish
 *
 */
public class KeycloakServiceProvider {

	protected static final String REALM_NAME = "basyx-demo";
	protected static final String MASTER_REALM_NAME = "master";
	private static final String CLIENT_NAME = "basyx-demo";
	private static final String MASTER_CLIENT_ID = "admin-cli";
	private static final String USERNAME = "admin";
	private static final String PASSWORD = "admin";
	private static final String REALM_FILE_NAME = "Test_realm.json";
	private static final String CLIENT_FILE_NAME = "Test_client.json";
	protected static final String SERVER_ADDRESS = "http://127.0.0.1:9006";
	protected static final String BASE_ADDRESS = SERVER_ADDRESS + "/auth/";
	private Keycloak keycloak;

	public KeycloakServiceProvider() throws RealmCreationException, AddClientException {
		buildKeycloak();

		createRealm();

		addClientToRealm();
	}

	private void buildKeycloak() {
		keycloak = KeycloakBuilder.builder().serverUrl(BASE_ADDRESS).realm(MASTER_REALM_NAME).clientId(MASTER_CLIENT_ID).username(USERNAME).password(PASSWORD).build();
	}

	private void createRealm() throws RealmCreationException {
		RealmRepresentation realmRepresentation = createRealmRepresentationFromJson();

		keycloak.realms().create(realmRepresentation);
	}

	private RealmRepresentation createRealmRepresentationFromJson() {
		return loadJson(KeycloakServiceProvider.class.getResourceAsStream("/" + REALM_FILE_NAME), RealmRepresentation.class);
	}

	private void addClientToRealm() throws AddClientException {
		RealmResource realmResource = getRealmResource();

		ClientRepresentation clientRepresentation = createClientRepresentationFromJson();

		realmResource.clients().create(clientRepresentation);
	}

	private RealmResource getRealmResource() {
		return keycloak.realms().realm(REALM_NAME);
	}

	private ClientRepresentation createClientRepresentationFromJson() {
		return loadJson(KeycloakServiceProvider.class.getResourceAsStream("/" + CLIENT_FILE_NAME), ClientRepresentation.class);
	}

	private static <T> T loadJson(InputStream is, Class<T> type) {
		try {
			return JsonSerialization.readValue(is, type);
		} catch (IOException e) {
			throw new RuntimeException("Failed to parse json", e);
		}
	}

	public String getClientSecret() {
		ClientRepresentation clientRepresentation = getClientRepresentation();

		ClientResource clientResource = getClientResource(clientRepresentation);

		return clientResource.getSecret().getValue();
	}

	private ClientRepresentation getClientRepresentation() {
		return keycloak.realm(REALM_NAME).clients().findByClientId(CLIENT_NAME).get(0);
	}

	private ClientResource getClientResource(ClientRepresentation clientRepresentation) {
		return keycloak.realm(REALM_NAME).clients().get(clientRepresentation.getId());
	}

	public void deleteRealm() throws RealmDeletionException {
		keycloak.realm(REALM_NAME).remove();
	}

}
