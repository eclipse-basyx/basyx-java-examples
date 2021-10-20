package org.eclipse.basyx.examples.scenarios.cloudedgedeployment;

import java.util.Map;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.registry.RegistryComponent;
import org.eclipse.basyx.components.registry.configuration.BaSyxRegistryConfiguration;
import org.eclipse.basyx.components.registry.configuration.RegistryBackend;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;

class Example2 {
	private static ISubmodel testSubmodel1;
	private static Map<String, IOperation> operations;

	public static void main(String[] args) throws Exception {
		startRegistry();
		AASRegistryProxy registry = new AASRegistryProxy("http://localhost:4000/registry/");

		Aas2 aas1 = new Aas2();
		aas1.startRESTServer();

		aas1.registerAAS(registry);

		connect_to_aas(registry, new Identifier(IdentifierType.IRI, "http://www.sartorius.com/sli/aas/0/0/aas2"));

		// Beispiel - Aufruf einer Operation mit SubmodelElementCollection als R�ckgabe
		// Ergebnis ist als erstes SubmodelElement des Array ein Property mit einer Map
		// als Value
		// Erwartung w�re, dass ich die aus der Operation zur�ckgegebene
		// SubmodelElementCollection erhalte

		try {

			SubmodelElement[] result = operations.get("smc_result").invoke();

			for (SubmodelElement element : result) {
				System.out.println("Result-Type: " + element.getClass().getSimpleName());
				System.out.println("Result: " + element);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static void connect_to_aas(AASRegistryProxy registry, IIdentifier aas_identifier) {

		ConnectedAssetAdministrationShellManager connManager = new ConnectedAssetAdministrationShellManager(registry);
		ConnectedAssetAdministrationShell aas1 = connManager.retrieveAAS(aas_identifier);

		Map<String, ISubmodel> map = aas1.getSubmodels();

		testSubmodel1 = map.get("testSubmodel1");

		operations = testSubmodel1.getOperations();

	}

	private static void startRegistry() {
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration(4000, "/registry");
		BaSyxRegistryConfiguration registryConfig = new BaSyxRegistryConfiguration(RegistryBackend.INMEMORY);
		RegistryComponent registry = new RegistryComponent(contextConfig, registryConfig);

		// Start the created server
		registry.startComponent();
	}

}