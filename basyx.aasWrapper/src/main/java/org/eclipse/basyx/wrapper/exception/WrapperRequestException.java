/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.wrapper.exception;

/**
 * Exception that is thrown when the wrapper cannot request a value from its datasource
 * 
 * @author espen
 *
 */
public class WrapperRequestException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public WrapperRequestException(String message) {
        super(message);
    }

    public WrapperRequestException(String message, Throwable error) {
        super(message, error);
    }

}
