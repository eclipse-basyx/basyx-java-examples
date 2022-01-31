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
 * Used to indicate that the operation to delete a 
 * specific Realm from the KeyCloak server has been failed.
 * 
 * @author danish
 *
 */
public class RealmDeletionException extends Exception {
	private static final long serialVersionUID = 1L;

	public RealmDeletionException(String message) {
        super(message);
    }

}
