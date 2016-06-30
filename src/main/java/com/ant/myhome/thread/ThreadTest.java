package com.ant.myhome.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;

import com.ant.myhome.utils.Print;

public class ThreadTest extends Print{

	private final AtomicLong count = new AtomicLong(0);
	private volatile boolean flag;
	//@Test
	public void testCount(){
		synchronized(this){
			count.get();
			print(count.incrementAndGet());
		}
		
	}
	 class Thread1 implements Runnable{

		@Override
		public void run() {
			synchronized(this){
				print(this.hashCode());
				for(int i=0;i<5;i++){
					print(Thread.currentThread().getName()+"synchronized loop"+i);
				}
			}
			
		}
		
	}

	class CallableThread implements Callable{

		@Override
		public Object call() throws Exception {
			
			return null;
		}
		
	}
	 @Test
	 public void testSynchronized(){
		 Thread1 t1 = new Thread1();
		 print(t1.hashCode());
		 Thread ta = new Thread(t1,"A");
		 Thread tb = new Thread(t1,"B");
		 print(ta.hashCode());
		 print(tb.hashCode());
		 ta.start();
		 tb.start();
		 CallableThread ct = new CallableThread();
		 FutureTask ft = new FutureTask(ct);
		 Class<?>[] is = ft.getClass().getInterfaces();
		
		 try {
			// print(is[0].newInstance() instanceof Runnable);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		Thread t = new Thread(ft);
	 }
	
}
