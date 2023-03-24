package decoration;

import java.util.Collection;

import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.restapi.api.ISubmodelAPI;

public class PrintlnSubmodelAPI implements ISubmodelAPI {
	private ISubmodelAPI api;

	public PrintlnSubmodelAPI(ISubmodelAPI api) {
		this.api = api;
	}

	public ISubmodel getSubmodel() {
		ISubmodel submodel = api.getSubmodel();
		System.out.println("Get submodel: " + submodel.getIdShort());
		return submodel;
	}

	public void addSubmodelElement(ISubmodelElement elem) {
		System.out.println("Add submodel element: " + elem.getIdShort());
		api.addSubmodelElement(elem);
	}

	public void addSubmodelElement(String idShortPath, ISubmodelElement elem) {
		System.out.println("Add submodel element: " + idShortPath + elem.getIdShort());
		api.addSubmodelElement(idShortPath, elem);
	}

	public ISubmodelElement getSubmodelElement(String idShortPath) {
		System.out.println("Get submodel element: " + idShortPath);
		return api.getSubmodelElement(idShortPath);
	}

	public void deleteSubmodelElement(String idShortPath) {
		System.out.println("Delete submodel element: " + idShortPath);
		api.deleteSubmodelElement(idShortPath);
	}

	public Collection<IOperation> getOperations() {
		System.out.println("Get operations");
		return api.getOperations();
	}

	public Collection<ISubmodelElement> getSubmodelElements() {
		System.out.println("Get submodel elements");
		return api.getSubmodelElements();
	}

	public void updateSubmodelElement(String idShortPath, Object newValue) {
		System.out.println("Update submodel element: " + idShortPath);
		api.updateSubmodelElement(idShortPath, newValue);
	}

	public Object getSubmodelElementValue(String idShortPath) {
		System.out.println("Get submodel element value: " + idShortPath);
		return api.getSubmodelElementValue(idShortPath);
	}

	public Object invokeOperation(String idShortPath, Object... params) {
		System.out.println("Invoke operation: " + idShortPath);
		return api.invokeOperation(idShortPath, params);
	}

	public Object invokeAsync(String idShortPath, Object... params) {
		System.out.println("Invoke async: " + idShortPath);
		return api.invokeAsync(idShortPath, params);
	}

	public Object getOperationResult(String idShort, String requestId) {
		System.out.println("Get operation result: " + idShort);
		return api.getOperationResult(idShort, requestId);
	}
}
