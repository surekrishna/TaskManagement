package com.mindtree.taskmanagement.service;

import java.util.List;

import com.mindtree.taskmanagement.domain.Employee;
import com.mindtree.taskmanagement.domain.Project;
import com.mindtree.taskmanagement.domain.TaskManagement;
import com.mindtree.taskmanagement.exceptions.ServiceException;

public interface TaskManagementService {

	public List<Project> getProjects() throws ServiceException;
	public List<Employee> getEmployees(int projectId) throws ServiceException;
	public int saveAssignedEmployees(TaskManagement taskManagement) throws ServiceException;
	public List<Project> getEmployeeProject(String projectName) throws ServiceException;
}
