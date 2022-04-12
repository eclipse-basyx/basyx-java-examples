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
package org.eclipse.basyx.examples.snippets.aas.active.tasks;

import org.eclipse.basyx.tools.aas.active.VABModelTask;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

/**
 * Task for computing the average value from a source property
 * 
 * @author espen
 *
 */
public class AverageTask implements VABModelTask {
	private float factor;
	private String sourcePath;
	private String targetPath;
	private double lastAverage;

	public AverageTask(float factor, String sourcePath, String targetPath) {
		this.factor = factor;
		this.sourcePath = sourcePath;
		this.targetPath = targetPath;
	}

	@Override
	public void execute(IModelProvider model) throws Exception {
		double nextValue = (double) model.getValue(sourcePath);
		double average = lastAverage;
		if ( average == 0 ) {
			average = nextValue;
		} else {
			average = average * (1 - factor) + nextValue * factor;
		}
		model.setValue(targetPath, average);
		lastAverage = average;
	}
}
