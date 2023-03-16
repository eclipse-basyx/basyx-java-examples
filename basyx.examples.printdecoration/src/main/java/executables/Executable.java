package executables;

import org.eclipse.basyx.components.aas.AASServerComponent;
import org.eclipse.basyx.components.aas.aascomponent.IAASServerFeature;
import org.eclipse.basyx.components.aas.configuration.BaSyxAASServerConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.registry.RegistryComponent;
import org.eclipse.basyx.components.registry.configuration.BaSyxRegistryConfiguration;

import feature.PrintlnAASServerFeature;

public class Executable {
	public static void main(String[] args) {
		startRegistry();
		startAASServer();
	}

	private static void startAASServer() {
		BaSyxContextConfiguration config = new BaSyxContextConfiguration();
		config.loadFromResource("./serverContext.properties");

		BaSyxAASServerConfiguration aasConfig = new BaSyxAASServerConfiguration();
		aasConfig.loadFromDefaultSource();

		AASServerComponent server = new AASServerComponent(config, aasConfig);
		server.addAASServerFeature(createPrintlnFeature());
		server.startComponent();
	}

	private static void startRegistry() {
		BaSyxContextConfiguration config = new BaSyxContextConfiguration();
		config.loadFromResource("./registryContext.properties");

		BaSyxRegistryConfiguration registryConfig = new BaSyxRegistryConfiguration();
		registryConfig.loadFromDefaultSource();

		RegistryComponent registry = new RegistryComponent(config, registryConfig);
		registry.startComponent();
	}
	
	private static IAASServerFeature createPrintlnFeature() {
		return new PrintlnAASServerFeature();
	}
}
