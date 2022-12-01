package org.eclipse.basyx.examples.scenarios.authorization.combined;

import static org.eclipse.basyx.examples.scenarios.authorization.ShutdownHookUtil.addShutdownHook;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.components.aas.AASServerComponent;
import org.eclipse.basyx.components.aas.configuration.BaSyxAASServerConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxSecurityConfiguration;
import org.eclipse.basyx.components.registry.RegistryComponent;
import org.eclipse.basyx.components.registry.configuration.BaSyxRegistryConfiguration;
import org.eclipse.basyx.examples.scenarios.authorization.shared.ExampleShell;
import org.eclipse.basyx.examples.scenarios.authorization.shared.ExampleSubmodel;
import org.eclipse.basyx.examples.scenarios.authorization.shared.SharedConfig;
import org.eclipse.basyx.extensions.aas.registration.authorization.AuthorizedAASRegistryProxy;
import org.eclipse.basyx.extensions.shared.authorization.KeycloakService;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This example uses the Off-the-Shelf AAS Server component and applies the security configuration
 * to enable authorization on the AAS Server. To be able to push data to the AAS Server, the credentials
 * of the {@link SharedConfig#SCENARIO_SETUP_USER_CREDENTIALS} Keycloak user will be used to retrieve
 * an access token from the Keycloak server first before making the request to the AAS server. This is
 * done by passing a Keycloak-specific {@link org.eclipse.basyx.vab.protocol.api.IConnectorFactory} to
 * {@link ConnectedAssetAdministrationShellManager}, injecting the token retrieval logic.
 *
 * This example also uses the Off-the-Shelf Registry Server component and applies security configuration
 * analogously. The AAS is registered at the registry using the {@link AuthorizedAASRegistryProxy} class
 * in the {@link ConnectedAssetAdministrationShellManager} to populate the Authorization header for access.
 */
public class AuthorizedAASServerAndRegistryUsingComponentsExecutable {
  private static Logger logger = LoggerFactory.getLogger(
      AuthorizedAASServerAndRegistryUsingComponentsExecutable.class);

  private final BaSyxContextConfiguration aasServerContextConfig;
  private final BaSyxSecurityConfiguration securityConfig;

  public static void main(String[] args) {
    new AuthorizedAASServerAndRegistryUsingComponentsExecutable();
  }

  public AuthorizedAASServerAndRegistryUsingComponentsExecutable() {
    aasServerContextConfig = SharedConfig.getAasServerContextConfig();
    securityConfig = SharedConfig.getSecurityConfig();

    configureKeycloak();

    startRegistry();

    createAASServer();

    logger.info("Scenario started");
  }

  private KeycloakService scenarioSetupKeycloakClient;

  private void configureKeycloak() {
    final String keycloakRealm = securityConfig.getAuthorizationStrategyJwtBearerTokenAuthenticationConfigurationProviderKeycloakRealm();
    final String keycloakServerUrl = securityConfig.getAuthorizationStrategyJwtBearerTokenAuthenticationConfigurationProviderKeycloakServerUrl();

    scenarioSetupKeycloakClient = new KeycloakService(keycloakServerUrl, keycloakRealm);

    scenarioSetupKeycloakClient.setCredentials(
        SharedConfig.KEYCLOAK_CLIENT_ID,
        SharedConfig.KEYCLOAK_CLIENT_SECRET,
        SharedConfig.SCENARIO_SETUP_USER_CREDENTIALS.getUserName(),
        SharedConfig.SCENARIO_SETUP_USER_CREDENTIALS.getPassword()
    );
  }

  private BaSyxContextConfiguration registryContextConfig;

  private void startRegistry() {
    registryContextConfig = SharedConfig.getRegistryContextConfig();

    final BaSyxRegistryConfiguration registryConfig = SharedConfig.getRegistryConfig();
    final RegistryComponent registryComponent = new RegistryComponent(registryContextConfig, registryConfig);

    addShutdownHook(registryComponent);
    registryComponent.startComponent();
  }

  private void createAASServer() {
    final BaSyxAASServerConfiguration aasConfig = SharedConfig.getAasServerConfig();

    final AASServerComponent aasServerComponent = new AASServerComponent(aasServerContextConfig, aasConfig);

    addShutdownHook(aasServerComponent);
    aasServerComponent.startComponent();

    final AssetAdministrationShell shell = new ExampleShell();

    final Submodel submodel = new ExampleSubmodel();

    pushAASAndSubmodel(shell, submodel);
  }

  private void pushAASAndSubmodel(final AssetAdministrationShell shell, final Submodel submodel) {
    // Create Manager - This manager is used to interact with an AAS server
    final String registryUrl = registryContextConfig.getUrl();

    final ConnectedAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(
        new AuthorizedAASRegistryProxy(registryUrl, scenarioSetupKeycloakClient::getTokenAsBearer),
        scenarioSetupKeycloakClient.createConnectorFactory()
    );

    // Create AAS and push it to server
    manager.createAAS(shell, aasServerContextConfig.getUrl());

    manager.createSubmodel(shell.getIdentification(), submodel);
  }
}
