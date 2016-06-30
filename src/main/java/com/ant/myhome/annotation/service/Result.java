package com.ant.myhome.annotation.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Result {

	public NameType name()default NameType.SUCCESS;
	public String location()default "";
	public Type type() default Type.JSON;
	public String[] params()default{};
	public enum NameType{
		SUCCESS,ERROR;
	}
	public enum Type{
		JSON,XML;
	}
}
