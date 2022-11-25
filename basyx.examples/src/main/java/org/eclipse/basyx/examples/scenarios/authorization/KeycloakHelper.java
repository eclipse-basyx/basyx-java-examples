package org.eclipse.basyx.examples.scenarios.authorization;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import org.eclipse.basyx.extensions.aas.aggregator.authorization.AASAggregatorScopes;
import org.eclipse.basyx.extensions.aas.api.authorization.AASAPIScopes;
import org.eclipse.basyx.extensions.aas.registration.authorization.AASRegistryScopes;
import org.eclipse.basyx.extensions.submodel.aggregator.authorization.SubmodelAggregatorScopes;
import org.eclipse.basyx.extensions.submodel.authorization.SubmodelAPIScopes;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.ClientScopeRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.RolesRepresentation;

public class KeycloakHelper {
  private Keycloak keycloak;
  private RealmResource realmResource;
  private ClientResource clientResource;

  public KeycloakHelper(KeycloakBuilder builder) {
    keycloak = builder.build();
  }

  public void setRealmAndClient(String realmName, String clientName) {
    realmResource = keycloak.realm(realmName);

    final String clientId = realmResource.clients().findAll()
        .stream().filter(client -> client.getClientId().equals(clientName))
        .findFirst().get().getId();

    clientResource = realmResource.clients().get(clientId);
  }

  public void addScope(final String scope) {
    String scopeId = realmResource.clientScopes().findAll().stream()
        .filter(clientScope -> clientScope.getName().equals(scope))
        .findFirst().get().getId();

    clientResource.addDefaultClientScope(scopeId);
    //clientResource.addDefaultClientScope(scope);
  }

  public void addScopes(final Collection<String> scopes) {
    scopes.forEach(this::addScope);
  }

  private void clearScopes() {
    clientResource.getDefaultClientScopes().clear();
  }

  public void createScope(final String name) {
    ClientScopeRepresentation clientScopeRepresentation = new ClientScopeRepresentation();

    final String scopeId = UUID.randomUUID().toString();

    clientScopeRepresentation.setName(name);
    clientScopeRepresentation.setId(scopeId);
    clientScopeRepresentation.setProtocol("openid-connect");
    clientScopeRepresentation.setAttributes(Collections.emptyMap());
    clientScopeRepresentation.setDescription("hello world");
    clientScopeRepresentation.setProtocolMappers(Collections.emptyList());

    realmResource.clientScopes().create(clientScopeRepresentation);
    realmResource.addDefaultDefaultClientScope(scopeId);
  }

  public Predicate<Object> makeInterceptor(String issuer) {
    return claimValue -> claimValue != null && issuer.equals(claimValue.toString());
  }

  public void deleteScope(final String name) {
    String scopeId = realmResource.clientScopes().findAll().stream()
        .filter(clientScope -> clientScope.getName().equals(name))
        .findFirst().map(ClientScopeRepresentation::getId).orElse(null);

    if (scopeId != null) {
      realmResource.clientScopes().get(scopeId).remove();
    }
  }

  public void unsetScope(final String name) {
    String scopeId = realmResource.clientScopes().findAll().stream()
        .filter(clientScope -> clientScope.getName().equals(name))
        .findFirst().map(ClientScopeRepresentation::getId).orElse(null);

    if (scopeId != null) {
      clientResource.removeDefaultClientScope(scopeId);
    }
  }

  public void unsetScopes(final List<String> names) {
    for (String name : names) {
      unsetScope(name);
    }
  }

  public void unsetScopes() {
    unsetScopes(allScopes);
  }

  public static final List<String> allScopes = Arrays.asList(
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

  public void createRealm(final String name) {
    final RealmRepresentation realmRepresentation = new RealmRepresentation();

    realmRepresentation.setId(name);
    realmRepresentation.setRoles(new RolesRepresentation());

    ClientRepresentation clientRepresentation;

    keycloak.realms().create(realmRepresentation);
  }
}
