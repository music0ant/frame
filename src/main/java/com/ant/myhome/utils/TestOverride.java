package com.ant.myhome.utils;

public class TestOverride extends A {
	public TestOverride() {
	}

	public static void f() {
		System.out.println("Method f of TestOverride");
	}

	private void t(){
		System.out.println("Method t of TestOverride");
	}
	public static void main(String args[]) {
		try {
			TestOverride b = new TestOverride();
			b.g();
			A a = new TestOverride();
			a.f();
			((TestOverride) a).f();
			((TestOverride) a).t();
		} catch (RuntimeException e) {
			// TODO: handle exception
		}
	}
	public int solution(int n){
		if(n==0)
			return 0;
		StringBuffer s = new StringBuffer();
        while(n>0){
        	
        	n = n/2;
        }
		
		return Integer.parseInt(s.toString());
		
	}
	
}
