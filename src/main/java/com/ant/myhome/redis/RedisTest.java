package com.ant.myhome.redis;

import java.util.List;

import org.junit.Test;

import com.ant.myhome.utils.Print;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

public class RedisTest extends Print{
	private static Jedis jedis = null;
	static {
		jedis = new Jedis("192.168.1.116",6379);
	}
    //@Test
	public void  testWatch(){
		String value = jedis.get("116");
		jedis.set("WATCHKEY", "1");
		print(value);
	}
	@Test
	public void testTranscation(){
		try {
			String lockKey = "WATCHKEY";
			jedis.watch(lockKey);
			String value = jedis.get(lockKey);
			Transaction tx = jedis.multi();
			//Response<String> $116 = tx.get("116");
			tx.set("116", " update by 118");
			tx.set(lockKey, String.valueOf(Integer.parseInt(value) + 1));
			List<Object> result = tx.exec();
			if (result == null){
				print("fail");
				return ;
			}
				
			print("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//@Test
	public void testExtends(){
		A a = new B();
		a.test();
	}
	class A extends Print{
		private int a =0;
		public A() {
			a =2;
		}

		public void test(){
			print("A");
		}
	}
	class B extends A{
		
		public void test(){
			print("B");
		}
	}
}
