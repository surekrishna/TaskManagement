package com.mindtree.taskmanagement.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindtree.taskmanagement.dao.TaskManagementDAO;
import com.mindtree.taskmanagement.domain.Employee;
import com.mindtree.taskmanagement.domain.Project;
import com.mindtree.taskmanagement.domain.TaskManagement;
import com.mindtree.taskmanagement.exceptions.DAOException;
import com.mindtree.taskmanagement.exceptions.ServiceException;
import com.mindtree.taskmanagement.logger.LogFactory;
import com.mindtree.taskmanagement.logger.TaskManagementLogger;
import com.mindtree.taskmanagement.service.TaskManagementService;

/**
 * This class is used for the Service Layer operations
 * @author M1033511
 *
 */
@Service
@Transactional
public class TaskManagementServiceImpl implements TaskManagementService {

	private static final TaskManagementLogger LOGGER = LogFactory.getInstance(TaskManagementServiceImpl.class);
	
	@Autowired
	private TaskManagementDAO taskManagementDAO;
	
	/**
	 * This method is used to load all Projects
	 */
	@Override
	public List<Project> getProjects() throws ServiceException {
		final long startTime = LOGGER.logMethodEntry();
		List<Project> projectList;
		try {
			projectList =  (List<Project>) taskManagementDAO.getProjects();
		} catch (DAOException e) {
			LOGGER.error("Exception while loading All Projects :: "+e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		LOGGER.logMethodExit(startTime);
		return projectList;
	}

	/**
	 * This method is used for load Employees for particular Project
	 */
	@Override
	public List<Employee> getEmployees(int projectId) throws ServiceException {
		final long startTime = LOGGER.logMethodEntry();
		List<Employee> employeeList = null;
		try {
			employeeList = taskManagementDAO.getEmployees(projectId);
		} catch (DAOException e) {
			LOGGER.error("Exception loading Employees data :: "+e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		LOGGER.logMethodExit(startTime);
		return employeeList;
	}

	/**
	 * This method is used to save assigned employees data for particular Project
	 */
	@Override
	public int saveAssignedEmployees(TaskManagement taskManagement) throws ServiceException {
		final long startTime = LOGGER.logMethodEntry();
		int rowsCount = 0;
		try {
			rowsCount = taskManagementDAO.saveAssignedEmployees(taskManagement);
		} catch (DAOException e) {
			LOGGER.error("Exception when saving assigned Employees data :: "+e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		LOGGER.logMethodExit(startTime);
		return rowsCount;
	}

	/**
	 * This method is used to load Employees and Project Data
	 */
	@Override
	public List<Project> getEmployeeProject(String projectName) throws ServiceException {
		final long startTime = LOGGER.logMethodEntry();
		List<Project> project;
		try{
			project = taskManagementDAO.getEmployeeProject(projectName);
		}catch(DAOException e){
			LOGGER.error("Exception Loading Employees with respect to Project :: "+e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		LOGGER.logMethodExit(startTime);
		return project;
	}

}
