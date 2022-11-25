package org.eclipse.basyx.examples.scenarios.authorization.registry_using_sdk;

import static org.eclipse.basyx.examples.scenarios.authorization.ShutdownHookUtil.addShutdownHook;

import java.net.URISyntaxException;
import org.eclipse.basyx.aas.registration.api.IAASRegistry;
import org.eclipse.basyx.aas.registration.memory.AASRegistry;
import org.eclipse.basyx.aas.registration.memory.InMemoryRegistry;
import org.eclipse.basyx.aas.registration.restapi.AASRegistryModelProvider;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.examples.scenarios.authorization.submodel.Submodel;
import org.eclipse.basyx.extensions.aas.registration.authorization.AuthorizedAASRegistry;
import org.eclipse.basyx.extensions.aas.registration.authorization.GrantedAuthorityAASRegistryAuthorizer;
import org.eclipse.basyx.extensions.shared.authorization.AuthenticationContextProvider;
import org.eclipse.basyx.extensions.shared.authorization.AuthenticationGrantedAuthorityAuthenticator;
import org.eclipse.basyx.extensions.shared.authorization.KeycloakService;
import org.eclipse.basyx.extensions.submodel.authorization.AuthorizedSubmodelAPI;
import org.eclipse.basyx.extensions.submodel.authorization.GrantedAuthoritySubmodelAPIAuthorizer;
import org.eclipse.basyx.submodel.restapi.SubmodelProvider;
import org.eclipse.basyx.submodel.restapi.api.ISubmodelAPI;
import org.eclipse.basyx.submodel.restapi.vab.VABSubmodelAPI;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProvider;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;
import org.eclipse.basyx.vab.registry.restapi.VABRegistryModelProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthorizedRegistryUsingSDKScenario {
  private static Logger logger = LoggerFactory.getLogger(AuthorizedRegistryUsingSDKScenario.class);

  public static void main(String[] args) throws URISyntaxException {
    final AuthorizedRegistryUsingSDKScenario executable = new AuthorizedRegistryUsingSDKScenario();

    executable.createRegistry();
  }

  private void createRegistry() {
    logger.info("Starting Building1 component...");
    // Load context configuration
    BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration();
    contextConfig.loadFromResource("registry.properties");
    BaSyxContext context = contextConfig.createBaSyxContext();

    context.setJwtBearerTokenAuthenticationConfiguration(keycloakService.createJwtBearerTokenAuthenticationConfiguration());

    final IAASRegistry aasRegistry = new InMemoryRegistry();

    final IAASRegistry authorizedAASRegistry = new AuthorizedAASRegistry<>(aasRegistry,
        new GrantedAuthorityAASRegistryAuthorizer<>(
            new AuthenticationGrantedAuthorityAuthenticator()), new AuthenticationContextProvider());

    context.addServletMapping("/*", new VABHTTPInterface<>(new AASRegistryModelProvider(authorizedAASRegistry)));

    final BaSyxHTTPServer smServer = new BaSyxHTTPServer(context);

    smServer.start();

    logger.info("Registry started");

    addShutdownHook(smServer);
  }

  private final KeycloakService keycloakService = new KeycloakService(System.getenv("KEYCLOAK_SERVER_URL"), System.getenv("KEYCLOAK_REALM"));

  {
    keycloakService.setCredentials(
        System.getenv("KEYCLOAK_CLIENT_ID"),
        System.getenv("KEYCLOAK_CLIENT_SECRET"),
        System.getenv("KEYCLOAK_ADMIN_USERNAME"),
        System.getenv("KEYCLOAK_ADMIN_PASSWORD")
    );
  }
}
