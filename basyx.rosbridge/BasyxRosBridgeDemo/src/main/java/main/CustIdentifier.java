package main;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;


public class CustIdentifier implements IIdentifier {

	private String id;
	
	public CustIdentifier(String id) {
		this.id = id;
	}
	
	public IdentifierType getIdType() {
		return IdentifierType.CUSTOM;
	}


	public String getId() {
		return this.id;
	}
	
}
