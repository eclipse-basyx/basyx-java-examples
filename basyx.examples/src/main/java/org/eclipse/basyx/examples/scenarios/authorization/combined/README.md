# Examples for authorized AAS and Registry servers

The scenarios here show how to set up an AAS server and registry server with authorization.

As a precondition, you need to have a non-TLS Keycloak server running on the url specified in the resources/combined/security.properties file (tested with Keycloak 20.0.0). You can adapt the url in there if needed.

For the required Keycloak configuration, you can run the SetupKeycloak class to automatically create the realm, client, roles and users + user role mappings.

Now can either run the [AuthorizedAASServerUsingComponentsExecutable](./AuthorizedAASServerAndRegistryUsingComponentsExecutable.java) or the [AuthorizedAASServerAndRegistryUsingSDKExecutable](./AuthorizedAASServerAndRegistryUsingSDKExecutable.java) class.
Initialization has finished when it says "Scenario started" in the output.
Leave the thereby launched BaSyx servers running so you can try to make requests against them.


For testing, you can run the [AASServerTestClient](./AASServerTestClient.java) and [RegistryTestClient](./RegistryTestClient.java) classes, which have options to make requests with different predefined user authentications.
Or you can use an HTTP client of your choice to make the requests.
On default in the scenario:

Getting the access token from Keycloak:
```
curl --location --request POST 'http://localhost:9006/realms/basyx-demo/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'client_id=demo-device' \
--data-urlencode 'client_secret=UBBEVnUBRDxsOu5HYObmSmYNVaz9EbTc' \
--data-urlencode 'username=unauthorized' \
--data-urlencode 'password=unauthorizedPW'
```
other users:

| Username          | Password            |
| ----------------- | ------------------- |
| authorized_reader | authorized_readerPW |
| scenario          | scenarioPW          |


```
curl --location --request GET 'http://localhost:4003/aasServer/shells' \
--header 'Authorization: Bearer <keycloak token here>
```
other endpoints: https://app.swaggerhub.com/apis-docs/BaSyx/basyx_asset_administration_shell_repository_http_rest_api/v1

```
curl --location --request GET 'http://localhost:4000/registry/api/v1/registry'
```
other endpoints: https://app.swaggerhub.com/apis-docs/BaSyx/BaSyx_Registry_API/v1


If you want to try out other configurations, you can adapt the files:

* [SharedConfig](../shared/SharedConfig.java) (links to the properties files and defines the users)
* [security.properties](../../../../../../../../resources/combined/security.properties) (holds the Keycloak server url, realm, authorization parameters)
* [rbac_rules.json](../../../../../../../../resources/combined/rbac_rules.json) (holds the permissions for the different roles as referenced by Keycloak/the access token)
* [aasServer.properties](../../../../../../../../resources/combined/aasServer.properties) (holds the authorization switch for the aas server)
* [aasServer_context.properties](../../../../../../../../resources/combined/aasServer_context.properties) (holds the context path and url for the aas server)
* [registry.properties](../../../../../../../../resources/combined/registry.properties) (holds the authorization switch for the registry server)
* [registry_context.properties](../../../../../../../../resources/combined/registry_context.properties) (holds the context path and url for the registry server)


Mind that the setups of the scenarios require permissions, too, in order to push the data to the aas server and registry (hence the "scenario" user).
The scenarios are laid out for the SimpleRbac authorization scheme.