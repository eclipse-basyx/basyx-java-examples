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
package org.eclipse.basyx.examples.scenarios.authorization.shared;

import java.util.Optional;
import org.eclipse.basyx.components.aas.configuration.BaSyxAASServerConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxSecurityConfiguration;
import org.eclipse.basyx.components.registry.configuration.BaSyxRegistryConfiguration;

public final class SharedConfig {
	private SharedConfig() {
	}

	public static final String AAS_SERVER_CONTEXT_CONFIG_PATH = "combined/aasServer_context.properties";
	public static final String AAS_SERVER_CONFIG_PATH = "combined/aasServer.properties";
	public static final String REGISTRY_CONTEXT_CONFIG_PATH = "combined/registry_context.properties";
	public static final String REGISTRY_CONFIG_PATH = "combined/registry.properties";
	public static final String SECURITY_CONFIG_PATH = "combined/security.properties";

	public static final String KEYCLOAK_CLIENT_ID = Optional.ofNullable(System.getenv("KEYCLOAK_CLIENT_ID")).orElse("demo-device");
	public static final String KEYCLOAK_CLIENT_SECRET = Optional.ofNullable(System.getenv("KEYCLOAK_CLIENT_SECRET")).orElse("UBBEVnUBRDxsOu5HYObmSmYNVaz9EbTc");

	public static final String KEYCLOAK_SCOPED_CLIENT_ID = Optional.ofNullable(System.getenv("KEYCLOAK_SCOPED_CLIENT_ID")).orElse("scoped-demo-device");
	public static final String KEYCLOAK_SCOPED_CLIENT_SECRET = Optional.ofNullable(System.getenv("KEYCLOAK_SCOPED_CLIENT_SECRET")).orElse("ABBAVnUBRDxsOu5HYObmSmYNVaz9EbTo");

	public static final String KEYCLOAK_SCOPELESS_CLIENT_ID = Optional.ofNullable(System.getenv("KEYCLOAK_SCOPELESS_CLIENT_ID")).orElse("scopeless-demo-device");
	public static final String KEYCLOAK_SCOPELESS_CLIENT_SECRET = Optional.ofNullable(System.getenv("KEYCLOAK_SCOPELESS_CLIENT_SECRET")).orElse("WXXZVnUBRDxsOu5HYObmSmYNVaz9EbTy");

	public static final UserCredentials AUTHORIZED_READER_USER_CREDENTIALS = new UserCredentials("authorized_reader", "authorized_readerPW");
	public static final UserCredentials UNAUTHENTICATED_USER_CREDENTIALS = new UserCredentials("unauthenticated", "unauthenticatedPW");
	public static final UserCredentials UNAUTHORIZED_USER_CREDENTIALS = new UserCredentials("unauthorized", "unauthorizedPW");
	public static final UserCredentials SCENARIO_SETUP_USER_CREDENTIALS = new UserCredentials("scenario", "scenarioPW");
	public static final UserCredentials KEYCLOAK_ADMIN_USER_CREDENTIALS = new UserCredentials("admin", "admin");

	public static BaSyxContextConfiguration getAasServerContextConfig() {
		final BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration();
		contextConfig.loadFromResource(AAS_SERVER_CONTEXT_CONFIG_PATH);
		return contextConfig;
	}

	public static BaSyxAASServerConfiguration getAasServerConfig() {
		final BaSyxAASServerConfiguration aasConfig = new BaSyxAASServerConfiguration();
		aasConfig.loadFromResource(AAS_SERVER_CONFIG_PATH);
		return aasConfig;
	}

	public static BaSyxContextConfiguration getRegistryContextConfig() {
		final BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration();
		contextConfig.loadFromResource(REGISTRY_CONTEXT_CONFIG_PATH);
		return contextConfig;
	}

	public static BaSyxRegistryConfiguration getRegistryConfig() {
		final BaSyxRegistryConfiguration registryConfig = new BaSyxRegistryConfiguration();
		registryConfig.loadFromResource(REGISTRY_CONFIG_PATH);
		return registryConfig;
	}

	public static BaSyxSecurityConfiguration getSecurityConfig() {
		final BaSyxSecurityConfiguration securityConfig = new BaSyxSecurityConfiguration();
		securityConfig.loadFromResource(SECURITY_CONFIG_PATH);
		return securityConfig;
	}

	public static class UserCredentials {
		private final String userName;
		private final String password;

		public UserCredentials(String userName, String password) {
			this.userName = userName;
			this.password = password;
		}

		public String getUserName() {
			return userName;
		}

		public String getPassword() {
			return password;
		}
	}
}
