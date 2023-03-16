package feature;

import org.eclipse.basyx.aas.aggregator.api.IAASAggregatorFactory;
import org.eclipse.basyx.aas.restapi.api.IAASAPIFactory;
import org.eclipse.basyx.components.aas.aascomponent.IAASServerDecorator;
import org.eclipse.basyx.submodel.aggregator.api.ISubmodelAggregatorFactory;
import org.eclipse.basyx.submodel.restapi.api.ISubmodelAPIFactory;

import factories.PrintlnDecoratingAASAPIFactory;
import factories.PrintlnDecoratingAASAggregatorFactory;
import factories.PrintlnDecoratingSubmodelAPIFactory;
import factories.PrintlnDecoratingSubmodelAggregatorFactory;

public class PrintlnAASServerDecorator implements IAASServerDecorator {

	public ISubmodelAPIFactory decorateSubmodelAPIFactory(ISubmodelAPIFactory submodelAPIFactory) {
		return new PrintlnDecoratingSubmodelAPIFactory(submodelAPIFactory);
	}

	public ISubmodelAggregatorFactory decorateSubmodelAggregatorFactory(
			ISubmodelAggregatorFactory submodelAggregatorFactory) {
		return new PrintlnDecoratingSubmodelAggregatorFactory(submodelAggregatorFactory);
	}

	public IAASAPIFactory decorateAASAPIFactory(IAASAPIFactory aasAPIFactory) {
		return new PrintlnDecoratingAASAPIFactory(aasAPIFactory);
	}

	public IAASAggregatorFactory decorateAASAggregatorFactory(IAASAggregatorFactory aasAggregatorFactory) {
		return new PrintlnDecoratingAASAggregatorFactory(aasAggregatorFactory);
	}

}
