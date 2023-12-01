/*******************************************************************************
 * Copyright (C) 2023 the Eclipse BaSyx Authors
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

package org.eclipse.basyx.examples.timeseries;

import java.util.Set;
import java.lang.Exception;

import org.eclipse.basyx.aas.bundle.AASBundle;
import org.eclipse.basyx.aas.factory.aasx.AASXToMetamodelConverter;

/*
 * Given path, and IdShort of AAS, imports an aasx and returns the AASBundle within
 * 
 * author Al-Obaidi
 */
public class AASXImporter {
    public static AASBundle getAasFromAasx(String path, String aasIdShort) throws Exception {
        AASXToMetamodelConverter aasxConverter = new AASXToMetamodelConverter(path);
        Set<AASBundle> aasBundles = aasxConverter.retrieveAASBundles();
        for (AASBundle aasBundle : aasBundles) {
            if (aasIdShort.equals(aasBundle.getAAS().getIdShort()))
                return aasBundle;
        }
        throw new Exception("AAS not found in AASX");
    }
}
