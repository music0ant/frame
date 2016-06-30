package com.ant.myhome.thread;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Renderer {

	private final ExecutorService executor;
	Renderer(ExecutorService executor){
		this.executor= executor;
	}
	void renderPage(CharSequence source){
		final List<String> info = scanForImageInfo(source);
		CompletionService<String> completionService = new ExecutorCompletionService<String>(executor);
		for(final String imageInfo :info){
			completionService.submit(new Callable<String>(){
				@Override
				public String call() throws Exception {
					return imageInfo.toString();
				}
			});
		}
		//renderText(source);
		try{
			for(int t=0,n = info.size();t<n;t++){
				Future<String> f = completionService.take();
				String imageData = f.get();
				renderIamage(imageData);
			}
		}catch(Exception e){
			Thread.currentThread().interrupt();
			//throw LaunderThrowable(e.getCause());
		}
	}
	private void renderIamage(String imageData) {
		// TODO Auto-generated method stub
		
	}
	private List<String> scanForImageInfo(CharSequence source) {
		// TODO Auto-generated method stub
		return null;
	}
	class PrimeProducer extends Thread{
		private final BlockingQueue<BigInteger>queue;
		PrimeProducer(BlockingQueue<BigInteger> queue){
			this.queue= queue;
		}
		public void run(){
			try{
				BigInteger p = BigInteger.ONE;
				while(!Thread.currentThread().isInterrupted()){
					queue.put(p = p.nextProbablePrime());
				}
			}catch(Exception e){
				
			}
		}
		public void cancel(){
			interrupt();
		}
	}
	
	
}
