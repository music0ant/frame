package com.ant.myhome.nio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

import com.ant.myhome.utils.Print;

public class Client extends Print implements Runnable{

	private static int idleCounter = 0;
	private Selector selector;
	private SocketChannel socketChannel;
	private ByteBuffer temp = ByteBuffer.allocate(1024);
	public  Client() throws IOException{
		this.selector = Selector.open();
		socketChannel = SocketChannel.open();
		Boolean  isConnected = socketChannel.connect(new InetSocketAddress("192.168.1.106",7878));
		socketChannel.configureBlocking(false);
		SelectionKey key = socketChannel.register(selector, SelectionKey.OP_READ);
		if(isConnected){
			socketChannel.write(ByteBuffer.wrap(" connecting".getBytes(Charset.forName("UTF-8"))));
		}else{
			key.interestOps(SelectionKey.OP_CONNECT);
		}
	}
    public void sendMsg() throws UnsupportedEncodingException, IOException{
    	socketChannel.write(ByteBuffer.wrap(" sendMsg writing".getBytes(Charset.forName("UTF-8"))));
    }
	@Override
	public void run() {
		while(true){
			
				int num;
				try {
					num = this.selector.select(1000);
					if(num ==0){
						idleCounter ++;
						if(idleCounter>10){
							try {
								this.sendMsg();
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}finally{
								this.socketChannel.close();
							}
							return;
						}
						continue;
					}else{
						idleCounter = 0;
					}
					
					Set<SelectionKey> keys = this.selector.selectedKeys();
					Iterator<SelectionKey>it = keys.iterator();
					while(it.hasNext()){
						SelectionKey key = it.next();
						it.remove();
						if(key.isConnectable()){
							SocketChannel sc = (SocketChannel)key.channel();
							println("client do connection from server address:"+sc.getRemoteAddress());
							if(sc.isConnectionPending()){
								sc.finishConnect();
							}
							this.sendMsg();
						}else if(key.isReadable()){
							SocketChannel sc = (SocketChannel)key.channel();
							int count  = sc.read(temp);
							if(count<0){
								sc.close();
								continue;
							}
							temp.flip();
							String msg = Charset.forName("UTF-8").decode(temp).toString();
							println("client received ["+msg+"] from server address:"+sc.getRemoteAddress());
							
						    Thread.sleep(1000);
							
							sc.write(ByteBuffer.wrap("result message".getBytes("UTF-8")));
							temp.clear();
						}
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
		}
		
	}

	public  static void main(String[] args)throws IOException{
		Client client = new Client();
		new Thread(client).start();
		Client client1 = new Client();
		new Thread(client1).start();
	}
}
