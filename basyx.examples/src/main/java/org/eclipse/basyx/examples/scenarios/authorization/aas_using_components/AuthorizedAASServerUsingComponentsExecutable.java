package org.eclipse.basyx.examples.scenarios.authorization.aas_using_components;

import static org.eclipse.basyx.examples.scenarios.authorization.ShutdownHookUtil.addShutdownHook;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.components.aas.AASServerComponent;
import org.eclipse.basyx.components.aas.configuration.AASServerBackend;
import org.eclipse.basyx.components.aas.configuration.BaSyxAASServerConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.examples.scenarios.authorization.KeycloakHelper;
import org.eclipse.basyx.examples.snippets.configuration.KeycloakConfiguration;
import org.eclipse.basyx.extensions.aas.registration.authorization.AuthorizedAASRegistryProxy;
import org.eclipse.basyx.extensions.shared.authorization.KeycloakService;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.keycloak.admin.client.KeycloakBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthorizedAASServerUsingComponentsExecutable {
  private static final String AUTHORIZED_AAS_SERVER_CONTEXT_PATH = "AuthorizedAASServerContext.properties";
  private static Logger logger = LoggerFactory.getLogger(AuthorizedAASServerUsingComponentsExecutable.class);

  public static final String KEYCLOAK_SERVER_URL = "http://localhost:9006";
  public static final String KEYCLOAK_REALM = "basyx-demo";
  public static final String KEYCLOAK_TOKEN_ENDPOINT = "http://127.0.0.1:9006/realms/basyx-demo/protocol/openid-connect/token";
  public static final String KEYCLOAK_CERTS_ENDPOINT = "http://127.0.0.1:9006/realms/basyx-demo/protocol/openid-connect/certs";

  private static final KeycloakBuilder keycloakBuilder = KeycloakBuilder.builder()
      .serverUrl(KEYCLOAK_SERVER_URL)
        .clientId("admin-cli")
        .realm("master")
        .username("admin")
        .password("admin")
        .grantType("password");

  private static final KeycloakHelper keycloakHelper = new KeycloakHelper(keycloakBuilder);

  public static void main(String[] args) {
    final AuthorizedAASServerUsingComponentsExecutable executable = new AuthorizedAASServerUsingComponentsExecutable();

    executable.configureKeycloak();

    executable.createAASServer();
  }

  private void configureKeycloak() {
    keycloakHelper.setRealmAndClient("basyx-demo", "demo-device");
    keycloakHelper.addScopes(KeycloakHelper.allScopes);
  }

  private static final int REGISTRY_PORT = 8090;
  public static final String REGISTRY_URL = "http://localhost:" + REGISTRY_PORT + "/registry";

  private final KeycloakService keycloakService = new KeycloakService(KEYCLOAK_SERVER_URL, KEYCLOAK_REALM);

  {
    keycloakService.setCredentials(
        System.getenv("KEYCLOAK_CLIENT_ID"),
        System.getenv("KEYCLOAK_CLIENT_SECRET"),
        System.getenv("KEYCLOAK_ADMIN_USERNAME"),
        System.getenv("KEYCLOAK_ADMIN_PASSWORD")
    );
  }

  private void createAASServer() {
    BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration();
    contextConfig.loadFromResource("building1/aas.properties");

    final BaSyxAASServerConfiguration aasConfig = new BaSyxAASServerConfiguration();
    aasConfig.loadFromResource("building1/aas.properties");

    final AASServerComponent aasServerComponent = new AASServerComponent(contextConfig, aasConfig);

    aasServerComponent.startComponent();

    addShutdownHook(aasServerComponent);
  }

  private void pushSubmodel(final BaSyxAASServerConfiguration aasServerConfiguration, final BaSyxContextConfiguration contextConfiguration, final AssetAdministrationShell shell, final Submodel submodel) {
    // Create Manager - This manager is used to interact with an AAS server
    ConnectedAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(
        new AuthorizedAASRegistryProxy(REGISTRY_URL, keycloakService::getTokenAsBearer),
        keycloakService.createConnectorFactory()
    );

    // Create AAS and push it to server
    manager.createAAS(shell, contextConfiguration.getUrl());

    manager.createSubmodel(shell.getIdentification(), submodel);
  }

  private BaSyxAASServerConfiguration configureAuthorizedBasyxAASServer() {
    BaSyxAASServerConfiguration aasServerConfig = new BaSyxAASServerConfiguration();
    aasServerConfig.setAASBackend(AASServerBackend.INMEMORY);

    aasServerConfig.loadFromResource(AUTHORIZED_AAS_SERVER_CONTEXT_PATH);

    KeycloakConfiguration keycloakConfig = new KeycloakConfiguration();

    keycloakConfig.loadFromResource(KeycloakConfiguration.KEYCLOAK_CONTEXT_FILE_PATH);

    aasServerConfig.setAuthorizationStrategyJwtBearerTokenAuthenticationConfigurationProviderKeycloakRealm(keycloakConfig.getRealm());
    aasServerConfig.setAuthorizationStrategyJwtBearerTokenAuthenticationConfigurationProviderKeycloakServerUrl(keycloakConfig.getServerUrl());

    return aasServerConfig;
  }
}
