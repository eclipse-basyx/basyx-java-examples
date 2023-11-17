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
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyType;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetype.ValueType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;

import com.google.gson.Gson;

//From JavaRosbridgeClient
import java_rosbridge_client.core.utility.RosMessage;
import java_rosbridge_client.core.utility.RosMessageElement;
import java_rosbridge_client.core.utility.RosService;
import java_rosbridge_client.core.utility.RosServiceArgs;
import java_rosbridge_client.core.utility.RosServiceResp;


public class RosToSubmodelConverter {
	
	private static Class<?>[] PRIMITIVE_WRAPPER_CLASSES = {Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Boolean.class, BigInteger.class};

	
	public static <T extends RosMessageElement> SubmodelElementCollection msgToSmc(String idShort, Class<T> msg) {
		SubmodelElementCollection curSmc = new SubmodelElementCollection();
		curSmc.setIdShort(idShort);
		
		
		for (Field field: msg.getDeclaredFields()) {
			if (field.getType().isArray()) {
				Property curProperty = new Property();
				curProperty.setIdShort(field.getName());
				curProperty.setValueType(ValueType.String);
				
				//Using Semantic ID to indicate that it is a JSONString and not a regular one
				Key singleKey = new Key(KeyElements.SUBMODELELEMENTCOLLECTION, false, "JSONString", KeyType.CUSTOM);
				Reference singleRef = new Reference(singleKey);
				curProperty.setSemanticId(singleRef);
				curSmc.addSubmodelElement(curProperty);
			}
			else if (field.getType() == String.class) {
				Property curProperty = new Property();
				curProperty.setIdShort(field.getName());
				curProperty.setValueType(ValueType.String);
				curSmc.addSubmodelElement(curProperty);
			}
			else if (Arrays.asList(PRIMITIVE_WRAPPER_CLASSES).contains(field.getType())) {
				Property curProperty = new Property();
				curProperty.setIdShort(field.getName());
				ValueType curType = BasyxJavaTypeConverter.javaWrapperToBasyxType(field.getType().toString());
				curProperty.setValueType(curType);
				curSmc.addSubmodelElement(curProperty);
			}	
			else {
				//In this case, the field is another message
				String curIdShort = field.getName();
				curSmc.addSubmodelElement(msgToSmc(curIdShort, field.getType().asSubclass(RosMessageElement.class)));
			}
		}
		return curSmc;
	}
	
	public static <T extends RosMessageElement> void msgToSmc(SubmodelElementCollection curSmc, Class<T> msg) {
		
		for (Field field: msg.getDeclaredFields()) {
			
			if (field.getType().isArray()) {
				
				Property curProperty = new Property();
				curProperty.setIdShort(field.getName());
				curProperty.setValueType(ValueType.String);
				
				//Using Semantic ID to indicate that it is a JSONString and not a regular one
				//TODO: Currently crashes the SMC creation, needs to be fixed later for identifying json string
				//was this already fixed? Test with image where JSONString (should) occure seems to run smooth 
				Key singleKey = new Key(KeyElements.PROPERTY, false, "JSONString", KeyType.CUSTOM);
				Reference singleRef = new Reference(singleKey);
				curProperty.setSemanticId(singleRef);
				
				curSmc.addSubmodelElement(curProperty);
			}
			else if (field.getType() == String.class) {
				Property curProperty = new Property();
				curProperty.setIdShort(field.getName());
				curProperty.setValueType(ValueType.String);
				curSmc.addSubmodelElement(curProperty);
			}
			else if (Arrays.asList(PRIMITIVE_WRAPPER_CLASSES).contains(field.getType())) {
				Property curProperty = new Property();
				curProperty.setIdShort(field.getName());
				ValueType curType = BasyxJavaTypeConverter.javaWrapperToBasyxType(field.getType().toString());
				curProperty.setValueType(curType);
				curSmc.addSubmodelElement(curProperty);
			}
			else {
				//In this case, the field is another message
				String curIdShort = field.getName();
				curSmc.addSubmodelElement(msgToSmc(curIdShort, field.getType().asSubclass(RosMessageElement.class)));
			}
		}
	}


	public static <T extends RosMessageElement> void updateSmc(SubmodelElementCollection smc, T msg) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Gson gson = new Gson();
		for (Field field: msg.getClass().getDeclaredFields()) {
			if (field.getType().isArray()) {
				Method getterMethod = msg.getClass().getMethod(retrieveGetterMethodString(field.getName()));
				String jsonString = gson.toJson(getterMethod.invoke(msg));
				smc.getSubmodelElement(field.getName()).setValue(jsonString);
			}
			else if (field.getType() == String.class) {
				Method getterMethod = msg.getClass().getMethod(retrieveGetterMethodString(field.getName()));
				smc.getSubmodelElement(field.getName()).setValue(getterMethod.invoke(msg));
			}
			else if (Arrays.asList(PRIMITIVE_WRAPPER_CLASSES).contains(field.getType())) {
				Method getterMethod = msg.getClass().getMethod(retrieveGetterMethodString(field.getName()));
				smc.getSubmodelElement(field.getName()).setValue(getterMethod.invoke(msg));
			}
			else {
				Method getterMethod = msg.getClass().getMethod(retrieveGetterMethodString(field.getName()));
				SubmodelElementCollection subSmc = (SubmodelElementCollection) smc.getSubmodelElement(field.getName());
				updateSmc(subSmc, (RosMessageElement) getterMethod.invoke(msg));
			}
		}
	}
	
	
	public static <T extends RosMessageElement> T retrieveMsg(SubmodelElementCollection smc, Class<T> msgClass) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException {
		Gson gson = new Gson();

		T msg = msgClass.getConstructor().newInstance();
		
		for (Field field: msgClass.getDeclaredFields()) {
			if (field.getType().isArray()) {
				Method setterMethod = msgClass.getMethod(retrieveSetterMethodString(field.getName()), field.getType());
				Method getterMethod = msgClass.getMethod(retrieveGetterMethodString(field.getName()));
				String jsonString = (String) smc.getSubmodelElement(field.getName()).getValue();
				
				setterMethod.invoke(msg, gson.fromJson(jsonString, getterMethod.getReturnType()));
			}
			else if (field.getType() == String.class) {
				Method setterMethod = msgClass.getMethod(retrieveSetterMethodString(field.getName()), field.getType());
				setterMethod.invoke(msg, smc.getSubmodelElement(field.getName()).getValue());
			}
			else if (Arrays.asList(PRIMITIVE_WRAPPER_CLASSES).contains(field.getType())) {
				Method setterMethod = msgClass.getMethod(retrieveSetterMethodString(field.getName()), field.getType());
				setterMethod.invoke(msg, field.getType().cast(smc.getSubmodelElement(field.getName()).getValue()));

			}
			else {
				Method getterMethod = msgClass.getMethod(retrieveGetterMethodString(field.getName()));
				Method setterMethod = msgClass.getMethod(retrieveSetterMethodString(field.getName()), field.getType());
				SubmodelElementCollection subSmc = (SubmodelElementCollection) smc.getSubmodelElement(field.getName());
				RosMessageElement elem = retrieveMsg(subSmc, (Class<? extends RosMessageElement>) getterMethod.getReturnType());
				setterMethod.invoke(msg, elem);
			}
		}
		return msg;
	}
	
	
	public static <T extends RosMessage> void msgPublisherToSmc(MessagePublisherSMC<T> curSmc, Class<T> msg) {
		RosToSubmodelConverter.msgToSmc((SubmodelElementCollection) curSmc, msg);

		msg.getClass().getMethods();
		Function<Object[], Object> publishFunction = (args) -> {
			try {
				T elem = (T) retrieveMsg(curSmc, msg);
				curSmc.publish(elem);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		};

		Operation publishOperation = new Operation(publishFunction);
		publishOperation.setIdShort("publishSMC");
		
		curSmc.addSubmodelElement(publishOperation);
	}
	
	

	
	
	public static <T extends RosService, U extends RosServiceArgs, V extends RosServiceResp> void srvCallerToSmc(ServiceCallerSMC<T,U,V> curSmc, Class<T> srv, Class<U> srvArgs, Class<V> srvResp) {
		
		Operation op = new Operation();
		SubmodelElementCollection inSmc = msgToSmc("in_args", srvArgs);
		SubmodelElementCollection outSmc = msgToSmc("out_args", srvResp);
		
		Function<Object[], Object> srvCall = (args) -> {
			List inArgsList = (List) args[0];
			SubmodelElementCollection helperSmc = new SubmodelElementCollection();
			helperSmc.setIdShort("Helper");
			for (int i = 0; i < inArgsList.size(); i++) {
				if (inArgsList.get(i).getClass().equals(Property.class)) {
					helperSmc.addSubmodelElement((Property) inArgsList.get(i));
				}
				if (inArgsList.get(i).getClass().equals(SubmodelElementCollection.class)) {
					helperSmc.addSubmodelElement((SubmodelElementCollection) inArgsList.get(i));
				}
			}
			
			SubmodelElementCollection curOutSmc = new SubmodelElementCollection();
			curOutSmc.setIdShort("out_args");

			try {
				U argsClass = (U) retrieveMsg(helperSmc, srvArgs);
				V resp = curSmc.callSrv(argsClass);
				msgToSmc(curOutSmc, srvResp);
				updateSmc(curOutSmc, resp);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException | InstantiationException e) {
				e.printStackTrace();
			}
			
			//get value is necessary, because the basyx sdk "wraps" the result into the output parameter. Also, according to wrapResult/wrapSingleResult,
			//it seems that only one single output is allowed --> everything must be packaged into a single SubmodelElementCollection
			return curOutSmc.getValue();
		};
		
		ArrayList<OperationVariable> inVars = new ArrayList<OperationVariable>();
		inVars.add(new OperationVariable(inSmc));
		ArrayList<OperationVariable> outVars = new ArrayList<OperationVariable>();
		outVars.add(new OperationVariable(outSmc));
		
		//Derive from RosServiceArgs description
		op.setInputVariables(inVars);
		
		//Derive from RosServiceResp description
		op.setOutputVariables(outVars);
		
		op.setInvokable(srvCall);
		op.setIdShort("callService");
				
		curSmc.addSubmodelElement(op);
	}
	
	
	//almost identical to srvCallerToSmc, maybe merge parts into common methods
	public static <T extends RosService, U extends RosServiceArgs, V extends RosServiceResp> void srvPublisherToSmc(ServicePublisherSMC<T,U,V> curPub, Class<T> srv, Class<U> srvArgs, Class<V> srvResp) {
		
		Operation op = new Operation();
		SubmodelElementCollection inSmc = msgToSmc("in_args", srvArgs);
		SubmodelElementCollection outSmc = msgToSmc("out_args", srvResp);
		

		
		Function<Object[], Object> srvCall = (args) -> {
			List inArgsList = (List) args[0];
			SubmodelElementCollection helperSmc = new SubmodelElementCollection();
			helperSmc.setIdShort("Helper");
			for (int i = 0; i < inArgsList.size(); i++) {
				if (inArgsList.get(i).getClass().equals(Property.class)) {
					helperSmc.addSubmodelElement((Property) inArgsList.get(i));
				}
				if (inArgsList.get(i).getClass().equals(SubmodelElementCollection.class)) {
					helperSmc.addSubmodelElement((SubmodelElementCollection) inArgsList.get(i));
				}
			}
			
			SubmodelElementCollection curOutSmc = new SubmodelElementCollection();
			curOutSmc.setIdShort("out_args");

			try {
				U argsClass = (U) retrieveMsg(helperSmc, srvArgs);
				V resp = curPub.respondSrv(argsClass);
				msgToSmc(curOutSmc, srvResp);
				updateSmc(curOutSmc, resp);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException | InstantiationException e) {
				e.printStackTrace();
			}

			//get value is necessary, because the basyx sdk "wraps" the result into the output parameter. Also, according to wrapResult/wrapSingleResult,
			//it seems that only one single output is allowed --> everything must be packaged into a single SubmodelElementCollection
			return curOutSmc.getValue();
		};
		
		ArrayList<OperationVariable> inVars = new ArrayList<OperationVariable>();
		inVars.add(new OperationVariable(inSmc));
		ArrayList<OperationVariable> outVars = new ArrayList<OperationVariable>();
		outVars.add(new OperationVariable(outSmc));
		
		//Derive from RosServiceArgs description
		op.setInputVariables(inVars);
		
		//Derive from RosServiceResp description
		op.setOutputVariables(outVars);
		
		op.setInvokable(srvCall);
		
		op.setIdShort("callService");
		
		curPub.addSubmodelElement(op);
	}
	
	private static String retrieveGetterMethodString(String fieldName) {
		String fieldNameUpperCase = fieldName.substring(0,1).toUpperCase() + fieldName.substring(1,fieldName.length());
		return "get" + fieldNameUpperCase;
		
	}
	
	private static String retrieveSetterMethodString(String fieldName) {
		String fieldNameUpperCase = fieldName.substring(0,1).toUpperCase() + fieldName.substring(1,fieldName.length());
		return "set" + fieldNameUpperCase;
		
	}

}
