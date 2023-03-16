package factories;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.restapi.api.IAASAPI;
import org.eclipse.basyx.aas.restapi.api.IAASAPIFactory;

import decoration.PrintlnAASAPI;

public class PrintlnDecoratingAASAPIFactory implements IAASAPIFactory {
	private IAASAPIFactory apiFactory;

	public PrintlnDecoratingAASAPIFactory(IAASAPIFactory factoryToBeDecorated) {
		this.apiFactory = factoryToBeDecorated;
	}

	public IAASAPI getAASApi(AssetAdministrationShell aas) {
		IAASAPI api = apiFactory.create(aas);
		return new PrintlnAASAPI(api);
	}
}
