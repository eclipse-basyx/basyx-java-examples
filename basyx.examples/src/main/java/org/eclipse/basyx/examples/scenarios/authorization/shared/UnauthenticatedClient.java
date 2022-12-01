package org.eclipse.basyx.examples.scenarios.authorization.shared;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnauthenticatedClient {
  private static Logger logger = LoggerFactory.getLogger(UnauthenticatedClient.class);

  public void tryGET(final String targetUrl) {
    try {
      HttpClient httpClient = HttpClients.createDefault();
      HttpGet httpGet = new HttpGet(targetUrl);

      logger.info("requesting GET {} without access token", targetUrl);

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
