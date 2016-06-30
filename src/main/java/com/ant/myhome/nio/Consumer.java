package com.ant.myhome.nio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.UnknownHostException;


import com.ant.myhome.utils.Print;

public class Consumer extends Print{

	public  static void main(String[] args){
	
			try {
				new Consumer().sendMsg();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	public void sendMsg() throws NoSuchMethodException, SecurityException, UnknownHostException, IOException, ClassNotFoundException{
		
		String interfaceName = SayHelloService.class.getName();
		Method method = SayHelloService.class.getMethod("sayHello", String.class);
		method.getParameterTypes();
		Object[] arguments = {"hello"};
		RmiDomain rmiDomain = new RmiDomain();
		rmiDomain.setInterfaceName(interfaceName);
		rmiDomain.setMethodName(method.getName());
		rmiDomain.setParameterTypes(method.getParameterTypes());
		rmiDomain.setArguments(arguments);
		@SuppressWarnings("resource")
		Socket socket = new Socket("192.168.1.106",1234);
		ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
		output.writeObject(rmiDomain);
		ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
		Object result = input.readObject();
		println(result.toString());
	}
}
