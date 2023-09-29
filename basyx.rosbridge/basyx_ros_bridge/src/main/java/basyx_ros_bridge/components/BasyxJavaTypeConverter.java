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
