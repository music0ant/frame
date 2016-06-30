package com.ant.myhome.reflect.domain;

import com.ant.myhome.utils.Print;

public class SubjectImpl extends Print implements Subject{

	@Override
	public void request() {
		println(" do with request");
		
	}

}
