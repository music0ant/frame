package com.ant.myhome.nio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ant.myhome.utils.Print;

public class ServerProvider extends Print{

	public  static void main(String[] args){
		try {
			new ServerProvider().startServer();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@SuppressWarnings({ "unchecked",  "rawtypes", "resource" })
	public void startServer() throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Map<String,Service> serivces = new ConcurrentHashMap<String,Service>();
		serivces.put(SayHelloService.class.getName(), new SayHelloServiceImpl());
		ServerSocket server = new ServerSocket();
		server.bind(new InetSocketAddress("192.168.1.106",1234));
		println("start server");
		while(true){
			Socket socket = server.accept();
			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			RmiDomain rmiDomain = (RmiDomain)input.readObject();
			Class clazz = Class.forName(rmiDomain.getInterfaceName());
			Method method = clazz.getMethod(rmiDomain.getMethodName(), rmiDomain.getParameterTypes());
			Object result = method.invoke(serivces.get(rmiDomain.getInterfaceName()), rmiDomain.getArguments());
			
			ObjectOutputStream output  = new ObjectOutputStream(socket.getOutputStream());
			output.writeObject(result);
		}
	}
}
