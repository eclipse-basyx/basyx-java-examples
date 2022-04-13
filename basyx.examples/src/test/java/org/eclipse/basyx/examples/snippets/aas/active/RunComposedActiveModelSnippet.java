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
package org.eclipse.basyx.examples.snippets.aas.active;

import org.eclipse.basyx.examples.snippets.aas.active.tasks.AverageTask;
import org.eclipse.basyx.examples.snippets.aas.active.tasks.IncrementTask;
import org.eclipse.basyx.submodel.restapi.SubmodelProvider;
import org.eclipse.basyx.tools.aas.active.ActiveModel;
import org.eclipse.basyx.tools.aas.active.VABModelTaskGroup;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProviderHelper;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunComposedActiveModelSnippet {

	/**
	 * Initiates a logger using the current class
	 */
	private static final Logger logger = LoggerFactory.getLogger(RunComposedActiveModelSnippet.class);

	/**
	 * Test active model computing and printing an average temperature property
	 */
	@Test
	@Ignore
	public void snippet() throws Exception {
		// Create the model provider for the active model
		IModelProvider modelProvider = new SubmodelProvider();
		modelProvider.createValue("count", 0);
		modelProvider.createValue("temperature", VABLambdaProviderHelper.createSimple(() -> {
			return 30d + (Math.random() * 10d - 5d);
		}, null));

		// Create an active model based on the previously created IModelProvider
		// => Could be any model provider here, also a connected model provider or one
		// containing a submodel
		ActiveModel activeModel = new ActiveModel(modelProvider);

		// Add a task group with multiple tasks to the active model
		// The groups' update interval is set to 20x per second
		// Then the group is started.
		activeModel.createTaskGroup().addTask(new IncrementTask("/count")).addTask(new AverageTask(0.01f, "/temperature", "/average")).setUpdateInterval(50).start();

		// Runs a task group with a single task (1x per second)
		VABModelTaskGroup printerGroup = activeModel.runTask(1000, model -> {
			logger.debug("Current count: " + model.getValue("/count"));
			logger.debug("Current average: " + model.getValue("/average"));
		});

		// Adds an additional task to the existing task group
		printerGroup.addTask(model -> {
			logger.debug("Printed count + average");
		});

		// Wait for 5 seconds and then stop the printerGroup.
		// The other task group is still scheduled
		Thread.sleep(5000);
		printerGroup.stop();

		// Wait again and then stop all groups. Then start only the printerGroup, but
		// with a faster update interval
		Thread.sleep(5000);
		activeModel.stopAll();
		printerGroup.setUpdateInterval(100).start();

		// Finally stop and clear the model
		Thread.sleep(1000);
		activeModel.clear();

	}
}
