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
 
package basyx_ros_bridge.components;
import java.math.BigInteger;

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetype.ValueType;

public class BasyxJavaTypeConverter {
	public static ValueType javaPrimitiveToBasyxType(String java_primitive) {
		if (java_primitive.equals("byte")) {
			return ValueType.Int8;
		}
		else if (java_primitive.equals("short")) {
			return ValueType.Int16;
		}
		else if (java_primitive.equals("int")) {
			return ValueType.Int32;
		}
		else if (java_primitive.equals("long")) {
			return ValueType.Int64;
		}
		else if (java_primitive.equals("float")) {
			return ValueType.Float;
		}
		else if (java_primitive.equals("double")) {
			return ValueType.Double;
		}
		else if (java_primitive.equals("String")) {
			return ValueType.String;
		}
		else if (java_primitive.equals("boolean")) {
			return ValueType.Boolean;
		}
		else {
			return null;
		}
	}
	
	public static ValueType javaWrapperToBasyxType(String javaWrapper) {
		if (javaWrapper.equals(Byte.class.toString())) {
			return ValueType.Int8;
		}
		else if (javaWrapper.equals(Short.class.toString())) {
			return ValueType.Int16;
		}
		else if (javaWrapper.equals(Integer.class.toString())) {
			return ValueType.Int32;
		}
		else if (javaWrapper.equals(Long.class.toString())) {
			return ValueType.Int64;
		}
		else if (javaWrapper.equals(Float.class.toString())) {
			return ValueType.Float;
		}
		else if (javaWrapper.equals(Double.class.toString())) {
			return ValueType.Double;
		}
		else if (javaWrapper.equals(BigInteger.class.toString())) {
			//Convention: Big Integer is only ever used for as Substitute for UInt64 in JavaRosbridgeClient Message Definitions
			return ValueType.UInt64;
		}
		else if (javaWrapper.equals(Boolean.class.toString())) {
			return ValueType.Boolean;
		}
		else {
			return null;
		}
	}
	
	
}
