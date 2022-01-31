/*******************************************************************************
 * Copyright (C) 2022 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.examples.scenarios.authorization.exception;

/**
 * Used to indicate that the operation to create a 
 * Realm on the KeyCloak server has been failed.
 * 
 * @author danish
 *
 */
public class RealmCreationException extends Exception {
	private static final long serialVersionUID = 1L;

	public RealmCreationException(String message) {
        super(message);
    }
	
}
