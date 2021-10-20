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
 * Task for incrementing a given property in each execution step
 * 
 * @author espen
 *
 */
public class IncrementTask implements VABModelTask {
	private String path;

	public IncrementTask(String path) {
		this.path = path;
	}

	@Override
	public void execute(IModelProvider model) throws Exception {
		int current = (int) model.getValue(path);
		model.setValue(path, current + 1);
	}
}
