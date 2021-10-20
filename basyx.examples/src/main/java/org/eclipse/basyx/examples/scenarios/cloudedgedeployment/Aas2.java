package org.eclipse.basyx.examples.scenarios.cloudedgedeployment;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.servlet.http.HttpServlet;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.aas.restapi.AASModelProvider;
import org.eclipse.basyx.aas.restapi.MultiSubmodelProvider;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;
import org.eclipse.basyx.submodel.restapi.SubmodelProvider;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;

public class Aas2 extends AssetAdministrationShell {

	private Submodel testSubmodel1;

	Aas2() {
		super();

		setIdShort("aas2");
		setIdentification(IdentifierType.IRI, "http://www.sartorius.com/sli/aas/0/0/aas2");

		testSubmodel1 = new Submodel();

		testSubmodel1.setIdShort("testSubmodel1");
		testSubmodel1.setIdentification(IdentifierType.IRI, "http://www.sartorius.com/sli/submodel/0/0/testSubmodel");

		Operation smc_result_Op = new Operation();
		smc_result_Op.setIdShort("smc_result");
		SubmodelElementCollection collection = new SubmodelElementCollection("result");
		// Property collection = new Property("");
		collection.setIdShort("result");

		OperationVariable outputOV = new OperationVariable(collection);
		List<OperationVariable> out = new ArrayList<>();
		out.add(outputOV);
		smc_result_Op.setOutputVariables(out);

		smc_result_Op.setInvokable((Function<Object[], Object>) a -> smc_result());

		testSubmodel1.addSubmodelElement(smc_result_Op);

	}

	public void startRESTServer() {

		MultiSubmodelProvider provider = new MultiSubmodelProvider(new AASModelProvider(this));

		HttpServlet aasServlet = new VABHTTPInterface<IModelProvider>(provider);

		provider.addSubmodel(new SubmodelProvider(testSubmodel1));

		BaSyxContext context = new BaSyxContext("", "", "localhost", 4001);
		context.addServletMapping("/*", aasServlet);
		BaSyxHTTPServer httpServer = new BaSyxHTTPServer(context);

		httpServer.start();

	}

	public void registerAAS(AASRegistryProxy registry) {

		AASDescriptor aasDescriptor = new AASDescriptor(this, "http://localhost:4001/aas");
		SubmodelDescriptor sm1Descriptor = new SubmodelDescriptor(testSubmodel1,
				"http://localhost:4000/aas/submodels/testSubmodel1");

		aasDescriptor.addSubmodelDescriptor(sm1Descriptor);

		registry.register(aasDescriptor);

	}

	private Object smc_result() {
		System.out.println("Operation smc_result");

		Property property = new Property("Hello, World!");
		property.setIdShort("message");

		List<SubmodelElement> collection = new ArrayList<>();
		collection.add(property);

		return collection;
	}

}
