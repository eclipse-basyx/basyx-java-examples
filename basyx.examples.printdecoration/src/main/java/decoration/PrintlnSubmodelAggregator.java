package decoration;

import java.util.Collection;

import org.eclipse.basyx.submodel.aggregator.api.ISubmodelAggregator;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.restapi.api.ISubmodelAPI;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;

public class PrintlnSubmodelAggregator implements ISubmodelAggregator {
	private ISubmodelAggregator aggregator;

	public PrintlnSubmodelAggregator(ISubmodelAggregator aggregator) {
		this.aggregator = aggregator;
	}

	public Collection<ISubmodel> getSubmodelList() {
		System.out.println("Get submodel list");
		return aggregator.getSubmodelList();
	}

	public ISubmodel getSubmodel(IIdentifier identifier) throws ResourceNotFoundException {
		System.out.println("Get submodel: " + identifier.getId());
		return aggregator.getSubmodel(identifier);
	}

	public ISubmodel getSubmodelbyIdShort(String idShort) throws ResourceNotFoundException {
		System.out.println("Get submodel: " + idShort);
		return aggregator.getSubmodelbyIdShort(idShort);
	}

	public ISubmodelAPI getSubmodelAPIById(IIdentifier identifier) throws ResourceNotFoundException {
		System.out.println("Get submodel API: " + identifier.getId());
		return aggregator.getSubmodelAPIById(identifier);
	}

	public ISubmodelAPI getSubmodelAPIByIdShort(String idShort) throws ResourceNotFoundException {
		System.out.println("Get submodel API: " + idShort);
		return aggregator.getSubmodelAPIByIdShort(idShort);
	}

	public void createSubmodel(Submodel submodel) {
		System.out.println("Create submodel: " + submodel.getIdShort());
		aggregator.createSubmodel(submodel);
	}

	public void createSubmodel(ISubmodelAPI submodelAPI) {
		System.out.println("Create submodel: " + submodelAPI.getSubmodel().getIdShort());
		aggregator.createSubmodel(submodelAPI);
	}

	public void updateSubmodel(Submodel submodel) throws ResourceNotFoundException {
		System.out.println("Update submodel: " + submodel.getIdShort());
		aggregator.updateSubmodel(submodel);
	}

	public void deleteSubmodelByIdentifier(IIdentifier identifier) {
		System.out.println("Delete submodel: " + identifier.getId());
		aggregator.deleteSubmodelByIdentifier(identifier);
	}

	public void deleteSubmodelByIdShort(String idShort) {
		System.out.println("Delete submodel: " + idShort);
		aggregator.deleteSubmodelByIdShort(idShort);
	}

}
