package factories;

import org.eclipse.basyx.submodel.aggregator.api.ISubmodelAggregator;
import org.eclipse.basyx.submodel.aggregator.api.ISubmodelAggregatorFactory;

import decoration.PrintlnSubmodelAggregator;

public class PrintlnDecoratingSubmodelAggregatorFactory implements ISubmodelAggregatorFactory {
	private ISubmodelAggregatorFactory aggregatorFactory;

	public PrintlnDecoratingSubmodelAggregatorFactory(ISubmodelAggregatorFactory factoryToBeDecorated) {
		this.aggregatorFactory = factoryToBeDecorated;
	}

	public ISubmodelAggregator create() {
		ISubmodelAggregator aggregator = aggregatorFactory.create();
		return new PrintlnSubmodelAggregator(aggregator);
	}

}
