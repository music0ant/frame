package com.ant.myhome.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.ant.myhome.utils.Print;

public class DynamicSubject extends Print implements InvocationHandler {

	private Object obj ;
	public DynamicSubject(Object obj){
		this.obj = obj;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	
		println(" before calling "+ method.getName());
		Object result = method.invoke(obj, args);
		println(" after calling "+ method.getName());
		
		return result;
	}

}
