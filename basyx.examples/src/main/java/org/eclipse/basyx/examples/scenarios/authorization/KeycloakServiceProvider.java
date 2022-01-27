package org.eclipse.basyx.examples.scenarios.authorization;

import java.io.IOException;
import java.io.InputStream;
import org.eclipse.basyx.examples.scenarios.authorization.exception.AddClientException;
import org.eclipse.basyx.examples.scenarios.authorization.exception.RealmCreationException;
import org.eclipse.basyx.examples.scenarios.authorization.exception.RealmDeletionException;
import org.junit.Test;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.util.JsonSerialization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeycloakServiceProvider {
	
private final Logger logger = LoggerFactory.getLogger(KeycloakServiceProvider.class);
	
	protected static final String REALM_NAME = "basyx-demo";
	protected static final String MASTER_REALM_NAME = "master";
	private static final String CLIENT_NAME = "basyx-demo";
	private static final String CLIENT_ID_VALUE = "admin-cli";
	private static final String USERNAME_VALUE = "admin";
	private static final String PASSWORD_VALUE = "admin";
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
	
	private Keycloak buildKeycloak() {
		keycloak = KeycloakBuilder.builder()
	            .serverUrl(BASE_ADDRESS)
	            .realm(MASTER_REALM_NAME)
	            .clientId(CLIENT_ID_VALUE)
	            .username(USERNAME_VALUE)
	            .password(PASSWORD_VALUE)
	            .build();
		
		return keycloak;
	}
	
	private void createRealm() throws RealmCreationException {        
		RealmRepresentation realmRepresentation = createRealmRepresentationFromJson();
		
		keycloak.realms().create(realmRepresentation);
	}
	
	private RealmRepresentation createRealmRepresentationFromJson() {
		return loadJson(KeyCloakProvider.class.getResourceAsStream("/" + REALM_FILE_NAME), RealmRepresentation.class);
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
		return loadJson(KeyCloakProvider.class.getResourceAsStream("/" + CLIENT_FILE_NAME), ClientRepresentation.class);
	}
	
	public static <T> T loadJson(InputStream is, Class<T> type) {
        try {
            return JsonSerialization.readValue(is, type);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse json", e);
        }
    }

	public String getClientSecret() {
		 ClientRepresentation clientRepresentation = getClientRepresentation();

		 ClientResource clientResource = getClientResource(clientRepresentation);
		 
		 System.out.println(clientResource.getSecret().getValue());
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
