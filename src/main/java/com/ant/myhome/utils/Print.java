package com.ant.myhome.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class Print {

	
	public static void println(Object obj){
		System.out.println(obj.toString());
	}
	public static void print(Object obj){
		System.out.print(obj.toString());
	}
	
	public int solution(int n){
		//sdfds
		String str = Integer.toBinaryString(n);
		int longestGap = 0;
		while(true){
			int indexLast = str.lastIndexOf("1");
			if(indexLast==0){
				break;
			}
			str = str.substring(0, indexLast);
			int temp = indexLast - str.lastIndexOf("1")-1;
			if(temp>longestGap){
				longestGap = temp;
			}
		}
		return longestGap;
	}

	@Test
	public void test(){
		int[] ints = {9,529,20,15,1041,1111111111};
		for(int i:ints){
			solution(i);
		}
	}
	@Test 
	public void testq(){
		int[] ints = {9,9,9,3,9,3,9,7,9,8};
		println(solution(null,1));
		
	}
	public int solution(int[] A,int n){
		if(A==null ||A.length==0){
			return 0;
		}
		int len = A.length;
		Map<Integer,Integer> pairedMap = new HashMap<Integer,Integer>();
		int unpaired = 0;
		for(int i=0;i<len;i++){
			boolean unPariedFlog = true;
			int iNum = A[i];
			if(pairedMap.get(i)!=null){
				continue;
			}
			for(int j=i+1;j<len;j++){
				int jNum = A[j];
				if(iNum ==jNum){
					pairedMap.put(j, i);
					System.out.println("indexes "+i+" and "+j +" have "+iNum);
					unPariedFlog =false;
					break;
				}
				
			}
			if(unPariedFlog){
				unpaired = iNum;
			}
		}
		return unpaired;
	}
}
