package com.mindtree.taskmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mindtree.taskmanagement.dao.TaskManagementDAO;
import com.mindtree.taskmanagement.dao.impl.TaskManagementDAOImpl;
import com.mindtree.taskmanagement.service.TaskManagementService;
import com.mindtree.taskmanagement.service.impl.TaskManagementServiceImpl;

/**
 * This class is used for creating service and dao impl Objects
 * @author M1033511
 *
 */
@ComponentScan(basePackages = "com.mindtree")
@EnableTransactionManagement
@Configuration
public class TaskManagementConfig {

	@Bean
	public TaskManagementService taskManagementService(){
		return new TaskManagementServiceImpl();
	}
	
	@Bean
	public TaskManagementDAO taskManagementDAO(){
		return new TaskManagementDAOImpl();
	}
}
