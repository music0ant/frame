package com.ant.myhome.annotation.action;

import com.ant.myhome.annotation.service.Action;
import com.ant.myhome.annotation.service.Controller;
import com.ant.myhome.annotation.service.Result;
import com.ant.myhome.annotation.service.Result.NameType;
@Controller("PeopleAction")
public class PeopleAction {

	@Action(value="gotoList", results = { 
			@Result(location = "/List.jsp", name =Result.NameType.SUCCESS, params = { "root","domain" }, type = Result.Type.JSON), 
			@Result(location = "/error.jsp", name =Result.NameType.ERROR, params = { "root","domain" }, type = Result.Type.JSON) })
	public NameType gotoList(int type){
		if(type==0){
			return NameType.ERROR;
		}else{
			return NameType.SUCCESS;
		}
		
	}
	@Action(results = { @Result(name=Result.NameType.SUCCESS) }, value = "update")
	public Result.NameType update(){
		return Result.NameType.SUCCESS;
	}
	public void delete(){
		
	}
}
