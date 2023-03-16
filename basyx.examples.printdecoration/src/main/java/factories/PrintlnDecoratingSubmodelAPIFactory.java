package factories;

import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.restapi.api.ISubmodelAPI;
import org.eclipse.basyx.submodel.restapi.api.ISubmodelAPIFactory;

import decoration.PrintlnSubmodelAPI;

public class PrintlnDecoratingSubmodelAPIFactory implements ISubmodelAPIFactory {
	private ISubmodelAPIFactory apiFactory;
	
	public PrintlnDecoratingSubmodelAPIFactory(ISubmodelAPIFactory factoryToBeDecorated) {
		this.apiFactory = factoryToBeDecorated;
	}

	public ISubmodelAPI getSubmodelAPI(Submodel submodel) {
		ISubmodelAPI api = apiFactory.create(submodel);
		return new PrintlnSubmodelAPI(api);
	}

}
