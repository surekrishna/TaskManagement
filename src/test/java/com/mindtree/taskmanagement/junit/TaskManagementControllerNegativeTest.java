/**
 * 
 */
package com.mindtree.taskmanagement.junit;

import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.mindtree.taskmanagement.config.DBConfiguration;
import com.mindtree.taskmanagement.config.TaskManagementConfig;
import com.mindtree.taskmanagement.config.WebApplicationConfig;
import com.mindtree.taskmanagement.constants.ApplicationConstants;
import com.mindtree.taskmanagement.logger.LogFactory;
import com.mindtree.taskmanagement.logger.TaskManagementLogger;

/**
 * This class is used to test the controller layer with negative scenarios
 * 
 * @author M1033511
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {DBConfiguration.class, TaskManagementConfig.class,WebApplicationConfig.class})
@Transactional
public class TaskManagementControllerNegativeTest {

	private static final TaskManagementLogger LOGGER = LogFactory.getInstance(TaskManagementControllerNegativeTest.class);

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webappContext;
	
	@Autowired 
	MockHttpServletRequest request;
	
	@Autowired
	MockServletContext context;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webappContext).build();
	}

	/**
	 * Test method for {@link com.mindtree.taskmanagement.controller.TaskManagementController#loadWelcomePage()}.
	 * @throws Exception 
	 */
	@Test
	public void testLoadWelcomePage() throws Exception {
		MvcResult welcomePageResult = this.mockMvc.perform(get("/")).andExpect(view().name(ApplicationConstants.HOME)).andReturn();
		assertNotNull(welcomePageResult.getModelAndView());
		assertNotSame(ApplicationConstants.ASSIGN_TASK,welcomePageResult.getModelAndView());
		LOGGER.info("testLoadWelcomePage() View Name :: "+welcomePageResult.getModelAndView());
	}

	/**
	 * Test method for {@link com.mindtree.taskmanagement.controller.TaskManagementController#loadAssignTaskPage()}.
	 * @throws Exception 
	 */
	@Test
	public void testLoadAssignTaskPage() throws Exception {
		this.mockMvc.perform(get("/"+ApplicationConstants.ASSIGN_TASK)).andExpect(status().isOk()).andExpect(view().name(ApplicationConstants.ASSIGN_TASK))
			   .andExpect(model().attributeExists("projects")).andExpect(model().attributeDoesNotExist("project"))
			   .andExpect(model().hasNoErrors()).andExpect(model().attribute("project", nullValue()));
	}

	/**
	 * Test method for {@link com.mindtree.taskmanagement.controller.TaskManagementController#assignTask(com.mindtree.taskmanagement.domain.TaskManagement)}.
	 * @throws Exception 
	 */
	@Test
	@Rollback(false)
	public void testAssignTask() throws Exception {
		String[] employeeIds = new String[]{"E0000000","E0000001","E0000002"};
		MvcResult assignTaskResult = this.mockMvc.perform(post("/"+ApplicationConstants.ASSIGN_TASK)
				.param("projectName", "Windows app UI")
				.param("description", "Junit Test")
				.param("startDate", "01-10-2017")
				.param("endDate", "01-12-2017")
				.param("employeeList", employeeIds)).andExpect(model().hasNoErrors()).andExpect(model().attribute("rowsCount", "0 Employees Successfully assigned to Project")).andReturn();
		assertNotNull(assignTaskResult.getModelAndView());
		assertNotSame(ApplicationConstants.TASK_VIEW,assignTaskResult.getModelAndView());
		LOGGER.info("testAssignTask() View Name :: "+assignTaskResult.getModelAndView());
	}

	/**
	 * Test method for {@link com.mindtree.taskmanagement.controller.TaskManagementController#loadTaskViewPage()}.
	 * @throws Exception 
	 */
	@Test
	public void testLoadTaskViewPage() throws Exception {
		MvcResult taskViewPageResult = this.mockMvc.perform(get("/"+ApplicationConstants.TASK_VIEW)).andExpect(status().isOk()).andExpect(view().name(ApplicationConstants.TASK_VIEW))
		.andExpect(model().attributeExists("projects")).andExpect(model().attributeDoesNotExist("project"))
		   .andExpect(model().hasNoErrors()).andExpect(model().attribute("project", nullValue())).andReturn();
		assertNotNull(taskViewPageResult.getModelAndView());
		assertNotSame(ApplicationConstants.HOME,taskViewPageResult.getModelAndView());
		LOGGER.info("testLoadTaskViewPage() View Name :: "+taskViewPageResult.getModelAndView());
	}

	/**
	 * Test method for {@link com.mindtree.taskmanagement.controller.TaskManagementController#loadEmployees(int)}.
	 * @throws Exception 
	 */
	@Test
	public void testLoadEmployees() throws Exception {
		MvcResult employees = mockMvc.perform(get("/"+ApplicationConstants.LOAD_EMPLOYEES+"/{projectId}",10).accept(MediaType.APPLICATION_JSON)).andReturn();
		assertNotSame("",employees.getResponse().getContentAsString());
		assertFalse(employees.getResponse().getContentAsString().length() > 2);
		LOGGER.info("Load No Employees on Project ID :: "+employees.getResponse().getContentAsString());
	}

	/**
	 * Test method for {@link com.mindtree.taskmanagement.controller.TaskManagementController#loadProjectEmployees(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testLoadProjectEmployees() throws Exception {
		MvcResult projects = mockMvc.perform(get("/"+ApplicationConstants.LOAD_PROJECT_EMPLOYEE+"/{projectName}","RGA").accept(MediaType.APPLICATION_JSON)).andReturn();
		assertNotSame("",projects.getResponse().getContentAsString());
		assertFalse(projects.getResponse().getContentAsString().length() > 2);
		//assertNotNull(projects.getResponse().getContentAsString());
		LOGGER.info("Load No Projects and No Employees on Project Name :: "+projects.getResponse().getContentAsString());
	}

}
