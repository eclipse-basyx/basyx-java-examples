package org.eclipse.basyx.examples.scenarios.authorization;
import java.util.Arrays;

import org.junit.Test;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
public class KeyCloakProvider {
	
	@Test
	public void createRealm() {
		Keycloak keycloak = KeycloakBuilder.builder()
	            .serverUrl("http://127.0.0.1:9006/auth/")
	            .realm("master")
	            .clientId("admin-cli")
	            .username("admin")
	            .password("admin")
	            .build();
		
		RealmRepresentation rr = new RealmRepresentation();
		rr.setId("test-realm");
		rr.setRealm("test-realm");
		rr.setEnabled(true);

		keycloak.realms().create(rr);
		
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

}
