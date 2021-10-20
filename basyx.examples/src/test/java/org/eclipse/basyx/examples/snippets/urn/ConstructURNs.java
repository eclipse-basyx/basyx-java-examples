/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.snippets.urn;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.junit.Test;



/**
 * This code snippet illustrate both constructors of ModelURN class to create URNs
 * 
 * The BaSyx URN format is as following:
 *   urn:<legalEntity>:<subUnit>:<subModel>:<version>:<revision>:<elementID>#<elementInstance>
 *   
 * LegalEntity describes the organization that the object referred by the URN belongs to. The sub unit is the
 * referred sub unit of the organization. The sub model defines the actual sub model that is referred by the
 * URN, for example AAS for the Asset Administration shell. If sub model shall receive individual URNs, they
 * may be distinguished by this field. Version and revision define the version and revision of the element that
 * is referred by this URN. The element is the type of the referred element, e.g. a worker, a device, a product, 
 * or part of a device. The element instance is the concrete instance. 
 * 
 * @author kuhn
 *
 */
public class ConstructURNs {

		
	/**
	 * Run code snippet
	 */
	@Test
	public void snippet() throws Exception {
		
		// Create model URN
		// - The following creates a URN for the AAS of the first device x-509 (instance:001), belonging to de.fhg, sub unit devices.es.iese. 
		//   The URN refers to the version 1.0 and revision 3.
		ModelUrn modelURN1 = new ModelUrn("de.FHG", "devices.es.iese", "AAS", "1.0", "3", "x-509", "001");
		
		// Create model URN using string constructor
		// - The following creates a URN for the AAS of the first device x-509 (instance:001), belonging to de.fhg, sub unit devices.es.iese. 
		//   The URN refers to the version 1.0 and revision 3.
		ModelUrn modelURN2 = new ModelUrn("urn:de.FHG:devices.es.iese:AAS:1.0:3:x-509#001");

		
		
		// Validate results
		// - Check equality of both created URNs
		assertTrue(modelURN1.getURN().equals(modelURN2.getURN()));
	}
}

