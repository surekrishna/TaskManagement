package com.mindtree.taskmanagement.dao.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.mindtree.taskmanagement.constants.QueryConstants;
import com.mindtree.taskmanagement.dao.TaskManagementDAO;
import com.mindtree.taskmanagement.domain.Employee;
import com.mindtree.taskmanagement.domain.Project;
import com.mindtree.taskmanagement.domain.TaskManagement;
import com.mindtree.taskmanagement.exceptions.DAOException;
import com.mindtree.taskmanagement.logger.LogFactory;
import com.mindtree.taskmanagement.logger.TaskManagementLogger;

/**
 * This class is used for DAO Layer operations
 * @author M1033511
 *
 */
@Repository
@Transactional
public class TaskManagementDAOImpl implements TaskManagementDAO {

	private static final TaskManagementLogger LOGGER = LogFactory.getInstance(TaskManagementDAOImpl.class);
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * This method is used to load all Project Data from Database
	 */
	@Override
	public List<Project> getProjects() throws DAOException {
		final long startTime = LOGGER.logMethodEntry();
		List<Project> projects = null;
		
		try {
			projects = (List<Project>) hibernateTemplate.loadAll(Project.class);
		} catch (DataAccessException e) {
			LOGGER.error("Exception while loading All Projects data from DB :: "+e.getMessage());
			throw new DAOException();
		}
		LOGGER.logMethodExit(startTime);
		return projects;
	}

	/**
	 * This method is used to load Employees of particular Project
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> getEmployees(int projectId) throws DAOException {
		final long startTime = LOGGER.logMethodEntry();
		List<Employee> employeeList = null;
		try {
			employeeList = (List<Employee>) hibernateTemplate.find(QueryConstants.LOAD_EMPLOYEES,projectId);
		} catch (DataAccessException e) {
			LOGGER.error("Exception loading Employee data from DB :: "+e.getMessage());
			throw new DAOException();
		}
		LOGGER.logMethodExit(startTime);
		return employeeList;
	}

	/**
	 * This method is used to save employees data assigned to a project
	 */
	@Override
	public int saveAssignedEmployees(TaskManagement taskManagement) throws DAOException {
		final long startTime = LOGGER.logMethodEntry();
		int updateRow = 0,rowsCount = 0;
		Object[] args = null;
		try {
			for(String employeeId : taskManagement.getEmployeeList()){
				args = new Object[]{taskManagement.getDescription(),taskManagement.getStartDate(),taskManagement.getEndDate(),employeeId};
				updateRow = hibernateTemplate.bulkUpdate(QueryConstants.BULK_UPDATE_EMPLOYEE,args);
				rowsCount = rowsCount + updateRow;
			}
			LOGGER.info("Rows updated :: "+rowsCount);
		} catch (DataAccessException e) {
			LOGGER.error("Exception when saving assigned Employees data to DB :: "+e.getMessage());
			throw new DAOException();
		}
		LOGGER.logMethodExit(startTime);
		return rowsCount;
	}

	/**
	 * This method is used to load Employees data assigned to a different Projects 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Project> getEmployeeProject(String projectName) throws DAOException {
		final long startTime = LOGGER.logMethodEntry();
		List<Project> project;
		Iterator<Employee> empIterator = null;
		Iterator<Project> prjIterator;
		Set<Employee> empSet;
		try{
			if("ALL".equalsIgnoreCase(projectName)){
				project = (List<Project>) hibernateTemplate.loadAll(Project.class);
				prjIterator = project.iterator();
				while(prjIterator.hasNext()){
					Project prj = prjIterator.next();
					empSet = prj.getEmployeeSet();
					if(empSet.isEmpty()){
						prjIterator.remove();
					}else{
						removeObject(empIterator,empSet);
					}
				}
			}else{
				project = (List<Project>) hibernateTemplate.find(QueryConstants.ASSIGNED_PROJECT_EMPLOYEES, projectName);
				if(!project.isEmpty() && project.size() == 1){
					Project project1 = project.get(0);
					empSet = project1.getEmployeeSet();
					removeObject(empIterator,empSet);
				}
			}
		}catch(DataAccessException e){
			LOGGER.error("Exception Loading Employees with respect to Project data from DB :: "+e.getMessage());
			throw new DAOException();
		}
		LOGGER.logMethodExit(startTime);
		return project;
	}
	
	/**
	 * This method is used for removing empty objects while iterating
	 * @param empIterator
	 * @param empSet
	 */
	private void removeObject(Iterator<Employee> empIterator,Set<Employee> empSet){
		empIterator = empSet.iterator();
		while(empIterator.hasNext()){
			Employee emp = empIterator.next();
			if(null == emp.getDescription() && null == emp.getStartDate() && null == emp.getEndDate()){
				empIterator.remove();
			}
		}
	}

}
