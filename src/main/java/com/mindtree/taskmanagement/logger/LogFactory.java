package com.mindtree.taskmanagement.logger;

/**
 * 
 * @author M1033511
 *
 */
public class LogFactory {

	public static TaskManagementLogger getInstance(){
		return new TaskManagementLoggerImpl();
	}
	
	public static TaskManagementLogger getInstance(String className){
		return new TaskManagementLoggerImpl(className);
	}
	
	public static TaskManagementLogger getInstance(Class clazz){
		return new TaskManagementLoggerImpl(clazz.getCanonicalName());
	}
}
