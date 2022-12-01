package org.eclipse.basyx.examples.scenarios.authorization.combined;

import java.util.Scanner;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.examples.scenarios.authorization.shared.AuthorizedClient;
import org.eclipse.basyx.examples.scenarios.authorization.shared.SharedConfig;
import org.eclipse.basyx.examples.scenarios.authorization.shared.UnauthenticatedClient;
import org.eclipse.basyx.examples.scenarios.authorization.shared.UnauthorizedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegistryTestClient {
  private static Logger logger = LoggerFactory.getLogger(RegistryTestClient.class);

  public static void main(String[] args) {
    new RegistryTestClient();
  }

  public RegistryTestClient() {
    final BaSyxContextConfiguration contextConfig = SharedConfig.getRegistryContextConfig();
    final String baseUrl = contextConfig.getUrl();
    final String shellsUrl = String.format("%s/api/v1/registry", baseUrl);

    final Scanner scanner = new Scanner(System.in);

    while (true) {
      logger.info("please decide (0 - unauthenticated, 1 - unauthorized, 2 - authorized, q - quit):");
      final String input = scanner.nextLine();
      boolean cancel = false;
      switch (input) {
        case "0": {
          logger.info("execute unauthenticated client...");
          new UnauthenticatedClient().tryGET(shellsUrl);
          logger.info("finished unauthenticated client");
          break;
        }
        case "1": {
          logger.info("execute unauthorized client...");
          new UnauthorizedClient().tryGET(shellsUrl);
          logger.info("finished unauthorized client");
          break;
        }
        case "2": {
          logger.info("execute authrorized client...");
          new AuthorizedClient().tryGET(shellsUrl);
          logger.info("finished authrorized client...");
          break;
        }
        case "q": {
          cancel = true;
          break;
        }
        default: {
          logger.info("invalid option");
        }
      }
      if (cancel) {
        break;
      }
    }
  }
}
