package com.ant.myhome.nio;

import java.io.Serializable;

public class RmiDomain implements Serializable{
	private static final long serialVersionUID = -1906228235551091544L;
	private Class<?>[] parameterTypes;
	private String interfaceName;
	private String methodName;
	private Object[] arguments;

	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Object[] getArguments() {
		return arguments;
	}

	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}

}
