package org.eclipse.basyx.examples.scenarios.authorization.combined;

import org.eclipse.basyx.aas.aggregator.AASAggregator;
import org.eclipse.basyx.aas.aggregator.restapi.AASAggregatorProvider;
import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.registration.memory.InMemoryRegistry;
import org.eclipse.basyx.aas.registration.restapi.AASRegistryModelProvider;
import org.eclipse.basyx.aas.restapi.AASAPIFactory;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxSecurityConfiguration;
import org.eclipse.basyx.examples.scenarios.authorization.shared.ExampleShell;
import org.eclipse.basyx.examples.scenarios.authorization.shared.ExampleSubmodel;
import org.eclipse.basyx.examples.scenarios.authorization.shared.SharedConfig;
import org.eclipse.basyx.extensions.aas.aggregator.authorization.AuthorizedAASAggregator;
import org.eclipse.basyx.extensions.aas.aggregator.authorization.SimpleRbacAASAggregatorAuthorizer;
import org.eclipse.basyx.extensions.aas.api.authorization.AuthorizedAASAPI;
import org.eclipse.basyx.extensions.aas.api.authorization.AuthorizedDecoratingAASAPIFactory;
import org.eclipse.basyx.extensions.aas.api.authorization.SimpleRbacAASAPIAuthorizer;
import org.eclipse.basyx.extensions.aas.registration.authorization.AuthorizedAASRegistry;
import org.eclipse.basyx.extensions.aas.registration.authorization.AuthorizedAASRegistryProxy;
import org.eclipse.basyx.extensions.aas.registration.authorization.SimpleRbacAASRegistryAuthorizer;
import org.eclipse.basyx.extensions.shared.authorization.IRbacRuleChecker;
import org.eclipse.basyx.extensions.shared.authorization.IRoleAuthenticator;
import org.eclipse.basyx.extensions.shared.authorization.ISubjectInformationProvider;
import org.eclipse.basyx.extensions.shared.authorization.JWTAuthenticationContextProvider;
import org.eclipse.basyx.extensions.shared.authorization.KeycloakRoleAuthenticator;
import org.eclipse.basyx.extensions.shared.authorization.KeycloakService;
import org.eclipse.basyx.extensions.shared.authorization.PredefinedSetRbacRuleChecker;
import org.eclipse.basyx.extensions.shared.authorization.RbacRuleSet;
import org.eclipse.basyx.extensions.submodel.aggregator.authorization.AuthorizedDecoratingSubmodelAggregatorFactory;
import org.eclipse.basyx.extensions.submodel.aggregator.authorization.SimpleRbacSubmodelAggregatorAuthorizer;
import org.eclipse.basyx.extensions.submodel.authorization.AuthorizedDecoratingSubmodelAPIFactory;
import org.eclipse.basyx.extensions.submodel.authorization.AuthorizedSubmodelAPI;
import org.eclipse.basyx.extensions.submodel.authorization.SimpleRbacSubmodelAPIAuthorizer;
import org.eclipse.basyx.submodel.aggregator.SubmodelAggregatorFactory;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.restapi.vab.VABSubmodelAPIFactory;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * This example constructs an SDK-based AAS Server and applies the security configuration
 * to enable authorization on the AAS Server. To be able to push data to the AAS Server, the credentials
 * of the {@link SharedConfig#SCENARIO_SETUP_USER_CREDENTIALS} Keycloak user will be used to retrieve
 * an access token from the Keycloak server first before making the request to the AAS server. This is
 * done by passing a Keycloak-specific {@link org.eclipse.basyx.vab.protocol.api.IConnectorFactory} to
 * {@link ConnectedAssetAdministrationShellManager}, injecting the token retrieval logic.
 *
 * This example also constructs an SDK-based Registry Server and applies security configuration
 * analogously. The AAS is registered at the registry using the {@link AuthorizedAASRegistryProxy} class
 * in the {@link ConnectedAssetAdministrationShellManager} to populate the Authorization header for access.
 */
public class AuthorizedAASServerAndRegistryUsingSDKExecutable {
  private static Logger logger = LoggerFactory.getLogger(
      AuthorizedAASServerAndRegistryUsingSDKExecutable.class);

  private final BaSyxContextConfiguration aasServerContextConfig;
  private final BaSyxSecurityConfiguration securityConfig;

  public static void main(String[] args) {
    new AuthorizedAASServerAndRegistryUsingSDKExecutable();
  }

  public AuthorizedAASServerAndRegistryUsingSDKExecutable() {
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

    BaSyxContext basyxContext = registryContextConfig.createBaSyxContext();

    RbacRuleSet rbacRuleSet = RbacRuleSet.fromFile(securityConfig.getAuthorizationStrategySimpleRbacRulesFilePath());

    basyxContext.addServletMapping("/*", new VABHTTPInterface<>(new AASRegistryModelProvider(
        new AuthorizedAASRegistry<>(
            new InMemoryRegistry(),
            new SimpleRbacAASRegistryAuthorizer<>(
                new PredefinedSetRbacRuleChecker(
                    rbacRuleSet
                ),
                new KeycloakRoleAuthenticator()
            ),
            new JWTAuthenticationContextProvider()
        )
    )));
    basyxContext.setJwtBearerTokenAuthenticationConfiguration(scenarioSetupKeycloakClient.createJwtBearerTokenAuthenticationConfiguration());

    BaSyxHTTPServer registryServer = new BaSyxHTTPServer(basyxContext);

    registryServer.start();
  }

  private void createAASServer() {
    BaSyxContext basyxContext = aasServerContextConfig.createBaSyxContext();

    RbacRuleSet rbacRuleSet = RbacRuleSet.fromFile(securityConfig.getAuthorizationStrategySimpleRbacRulesFilePath());

    final ISubjectInformationProvider<Jwt> subjectInformationProvider = new JWTAuthenticationContextProvider();
    final IRbacRuleChecker rbacRuleChecker = new PredefinedSetRbacRuleChecker(
        rbacRuleSet
    );
    final IRoleAuthenticator<Jwt> roleAuthenticator = new KeycloakRoleAuthenticator();

    basyxContext.addServletMapping("/*", new VABHTTPInterface<>(new AASAggregatorProvider(
        new AuthorizedAASAggregator<>(
            new AASAggregator(
                new AuthorizedDecoratingAASAPIFactory<>(
                    new AASAPIFactory(),
                    new SimpleRbacAASAPIAuthorizer<>(
                        rbacRuleChecker,
                        roleAuthenticator
                    ),
                    subjectInformationProvider
                ),
                new AuthorizedDecoratingSubmodelAggregatorFactory<>(
                    null,
                    new SubmodelAggregatorFactory(
                        new AuthorizedDecoratingSubmodelAPIFactory<>(
                            null,
                            new VABSubmodelAPIFactory(),
                            new SimpleRbacSubmodelAPIAuthorizer<>(
                                rbacRuleChecker,
                                roleAuthenticator
                            ),
                            subjectInformationProvider
                        )
                    ),
                    new SimpleRbacSubmodelAggregatorAuthorizer<>(
                        rbacRuleChecker,
                        roleAuthenticator
                    ),
                    subjectInformationProvider
                )
            ),
            new SimpleRbacAASAggregatorAuthorizer<>(
                rbacRuleChecker,
                roleAuthenticator
            ),
            subjectInformationProvider
        )
    )));
    basyxContext.setJwtBearerTokenAuthenticationConfiguration(scenarioSetupKeycloakClient.createJwtBearerTokenAuthenticationConfiguration());

    final BaSyxHTTPServer aasServer = new BaSyxHTTPServer(basyxContext);

    aasServer.start();

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
