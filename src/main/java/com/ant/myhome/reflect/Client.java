package com.ant.myhome.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.ant.myhome.reflect.domain.Subject;
import com.ant.myhome.reflect.domain.SubjectImpl;
import com.ant.myhome.utils.Print;

public class Client extends Print{

	public static void main(String[] args){
		Subject rs = new SubjectImpl();
		InvocationHandler ds = new DynamicSubject(rs);
		Class<?>cls = rs.getClass();
		Subject subject = (Subject)Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), ds);
		println(subject instanceof Proxy);
		cls = subject.getClass();
		Field[] fields = subject.getClass().getDeclaredFields();
		println("############# declaredField");
		for(Field field:fields){
			println(field.getName());
		}
		fields = cls.getFields();
		println("#############   field");
		for(Field field:fields){
			println(field.getName());
		}
		Method[] methods = cls.getDeclaredMethods();
		println("#############  declaredMethod");
		for(Method method:methods){
			println(method.getName());
		}
		methods = cls.getMethods();
		println("#############  Method");
		for(Method method:methods){
			println(method.getName());
		}
		Class<?>[] interfaces= cls.getInterfaces();
		println("#############  Method");
		for(Class inter:interfaces){
			println(inter.getName());
		}
		subject.request();
	}
}
