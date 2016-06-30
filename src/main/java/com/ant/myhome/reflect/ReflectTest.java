package com.ant.myhome.reflect;

import java.lang.reflect.Field;

import org.junit.Test;

import com.ant.myhome.reflect.domain.ReflectDomain;

public class ReflectTest {

	@Test
	public void testGetFields() throws InstantiationException, IllegalAccessException{
		try {
			Field[] fields = ReflectDomain.class.getDeclaredFields();
			ReflectDomain domain = ReflectDomain.class.newInstance();
			Field.setAccessible(fields, true);
			print(fields[0].getName());
			fields[0].set(domain, 2);
			print(domain.getAge());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void print(Object obj){
		System.out.println(obj.toString());
	}
}
