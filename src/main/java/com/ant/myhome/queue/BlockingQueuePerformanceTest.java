package com.ant.myhome.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Test;

public class BlockingQueuePerformanceTest {

	class Production {
		@SuppressWarnings("unused")
		private String name;
		public Production(String name){
			this.name=name;
		}
	}
	static long endTime = 0L;
	public void testPerformance(final BlockingQueue<Production> queue,final int THREAD_NUM,final int PER_OUTPUT) throws InterruptedException{
		final long startTime = System.currentTimeMillis();
		
		class Productor implements Runnable{
			private String name ;
			
			public Productor(String name) {
				super();
				this.name = name;
			}

			@Override
			public void run() {
				for(int i=0;i<PER_OUTPUT;i++){
					try {
						if((i+1)%1000==0){
							//print(name + " products "+(i+1));
						}
						queue.put(new Production("plane"));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		class ConSumer implements Runnable{
			private String name;

			public ConSumer(String name) {
				super();
				this.name = name;
			}
			@Override
			public void run() {
				for(int i=0;i<PER_OUTPUT;i++){
						try {
							if((i+1)%1000==0){
								//print(name + " consume "+(i+1));
							}
							queue.take();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
				}
				endTime = System.currentTimeMillis();
			}
			
		}
		
		
		List<Thread> threads = new ArrayList<Thread>();
		for(int i=0;i<THREAD_NUM;i++){
			Thread  p =new Thread(new Productor("productor"+i));
			Thread c = new Thread(new ConSumer("ConSumer"+i));
			p.start();
			c.start();
			threads.add(p);
			threads.add(c);
		}
		Thread.sleep(1000*5);
		print(queue.getClass().getSimpleName()+" Thread:"+THREAD_NUM+" outPut:"+(THREAD_NUM*PER_OUTPUT)+" takes "+(endTime-startTime));
	}
	private void print(Object obj){
		System.out.println(obj.toString());
	}
	@Test
	public void showData() {
		try {
			/*this.testPerformance(new ArrayBlockingQueue<Production>(10 * 10000), 10, 10000);
			this.testPerformance(new LinkedBlockingQueue<Production>(10 * 10000), 10, 10000);
			this.testPerformance(new ArrayBlockingQueue<Production>(10 * 10000), 50, 10000);
			this.testPerformance(new LinkedBlockingQueue<Production>(10 * 10000), 50, 10000);
			this.testPerformance(new ArrayBlockingQueue<Production>(10 * 10000),10, 5*10000);
			this.testPerformance(new LinkedBlockingQueue<Production>(10 * 10000), 10, 5*10000);*/
			
			this.testPerformance(new ArrayBlockingQueue<Production>(1 * 10000),10000, 100);
			this.testPerformance(new LinkedBlockingQueue<Production>(1 * 10000), 10000, 100);
			this.testPerformance(new ArrayBlockingQueue<Production>(1 * 10000),100, 10000*10);
			this.testPerformance(new LinkedBlockingQueue<Production>(1 * 10000), 100, 10000*10);
			this.testPerformance(new ArrayBlockingQueue<Production>(1 * 10000),1000, 10000);
			this.testPerformance(new LinkedBlockingQueue<Production>(1 * 10000), 1000, 10000);
			this.testPerformance(new ArrayBlockingQueue<Production>(1000 * 10000),100, 10000*10);
			this.testPerformance(new LinkedBlockingQueue<Production>(1000 * 10000), 100, 10000*10);
			/**
			 ArrayBlockingQueue Thread:10000 outPut:1000000 takes 1903
			LinkedBlockingQueue Thread:10000 outPut:1000000 takes 1752
			ArrayBlockingQueue Thread:100 outPut:10000000 takes 1172
			LinkedBlockingQueue Thread:100 outPut:10000000 takes 2022
			ArrayBlockingQueue Thread:1000 outPut:10000000 takes 1084
			LinkedBlockingQueue Thread:1000 outPut:10000000 takes 1908
			ArrayBlockingQueue Thread:100 outPut:10000000 takes 1178
			LinkedBlockingQueue Thread:100 outPut:10000000 takes 2009
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
