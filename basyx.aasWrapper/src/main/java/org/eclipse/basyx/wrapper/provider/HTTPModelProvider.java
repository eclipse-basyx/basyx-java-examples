/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.wrapper.provider;

/**
 * A simple, generic HTTP-REST model provider
 * 
 * @author espen
 *
 */
public interface HTTPModelProvider {
	public Object get(String path);

	public Object delete(String path);

	public Object put(String path, Object data);

	public Object post(String path, Object data);

	public Object patch(String path, Object data);
}
