package com.mindtree.taskmanagement.dao;

import java.util.List;

import com.mindtree.taskmanagement.domain.Employee;
import com.mindtree.taskmanagement.domain.Project;
import com.mindtree.taskmanagement.domain.TaskManagement;
import com.mindtree.taskmanagement.exceptions.DAOException;

public interface TaskManagementDAO {

	public List<Project> getProjects() throws DAOException;
	public List<Employee> getEmployees(int projectId) throws DAOException;
	public int saveAssignedEmployees(TaskManagement taskManagement) throws DAOException;
	public List<Project> getEmployeeProject(String projectName) throws DAOException;
}
