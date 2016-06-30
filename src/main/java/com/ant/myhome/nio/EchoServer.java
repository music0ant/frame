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

import org.junit.Test;

import com.ant.myhome.utils.Print;

public class EchoServer {

	public static SelectorLoop connectionBell;
	public static SelectorLoop readBell;
	public boolean isReadBellRuning = false;
	public static void main(String[] args)throws IOException{
		new EchoServer().startServer();
	}
	@Test
	public void startServer()throws IOException{
		connectionBell = new SelectorLoop("connectionBell");
		readBell = new SelectorLoop("readBell");
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.configureBlocking(false);
		ServerSocket socket = ssc.socket();
		socket.bind(new InetSocketAddress("192.168.1.106",7878));
		ssc.register(connectionBell.getSelector(), SelectionKey.OP_ACCEPT);
		new Thread(connectionBell).start();
	}
	
	
	public class SelectorLoop extends Print implements Runnable{

		private Selector selector;
		private ByteBuffer temp = ByteBuffer.allocate(1024);
		private String name;
		public SelectorLoop(String name)throws IOException{
			this.selector = Selector.open();
			this.name= name;
		}
		public Selector getSelector(){
			return this.selector;
		}
		@Override
		public void run() {
			while(true){
				try{
					this.selector.select();
					Set<SelectionKey> selectKeys = this.selector.selectedKeys();
					Iterator<SelectionKey>it = selectKeys.iterator();
					while(it.hasNext()){
						SelectionKey key = it.next();
						it.remove();
					    
						dispatch(key);
						
					}
				}catch(IOException e){
					e.printStackTrace();
				}catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		private void dispatch(SelectionKey key) throws IOException, InterruptedException {
             if(key.isAcceptable()){
            	 ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
            	 SocketChannel sc = ssc.accept();
            	 sc.configureBlocking(false);
            	 sc.register(readBell.getSelector(),SelectionKey.OP_READ);
            	 println("########"+this.name +"do acception ");
            	 synchronized(EchoServer.this){
            		 if(!isReadBellRuning){
            			 isReadBellRuning = true;
            			 new Thread(readBell).start();
            		 }
            	 }
             }
             if(key.isReadable()){
            	 println("########"+this.name +"do reading ");
            	 SocketChannel sc = (SocketChannel)key.channel();
            	 int count = sc.read(temp);
            	 if(count<0){
            		 key.cancel();
            		 sc.close();
            		 return;
            	 }
            	 temp.flip();
            	 String msg = Charset.forName("UTF-8").decode(temp).toString();
            	 this.println("sever received ["+msg+"] from client address:"+sc.getRemoteAddress());
            	 Thread.sleep(1000);
            	 sc.write(ByteBuffer.wrap(msg.getBytes(Charset.forName("UTF-8"))));
            	 temp.clear();
             }
			
		}
		
	}
	
}
