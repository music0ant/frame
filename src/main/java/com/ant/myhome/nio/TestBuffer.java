package com.ant.myhome.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

import org.junit.Test;

import com.ant.myhome.utils.Print;

public class TestBuffer extends Print{

	//@Test
	public void testIntBuffer(){
		IntBuffer buffer =  IntBuffer.allocate(8);
		for(int i=0;i<8;i++){
			buffer.put(2*(i+1));
		}
		buffer.flip();
		while(buffer.hasRemaining()){
			print(buffer.get());
		}
	}
	@Test
	public void testChannel(){
		FileInputStream file =null;
		try {
			 file = new FileInputStream("g:\\t_student_info.sql");
			FileChannel channel = file.getChannel();
			ByteBuffer  buffer = ByteBuffer.allocate(1024);
			buffer = ByteBuffer.allocateDirect(1024);
			
			channel.read(buffer);
            buffer.flip();
            while(buffer.hasRemaining()){
            	print(buffer.get());
            }
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(file!=null){
				try {
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	protected Selector getSelector() throws IOException{
		Selector sel = Selector.open();
		ServerSocketChannel server = ServerSocketChannel.open();
		server.configureBlocking(false);
		ServerSocket socket = server.socket();
		InetSocketAddress address = new InetSocketAddress(8080);
		socket.bind(address);
		server.register(sel, SelectionKey.OP_ACCEPT);
		//SelectionKey.OP_CONNECT;
		//SelectionKey.OP_READ;
		//SelectionKey.OP_WRITE
		return sel;
	}
}
