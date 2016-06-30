package com.ant.myhome.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;

import org.junit.runner.notification.RunListener.ThreadSafe;

@ThreadSafe
//@Immutable
public class VolatileCachedFactorizer {
	//@GuardedBy(this)
	private long value= 0;

	private List<String> list = Collections.synchronizedList(new ArrayList<String>());
	public final CountDownLatch t =null;
	private Semaphore s ;
	
	public interface Computable<A,V>{
		V compute(A arg)throws InterruptedException, ExecutionException;
	}
	
	public class Memoizer<A,V>implements Computable<A,V>{
		private final ConcurrentHashMap<A,Future<V>> cache = new ConcurrentHashMap<A,Future<V>>();
		private final Computable<A,V>c;
		public Memoizer(Computable<A,V> c){
			this.c= c;
		}
		@Override
		public V compute(final A arg) throws InterruptedException, ExecutionException {
			Future<V> f = cache.get(arg);
			if(f==null){
				Callable<V>eval = new Callable<V>(){
					@Override
					public V call() throws Exception {
						return c.compute(arg);
					}
				};
				FutureTask<V>ft = new FutureTask<V>(eval);
				f = ft;
				//cache.put(arg, ft); 
				f =cache.putIfAbsent(arg, ft);
				ft.run();
			}
			return f.get();
		}
		
		
	}
}
