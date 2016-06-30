package com.ant.myhome.annotation;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

import com.ant.myhome.annotation.service.Action;
import com.ant.myhome.annotation.service.Controller;
import com.ant.myhome.annotation.service.Result;

public class AnnotationTest {

	@Test
	public void TestAnnotation(){
		try {
			Map<String, Object> actionPool = getAction();
			String requestAction = "PeopleAction";
			String doAction = "gotoList";
			Object[] actionParameters ={1};
			Object action = actionPool.get(requestAction);
			if(action==null){
				return;
			}
			Method[] methods= action.getClass().getMethods();
			for(Method method:methods){
				Action a =method.getAnnotation(Action.class);
				if(a==null||!a.value().equals(doAction)){
					continue;
				}
				Object obj = method.invoke(action, actionParameters);
				//Class<?> reCls = obj.getClass();
				Result[] results = a.results();
				for(Result r:results){
					if(r.name().equals(obj)){
						print(r.location());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void print(Object obj){
		System.out.println(obj.toString());
	}
	private Map<String, Object> getAction() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		Map<String, Object> actionPool = new ConcurrentHashMap<String, Object>();
		//com.ant.myhome.annotation.action
		//String file = AnnotationTest.class.getClass().getResource("G:/workspace/workspace20160410/FirstMaven-webapp/target/classes/").getFile();
		//String file = AnnotationTest.class.getClass().getResource("..").getFile();
		//File filePath = new File(file);
		File files = new File( "G:/workspace/workspace20160410/FirstMaven-webapp/target/classes/com/ant/myhome/annotation/action");
		File[] actions = files.listFiles(new FileFilter() {

			@Override
			public boolean accept(File file) {

				return file.isFile() && file.getName().endsWith(".class");
			}

		});
		List<String> names = new ArrayList<String>();
		for (File action : actions) {
			String fileName = action.getName();
			Class<?> cls = Class.forName("com.ant.myhome.annotation.action."+fileName.replace(".class", ""));
		    Controller c = cls.getAnnotation(Controller.class);
		    if(c!=null){
		    	actionPool.put(c.value(),cls.newInstance());
		    }
			names.add(fileName);
		} 
		return actionPool;
	}
}
