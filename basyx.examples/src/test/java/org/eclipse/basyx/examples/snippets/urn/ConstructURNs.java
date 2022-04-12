/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * SPDX-License-Identifier: MIT
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

