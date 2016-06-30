package com.ant.myhome.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskExecutionWebServer {

	private static final int NTHREADS = 100;
	private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);
	public void testExcecutor() throws IOException{
		ServerSocket socket = new ServerSocket(80);
		while(true){
			final Socket connection = socket.accept();
		    Runnable task = new Runnable(){
				@Override
				public void run() {
					handleRequest(connection);
				}

				private void handleRequest(Socket connection) {
				
					
				}
		    };
		    exec.execute(task);;
		}
	}
}
