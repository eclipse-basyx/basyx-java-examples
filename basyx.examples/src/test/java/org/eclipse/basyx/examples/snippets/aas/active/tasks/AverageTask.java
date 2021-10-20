/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
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
