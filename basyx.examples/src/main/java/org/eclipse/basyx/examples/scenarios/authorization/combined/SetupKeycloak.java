package org.eclipse.basyx.examples.scenarios.authorization.combined;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ws.rs.NotFoundException;
import org.eclipse.basyx.components.configuration.BaSyxSecurityConfiguration;
import org.eclipse.basyx.examples.scenarios.authorization.shared.SharedConfig;
import org.eclipse.basyx.extensions.aas.aggregator.authorization.AASAggregatorScopes;
import org.eclipse.basyx.extensions.aas.api.authorization.AASAPIScopes;
import org.eclipse.basyx.extensions.aas.registration.authorization.AASRegistryScopes;
import org.eclipse.basyx.extensions.submodel.aggregator.authorization.SubmodelAggregatorScopes;
import org.eclipse.basyx.extensions.submodel.authorization.SubmodelAPIScopes;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.ClientScopeResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.ClientScopeRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.RolesRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

public class SetupKeycloak {
  public static final String ROLE_ADMIN = "admin";
  public static final String ROLE_READER = "reader";

  public static void main(String[] args) {
    new SetupKeycloak();
  }

  private Keycloak keycloak;
  private final RealmResource realm;

  public SetupKeycloak() {
    final BaSyxSecurityConfiguration securityConfig = SharedConfig.getSecurityConfig();

    final String serverUrl = securityConfig.getAuthorizationStrategyJwtBearerTokenAuthenticationConfigurationProviderKeycloakServerUrl();

    final KeycloakBuilder keycloakBuilder = KeycloakBuilder.builder()
        .serverUrl(serverUrl)
        .realm("master")
        .clientId("admin-cli")
        .username(SharedConfig.KEYCLOAK_ADMIN_USER_CREDENTIALS.getUserName())
        .password(SharedConfig.KEYCLOAK_ADMIN_USER_CREDENTIALS.getPassword())
        .grantType(OAuth2Constants.PASSWORD);

    keycloak = keycloakBuilder.build();

    realm = createRealm();

    createClient();
    createRoles();
    createUsers();

    addScopes();
  }

  private RealmResource createRealm() {
    final RealmRepresentation realmRepresentation = new RealmRepresentation();

    final BaSyxSecurityConfiguration securityConfig = SharedConfig.getSecurityConfig();

    final String name = securityConfig.getAuthorizationStrategyJwtBearerTokenAuthenticationConfigurationProviderKeycloakRealm();

    realmRepresentation.setId(name);
    realmRepresentation.setEnabled(true);
    realmRepresentation.setRealm(name);
    realmRepresentation.setRoles(new RolesRepresentation());

    final Optional<RealmResource> realm = findRealmByName(name);

    return realm.orElseGet(() -> {
      keycloak.realms().create(realmRepresentation);
      return keycloak.realm(name);
    });
  }

  private void createClient() {
    final String clientId = SharedConfig.KEYCLOAK_CLIENT_ID;

    final ClientRepresentation clientRepresentation = new ClientRepresentation();

    //clientRepresentation.setId(clientId);
    clientRepresentation.setClientId(clientId);
    //clientRepresentation.setName(clientId);
    clientRepresentation.setEnabled(true);

    clientRepresentation.setClientAuthenticatorType("client-secret");
    clientRepresentation.setProtocol("openid-connect");
    clientRepresentation.setSecret(SharedConfig.KEYCLOAK_CLIENT_SECRET);

    realm.clients().create(clientRepresentation);

    final ClientRepresentation newClientRepresentation = realm.clients().findByClientId(clientId).get(0);

    final String id = newClientRepresentation.getId();

    final ClientResource newClientResource = realm.clients().get(id);

    newClientRepresentation.setPublicClient(false);
    newClientRepresentation.setSecret(SharedConfig.KEYCLOAK_CLIENT_SECRET);

    newClientResource.update(newClientRepresentation);
  }

  private Optional<RealmResource> findRealmByName(final String name) {
    try {
      final RealmResource realm = keycloak.realm(name);
      // check if this throws a NotFoundException
      realm.toRepresentation();
      return Optional.of(realm);
    } catch (final NotFoundException e) {
      return Optional.empty();
    }
  }

  private void createRoles() {
    createRole(ROLE_ADMIN);
    createRole(ROLE_READER);
  }

  private void createRole(final String name) {
    final RoleRepresentation newRoleRepresentation = findRoleByName(name).orElseGet(RoleRepresentation::new);

    newRoleRepresentation.setId(name);
    newRoleRepresentation.setName(name);

    createOrUpdateRole(newRoleRepresentation);
  }

  private void createOrUpdateRole(final RoleRepresentation roleRepresentation) {
    final Optional<RoleRepresentation> oldRoleRepresentation = findRoleByName(roleRepresentation.getName());

    if (oldRoleRepresentation.isPresent()) {
      final RoleResource roleResource = realm.roles().get(oldRoleRepresentation.get().getName());

      roleResource.update(roleRepresentation);
    } else {
      realm.roles().create(roleRepresentation);
    }
  }

  private Optional<RoleRepresentation> findRoleByName(final String name) {
    return realm.roles().list().stream().filter(r -> name.equals(r.getName())).findFirst();
  }

  private void createUsers() {
    createUser(
        SharedConfig.AUTHORIZED_READER_USER_CREDENTIALS.getUserName(),
        SharedConfig.AUTHORIZED_READER_USER_CREDENTIALS.getPassword(),
        Collections.singletonList(ROLE_READER)
    );
    createUser(
        SharedConfig.SCENARIO_SETUP_USER_CREDENTIALS.getUserName(),
        SharedConfig.SCENARIO_SETUP_USER_CREDENTIALS.getPassword(),
        Collections.singletonList(ROLE_ADMIN)
    );
    createUser(
        SharedConfig.UNAUTHORIZED_USER_CREDENTIALS.getUserName(),
        SharedConfig.UNAUTHORIZED_USER_CREDENTIALS.getPassword(),
        Collections.emptyList()
    );
  }

  public void createUser(final String userName, final String password, final List<String> roleNames) {
    final UserRepresentation userRepresentation = new UserRepresentation();
    final CredentialRepresentation pwCredential = new CredentialRepresentation();

    pwCredential.setTemporary(false);
    pwCredential.setType(CredentialRepresentation.PASSWORD);
    pwCredential.setValue(password);
    userRepresentation.setUsername(userName);
    userRepresentation.setCredentials(Collections.singletonList(
        pwCredential
    ));
    userRepresentation.setEnabled(true);

    realm.users().create(userRepresentation);

    final Optional<UserResource> userResource = findUserByName(userName);

    userResource.ifPresent(user -> {
      final List<RoleRepresentation> roles = roleNames.stream()
          .map(roleName -> realm.roles().get(roleName).toRepresentation())
          .collect(Collectors.toList());

      user.roles().realmLevel().add(roles);
    });
  }

  private Optional<UserResource> findUserByName(final String name) {
    final Optional<UserRepresentation> representation = realm.users().list().stream().filter(u -> name.equals(u.getUsername())).findFirst();
    return representation.map(repr -> realm.users().get(repr.getId()));
  }

  public static final List<String> ALL_SCOPES = Arrays.asList(
      AASAggregatorScopes.READ_SCOPE,
      AASAggregatorScopes.WRITE_SCOPE,
      AASAPIScopes.READ_SCOPE,
      AASAPIScopes.WRITE_SCOPE,
      SubmodelAggregatorScopes.READ_SCOPE,
      SubmodelAggregatorScopes.WRITE_SCOPE,
      SubmodelAPIScopes.READ_SCOPE,
      SubmodelAPIScopes.WRITE_SCOPE,
      AASRegistryScopes.READ_SCOPE,
      AASRegistryScopes.WRITE_SCOPE
  );

  private void addScopes() {
    ALL_SCOPES.forEach(this::addScope);
  }

  private void addScope(final String name) {
    final ClientScopeRepresentation clientScopeRepresentation = new ClientScopeRepresentation();

    clientScopeRepresentation.setDescription("");
    clientScopeRepresentation.setName(name);
    clientScopeRepresentation.setAttributes(new HashMap<>());
    clientScopeRepresentation.setProtocolMappers(new ArrayList<>());
    clientScopeRepresentation.setProtocol("openid-connect");

    final ClientScopeRepresentation effectiveClientScopeResource = createOrUpdateClientScope(clientScopeRepresentation);

    final String id = effectiveClientScopeResource.getId();

    if (realm.getDefaultDefaultClientScopes().stream().noneMatch(scope -> id.equals(scope.getId()))) {
      realm.addDefaultDefaultClientScope(id);
    }
  }

  private ClientScopeRepresentation createOrUpdateClientScope(final ClientScopeRepresentation clientScopeRepresentation) {
    final Optional<ClientScopeRepresentation> oldClientScopeRepresentation = findClientScopeByName(clientScopeRepresentation.getName());

    if (oldClientScopeRepresentation.isPresent()) {
      final ClientScopeResource clientScopeResource = realm.clientScopes().get(oldClientScopeRepresentation.get().getId());

      clientScopeResource.update(clientScopeRepresentation);
    } else {
      realm.clientScopes().create(clientScopeRepresentation);
    }

    return findClientScopeByName(clientScopeRepresentation.getName()).orElseThrow(AssertionError::new);
  }

  private Optional<ClientScopeRepresentation> findClientScopeByName(final String name) {
    return realm.clientScopes().findAll().stream().filter(s -> name.equals(s.getName())).findFirst();
  }
}
