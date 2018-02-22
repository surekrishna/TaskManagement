package com.mindtree.taskmanagement.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mindtree.taskmanagement.constants.ApplicationConstants;
import com.mindtree.taskmanagement.domain.Employee;
import com.mindtree.taskmanagement.domain.Project;
import com.mindtree.taskmanagement.domain.TaskManagement;
import com.mindtree.taskmanagement.exceptions.ServiceException;
import com.mindtree.taskmanagement.logger.LogFactory;
import com.mindtree.taskmanagement.logger.TaskManagementLogger;
import com.mindtree.taskmanagement.service.TaskManagementService;

/**
 * This Controller is used for communication from view navigating to different requests of methods
 * @author M1033511
 *
 */
@Controller
public class TaskManagementController {

	private static final TaskManagementLogger LOGGER = LogFactory.getInstance(TaskManagementController.class);
	
	@Autowired
	private TaskManagementService taskManagementService;
	
	/** 
	 * This method is used to load the home page
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/")
	public ModelAndView loadWelcomePage(){
		return new ModelAndView(ApplicationConstants.HOME);
	}

	/**
	 * This method is used to load assigntask page, To assign a task to multiple employees assigntask page required
	 * @return ModelAndView
	 */
	@RequestMapping(value = ApplicationConstants.ASSIGN_TASK, method = RequestMethod.GET)
	public ModelAndView loadAssignTaskPage(){
		ModelAndView model = new ModelAndView(ApplicationConstants.ASSIGN_TASK);
		List<Project> projects = null;
		try {
			projects = taskManagementService.getProjects();
			model.addObject("taskAssigned", new TaskManagement());
			model.addObject("projects",projects);
		} catch (ServiceException e) {
			LOGGER.error("Exception while loading All Projects :: "+e.getMessage());
		}
		return model;
	}
	
	/**
	 * This method is used for to submit the data of employees who are assigned to particular project
	 * @param taskManagement
	 * @return ModelAndView
	 */
	@RequestMapping(value = ApplicationConstants.ASSIGN_TASK, method = RequestMethod.POST)
	public ModelAndView assignTask(@Valid @ModelAttribute("taskAssigned")TaskManagement taskManagement,BindingResult result){
		ModelAndView model = null;
		List<Project> projects = null;
		int rowsCount = 0;
		try {
			if(result.hasErrors()){
				model = new ModelAndView(ApplicationConstants.ASSIGN_TASK);
				projects = taskManagementService.getProjects();
				model.addObject("projects",projects);
				if(!taskManagement.getProjectName().isEmpty())
					model.addObject("projectStatus",true);
			}else{
				rowsCount = taskManagementService.saveAssignedEmployees(taskManagement);
				model = new ModelAndView(ApplicationConstants.HOME);
				model.addObject("rowsCount", rowsCount+" Employees Successfully assigned to Project");
			}
		} catch (ServiceException e) {
			LOGGER.error("Exception when saving Employees :: "+e.getMessage());
		}
	    return model;
	}
	
	/**
	 * This method is used to load the taskview page
	 * @return ModelAndView
	 */
	@RequestMapping(value = ApplicationConstants.TASK_VIEW, method = RequestMethod.GET)
	public ModelAndView loadTaskViewPage(){
		ModelAndView model = new ModelAndView(ApplicationConstants.TASK_VIEW);
		List<Project> projects = null;
		try {
			projects = taskManagementService.getProjects();
			model.addObject("projects",projects);
		} catch (ServiceException e) {
			LOGGER.error("Exception loading Projects :: "+e.getMessage());
		}
		return model;
	}
	
	/**
	 * This method is used to load the employees when select any project in assigntask page
	 * @param projectId
	 * @return List<Employee>
	 */
	@RequestMapping(value = ApplicationConstants.LOAD_EMPLOYEES+"/{projectId}")
	public @ResponseBody List<Employee> loadEmployees(@PathVariable("projectId") int projectId){
		final long startTime = LOGGER.logMethodEntry();
		List<Employee> employeeList = null;
		try {
			employeeList = taskManagementService.getEmployees(projectId);
		} catch (ServiceException e) {
			LOGGER.error("Exception loading Employees :: "+e.getMessage());
		}
		LOGGER.logMethodExit(startTime);
		return employeeList;
	}
	
	/**
	 * This method is used to load the Employees who are assigned to particular project, can see either single or All Projects data
	 * @param projectName
	 * @return List<Employee>
	 */
	@RequestMapping(value = ApplicationConstants.LOAD_PROJECT_EMPLOYEE+"/{projectName}")
	public @ResponseBody List<Project> loadProjectEmployees(@PathVariable("projectName") String projectName){
		final long startTime = LOGGER.logMethodEntry();
		List<Project> projects = null;
		try{
			projects = taskManagementService.getEmployeeProject(projectName);
		}catch(ServiceException e){
			LOGGER.error("Exception Loading Employees with respect to Project  :: "+e.getMessage());
		}
		LOGGER.logMethodExit(startTime);
		return projects;
	}
	
}
