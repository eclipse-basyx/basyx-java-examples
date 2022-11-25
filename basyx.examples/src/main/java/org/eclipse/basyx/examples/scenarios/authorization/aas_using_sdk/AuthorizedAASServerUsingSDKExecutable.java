package org.eclipse.basyx.examples.scenarios.authorization.aas_using_sdk;

import static org.eclipse.basyx.examples.scenarios.authorization.ShutdownHookUtil.addShutdownHook;

import java.io.File;
import java.net.URISyntaxException;
import org.eclipse.basyx.aas.aggregator.AASAggregator;
import org.eclipse.basyx.aas.aggregator.restapi.AASAggregatorProvider;
import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.CustomId;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.aas.registration.memory.InMemoryRegistry;
import org.eclipse.basyx.aas.registration.restapi.AASRegistryModelProvider;
import org.eclipse.basyx.aas.restapi.AASModelProvider;
import org.eclipse.basyx.components.IComponent;
import org.eclipse.basyx.components.aas.AASServerComponent;
import org.eclipse.basyx.components.aas.configuration.BaSyxAASServerConfiguration;
import org.eclipse.basyx.components.aas.executable.AASServerExecutable;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.examples.scenarios.authorization.KeycloakHelper;
import org.eclipse.basyx.examples.scenarios.authorization.aas_using_components.Building;
import org.eclipse.basyx.examples.scenarios.authorization.aas_using_components.BuildingSubmodel;
import org.eclipse.basyx.extensions.aas.aggregator.authorization.AuthorizedAASAggregator;
import org.eclipse.basyx.extensions.aas.aggregator.authorization.SimpleRbacAASAggregatorAuthorizer;
import org.eclipse.basyx.extensions.aas.registration.authorization.AuthorizedAASRegistryProxy;
import org.eclipse.basyx.extensions.shared.authorization.AuthenticationContextProvider;
import org.eclipse.basyx.extensions.shared.authorization.AuthenticationGrantedAuthorityAuthenticator;
import org.eclipse.basyx.extensions.shared.authorization.JWTAuthenticationContextProvider;
import org.eclipse.basyx.extensions.shared.authorization.KeycloakRoleAuthenticator;
import org.eclipse.basyx.extensions.shared.authorization.KeycloakService;
import org.eclipse.basyx.extensions.shared.authorization.PredefinedSetRbacRuleChecker;
import org.eclipse.basyx.extensions.shared.authorization.RbacRuleSet;
import org.eclipse.basyx.extensions.submodel.authorization.AuthorizedSubmodelAPI;
import org.eclipse.basyx.extensions.submodel.authorization.GrantedAuthoritySubmodelAPIAuthorizer;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.restapi.SubmodelProvider;
import org.eclipse.basyx.submodel.restapi.api.ISubmodelAPI;
import org.eclipse.basyx.submodel.restapi.vab.VABSubmodelAPI;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProvider;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;
import org.keycloak.admin.client.KeycloakBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthorizedAASServerUsingSDKExecutable {
  private static Logger logger = LoggerFactory.getLogger(
      AuthorizedAASServerUsingSDKExecutable.class);

  public static final String KEYCLOAK_SERVER_URL = "http://localhost:9005";
  public static final String KEYCLOAK_REALM = "basyx-demo";
  public static final String KEYCLOAK_CLIENT = "demo-device";
  public static final String KEYCLOAK_TOKEN_ENDPOINT = "http://127.0.0.1:9005/realms/basyx-demo/protocol/openid-connect/token";
  public static final String KEYCLOAK_CERTS_ENDPOINT = "http://127.0.0.1:9005/realms/basyx-demo/protocol/openid-connect/certs";

  private static final KeycloakBuilder keycloakBuilder = KeycloakBuilder.builder()
      .serverUrl(KEYCLOAK_SERVER_URL)
        .clientId("admin-cli")
        .realm("master")
        .username("admin")
        .password("admin")
        .grantType("password");

  private static final KeycloakHelper keycloakHelper = new KeycloakHelper(keycloakBuilder);

  public static void main(String[] args) {
    final AuthorizedAASServerUsingSDKExecutable executable = new AuthorizedAASServerUsingSDKExecutable();

    executable.configureKeycloak();

    executable.createAASServer();
  }

  private void configureKeycloak() {
    keycloakHelper.setRealmAndClient(KEYCLOAK_REALM, KEYCLOAK_CLIENT);
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
    contextConfig.loadFromResource("context.properties");

    final BaSyxAASServerConfiguration aasConfig = new BaSyxAASServerConfiguration();
    aasConfig.loadFromResource("building1/aas.properties");

    BaSyxContext basyxContext = contextConfig.createBaSyxContext();

    basyxContext.addServletMapping("/*", new VABHTTPInterface<>(new AASAggregatorProvider(
        new AuthorizedAASAggregator<>(
            new AASAggregator(),
            new SimpleRbacAASAggregatorAuthorizer<>(
                new PredefinedSetRbacRuleChecker(
                    new RbacRuleSet()
                ),
                new KeycloakRoleAuthenticator()
            ),
            new JWTAuthenticationContextProvider()
        )
    )));

    final BaSyxHTTPServer aasServer = new BaSyxHTTPServer(basyxContext);

    aasServer.start();

    addShutdownHook(aasServer);
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
}
