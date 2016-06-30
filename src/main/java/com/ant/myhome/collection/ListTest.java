package com.ant.myhome.collection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class ListTest {

	@Test
	public void removeRepeatitive(){
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("1");
		list.add("12");
		list.add("1");
		list.add("1");
		list.add("2");
		Set<String> set = new HashSet<String>(list);
		for(Iterator<String> i=set.iterator();i.hasNext();){
			System.out.println(i.next());
		}
	}
	
}
