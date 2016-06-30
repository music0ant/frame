package com.ant.myhome.thread;

import org.junit.Test;

import com.ant.myhome.utils.Print;

public class ThreadStatus extends Print{

	class TStatus implements Runnable{

		private long count =0;
		private String name ;
		public TStatus(String name){
			this.name= name;
		}
		@Override
		public void run() {
			while(!Thread.currentThread().isInterrupted()){
				count++;
			}
		}
		public void cancle(){
			
		}
	}
	class ThreadS extends Thread{
		private long count =0;
		private String name ;
		private Thread ct = null;
		private Print p;
		public ThreadS(String name){
			
			this.name= name;
			p = new ThreadStatus();
		}
		public void run(){
			ct =Thread.currentThread();
			p.print(ct.getState());
		/*	while(!Thread.currentThread().isInterrupted()){
				count++;
				//System.out.print(count+" ");
			}*/
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void printStatus(){
			ct =Thread.currentThread();
			if(ct!=null){
				p.print(ct.getState());
			}else{
				p.print("Thread is not null ");
			}
		}
		public void cancle(){
			try {
				p.print("before interrupt status:"+ct.getState());
				this.interrupt();
				p.print("after interrupt status:"+ct.getState());
				Thread.sleep(1000);
				p.print("after interrupt 1000ms status:"+ct.getState());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	@Test
	public void testStatus() {
		try {
			ThreadS t = new ThreadS("myThread");
			t.printStatus();
			print("after bulid");
			t.start();
			Thread.sleep(100);
			print("after start");
			synchronized(t){
				try {
					t.wait();
				} catch (Exception e) {
					e.printStackTrace();
				}
				print("after wait");
				t.printStatus();
				try {
					t.notify();
				} catch (Exception e) {
					e.printStackTrace();
				}
				print("after notify");
			}
			t.printStatus();
			t.sleep(300);
			print("after sleep");
			t.printStatus();
			Thread.sleep(300);
			t.cancle();
			Thread.sleep(2000);
			print(t.getState());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
