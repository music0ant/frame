package com.ant.myhome.nio;

public class SayHelloServiceImpl extends Service implements SayHelloService {

	@Override
	public String sayHello(String hello) {
		if("hello".equals(hello)){
			return "hello";
		}else{
			return "bye bye";
		}
	}

}
