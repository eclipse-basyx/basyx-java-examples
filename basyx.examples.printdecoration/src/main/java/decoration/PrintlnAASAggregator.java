package decoration;

import java.util.Collection;

import org.eclipse.basyx.aas.aggregator.api.IAASAggregator;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

public class PrintlnAASAggregator implements IAASAggregator {
	private IAASAggregator aggregator;

	public PrintlnAASAggregator(IAASAggregator aggregator) {
		this.aggregator = aggregator;
	}

	public Collection<IAssetAdministrationShell> getAASList() {
		System.out.println("Get AAS list");
		return aggregator.getAASList();
	}

	public IAssetAdministrationShell getAAS(IIdentifier aasId) throws ResourceNotFoundException {
		System.out.println("Get AAS: " + aasId);
		return aggregator.getAAS(aasId);
	}

	public IModelProvider getAASProvider(IIdentifier aasId) throws ResourceNotFoundException {
		System.out.println("Get AASProvider: " + aasId);
		return aggregator.getAASProvider(aasId);
	}

	public void createAAS(AssetAdministrationShell aas) {
		System.out.println("Create AAS: " + aas.getIdentification().getId());
		aggregator.createAAS(aas);
	}

	public void updateAAS(AssetAdministrationShell aas) throws ResourceNotFoundException {
		System.out.println("Update AAS: " + aas.getIdentification().getId());
		aggregator.updateAAS(aas);
	}

	public void deleteAAS(IIdentifier aasId) {
		System.out.println("Delete AAS: " + aasId);
		aggregator.deleteAAS(aasId);
	}
}
