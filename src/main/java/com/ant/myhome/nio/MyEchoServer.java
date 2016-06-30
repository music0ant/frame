package com.ant.myhome.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

import com.ant.myhome.utils.Print;

public class MyEchoServer {

	private static SelectorLoop readBell;
	private static SelectorLoop connectionBell;
	private   boolean readBellRunning = false;
	
	public  static void main(String[] args){
		try {
			new MyEchoServer().startServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startServer() throws IOException{
		connectionBell = new SelectorLoop("##########connectionBell");
		readBell = new SelectorLoop("#########readBell");
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.configureBlocking(false);
		ServerSocket socket = ssc.socket();
		socket.bind(new InetSocketAddress("192.168.1.106",7878));
		ssc.register(connectionBell.selector, SelectionKey.OP_ACCEPT);
		new Thread(connectionBell).start();
	}
	
	
	public class SelectorLoop extends Print implements Runnable{
		private ByteBuffer cache ;
		private Selector selector;
		private String name;
		
		public SelectorLoop(String name){
			this.name = name;
			cache = ByteBuffer.allocate(1024);
			try {
				selector = Selector.open();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		@Override
		public void run() {
			while(true){
				try {
					this.selector.select();
				   Set<SelectionKey> keys = selector.selectedKeys();
				   Iterator<SelectionKey> it = keys.iterator();
				   while(it.hasNext()){
					   dispatch(it.next());
					   it.remove();
				   }
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		public void dispatch(SelectionKey key) throws IOException {
			if(key.isAcceptable()){
			  ServerSocketChannel ssc=(ServerSocketChannel)	key.channel();
			  SocketChannel sc = ssc.accept();
			  sc.configureBlocking(false);
			  println(Thread.currentThread().getName()+" : "+name +"   accepting ");
			  sc.register(readBell.selector,SelectionKey.OP_READ);
			  if(readBellRunning){
				  return;
			  }
			  synchronized(MyEchoServer.this){
				  if(!readBellRunning){
					  readBellRunning = true;
					  new Thread(readBell).start();
				  }
			  }
			}else if(key.isReadable()){
				SocketChannel sc = (SocketChannel)key.channel();
				int read =0;
				try {
					read = sc.read(cache);
				} catch (IOException e) {
					key.cancel();
					sc.close();
					e.printStackTrace();
					return;
				}
				if(read<0){
					key.cancel();
					sc.close();
					return;
				}
				cache.flip();
				String msg = Charset.forName("UTF-8").decode(cache).toString();
				println(Thread.currentThread().getName()+" : "+name +"  "+msg+" reading");
				sc.write(ByteBuffer.wrap(" read result".getBytes(Charset.forName("UTF-8"))));
				cache.clear();
			}
			
		}
		
		
	}
	
}
