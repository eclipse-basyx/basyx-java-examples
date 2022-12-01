package org.eclipse.basyx.examples.scenarios.authorization.shared;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.eclipse.basyx.components.configuration.BaSyxSecurityConfiguration;
import org.eclipse.basyx.examples.scenarios.authorization.shared.SharedConfig.UserCredentials;
import org.eclipse.basyx.extensions.shared.authorization.KeycloakService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthorizedClient {
  private static Logger logger = LoggerFactory.getLogger(AuthorizedClient.class);

  private final BaSyxSecurityConfiguration securityConfig;
  private final KeycloakService keycloakService;
  private final String clientId;
  private final String clientSecret;
  private final UserCredentials userCredentials;

  public AuthorizedClient() {
    securityConfig = SharedConfig.getSecurityConfig();
    clientId = SharedConfig.KEYCLOAK_CLIENT_ID;
    clientSecret = SharedConfig.KEYCLOAK_CLIENT_SECRET;
    userCredentials = SharedConfig.AUTHORIZED_READER_USER_CREDENTIALS;

    keycloakService = createKeycloakClient();
  }

  private KeycloakService createKeycloakClient() {
    final String serverUrl = securityConfig.getAuthorizationStrategyJwtBearerTokenAuthenticationConfigurationProviderKeycloakServerUrl();
    final String realm = securityConfig.getAuthorizationStrategyJwtBearerTokenAuthenticationConfigurationProviderKeycloakRealm();

    logger.info("use keycloak: serverUrl={} realm={}", serverUrl, realm);

    final KeycloakService keycloak = new KeycloakService(serverUrl, realm);

    keycloak.setCredentials(
        clientId,
        clientSecret,
        userCredentials.getUserName(),
        userCredentials.getPassword()
    );

    return keycloak;
  }

  public void tryGET(final String targetUrl) {
    try {
      logger.info("get access token using credentials clientId={} clientSecret={} userName={} password={}", clientId, clientSecret, userCredentials.getUserName(), userCredentials.getPassword());

      final String bearerToken = keycloakService.getTokenAsBearer().orElseThrow(() -> new RuntimeException("could not get token"));

      logger.info("retrieved access token {}", bearerToken);

      HttpClient httpClient = HttpClients.createDefault();
      HttpGet httpGet = new HttpGet(targetUrl);

      httpGet.setHeader("Authorization", bearerToken);

      logger.info("requesting GET {} with access token {}", targetUrl, bearerToken);

      final HttpResponse httpResponse = httpClient.execute(httpGet);

      logger.info("response status:");
      logger.info("{}", httpResponse.getStatusLine());

      final HttpEntity responseEntity = httpResponse.getEntity();
      final String responseEntityString = EntityUtils.toString(responseEntity);

      logger.info("response payload:");
      logger.info("{}", responseEntityString);
    } catch (final IOException e) {
      logger.error(e.getMessage(), e);
    }
  }
}
