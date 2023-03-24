package decoration;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.restapi.api.IAASAPI;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;

public class PrintlnAASAPI implements IAASAPI {
	private IAASAPI api;

	public PrintlnAASAPI(IAASAPI api) {
		this.api = api;
	}

	public IAssetAdministrationShell getAAS() {
		IAssetAdministrationShell aas = api.getAAS();
		System.out.println("Get AAS: " + aas.getIdShort());
		return aas;
	}

	public void addSubmodel(IReference submodel) {
		System.out.println("Add submodel: " + submodel);
		api.addSubmodel(submodel);
	}

	public void removeSubmodel(String idShort) {
		System.out.println("Remove submodel: " + idShort);
		api.removeSubmodel(idShort);
	}
}
