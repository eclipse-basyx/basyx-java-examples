/*******************************************************************************
 * Copyright (C) 2022 the Eclipse BaSyx Authors
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * SPDX-License-Identifier: MIT
 ******************************************************************************/
package org.eclipse.basyx.examples.scenarios.authorization.combined;

import java.util.Scanner;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.examples.scenarios.authorization.shared.AuthorizedClient;
import org.eclipse.basyx.examples.scenarios.authorization.shared.SharedConfig;
import org.eclipse.basyx.examples.scenarios.authorization.shared.UnauthenticatedClient;
import org.eclipse.basyx.examples.scenarios.authorization.shared.UnauthorizedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class creates a client for testing the AAS servers in
 * {@link AuthorizedAASServerAndRegistryUsingComponentsExecutable} and
 * {@link AuthorizedAASServerAndRegistryUsingSDKExecutable}.
 *
 * @author wege
 */
public class AASServerTestClient {
	private static Logger logger = LoggerFactory.getLogger(AASServerTestClient.class);

	public static void main(String[] args) {
		new AASServerTestClient();
	}

	public AASServerTestClient() {
		final BaSyxContextConfiguration contextConfig = SharedConfig.getAasServerContextConfig();
		final String baseUrl = contextConfig.getUrl();
		final String shellsUrl = String.format("%s/shells", baseUrl);

		try (final Scanner scanner = new Scanner(System.in)) {
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
}
