package org.eclipse.basyx.examples.scenarios.authorization;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.junit.Test;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.util.JsonSerialization;
public class KeyCloakProvider {
	private static final String FILE_BASE_PATH = "src/main/resources";
	private static final String REALM_FILE_NAME = "Test_realm.json";
	private static final String CLIENT_FILE_NAME = "Test_client.json";
	@Test
	public void createRealm() throws IOException {
		Keycloak keycloak = KeycloakBuilder.builder()
	            .serverUrl("http://127.0.0.1:9006/auth/")
	            .realm("master")
	            .clientId("admin-cli")
	            .username("admin")
	            .password("admin")
	            .build();
		
		RealmRepresentation rr = new RealmRepresentation();
//		createRealmFromJson(keycloak);
		createClient(keycloak);
//		rr.setId("basyx-demo");
//		rr.setRealm("basyx-demo");
//		rr.setDefaultSignatureAlgorithm("RS256");
//		rr.setRevokeRefreshToken(false);
//		rr.setEnabled(true);
//
//		keycloak.realms().create(rr);
		
		
		
		CredentialRepresentation credential = new CredentialRepresentation();
		credential.setType(CredentialRepresentation.PASSWORD);
		credential.setValue("1234");

		UserRepresentation user = new UserRepresentation();
		user.setUsername("test");
		user.setFirstName("test");
		user.setLastName("test");
		user.setEmail("test@gmail.com");
		user.setCredentials(Arrays.asList(credential));
		user.setEnabled(true);
		user.setRealmRoles(Arrays.asList("admin"));

		keycloak.realm("test-realm").users().create(user);
	}
	
	 public void createRealmFromJson(Keycloak keycloak) {
		 	System.out.println(KeyCloakProvider.class.getResourceAsStream("/Test_realm.json"));
	        RealmRepresentation rep = loadJson(KeyCloakProvider.class.getResourceAsStream("/Test_realm.json"), RealmRepresentation.class);
//	        System.out.println(rep);
	        keycloak.realms().create(rep);

	    }

	 public static <T> T loadJson(InputStream is, Class<T> type) {
	        try {
	            return JsonSerialization.readValue(is, type);
	        } catch (IOException e) {
	            throw new RuntimeException("Failed to parse json", e);
	        }
	    }
	 
	 public boolean createClient(Keycloak keycloak) throws IOException {
			RealmResource createdRealmResource = keycloak.realms().realm("basyx-demo");
			ClientRepresentation clientRepresentation = loadJson(KeyCloakProvider.class.getResourceAsStream("/Test_client.json"), ClientRepresentation.class);
//			clientRepresentation.setClientId(clientId);
//			clientRepresentation.setProtocol("openid-connect");
//			clientRepresentation.setSecret(clientId);
			createdRealmResource.clients().create(clientRepresentation);

		    return true;
		}

}
