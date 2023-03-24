package factories;

import org.eclipse.basyx.aas.aggregator.api.IAASAggregator;
import org.eclipse.basyx.aas.aggregator.api.IAASAggregatorFactory;

import decoration.PrintlnAASAggregator;

public class PrintlnDecoratingAASAggregatorFactory implements IAASAggregatorFactory {
	private IAASAggregatorFactory aggregatorFactory;

	public PrintlnDecoratingAASAggregatorFactory(IAASAggregatorFactory factoryToBeDecorated) {
		this.aggregatorFactory = factoryToBeDecorated;
	}

	public IAASAggregator create() {
		IAASAggregator aggregator = aggregatorFactory.create();
		return new PrintlnAASAggregator(aggregator);
	}
}
