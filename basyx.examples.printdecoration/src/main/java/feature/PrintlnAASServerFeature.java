package feature;

import org.eclipse.basyx.components.aas.aascomponent.IAASServerDecorator;
import org.eclipse.basyx.components.aas.aascomponent.IAASServerFeature;

public class PrintlnAASServerFeature implements IAASServerFeature {
	public void initialize() {
		// Code to create the context of the feature
	}

	public void cleanUp() {
		// Code to close the context of the feature
	}

	public IAASServerDecorator getDecorator() {
		return new PrintlnAASServerDecorator();
	}
}
