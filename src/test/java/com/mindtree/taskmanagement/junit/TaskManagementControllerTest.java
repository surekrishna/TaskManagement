/**
 * 
 */
package com.mindtree.taskmanagement.junit;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
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
 * This class is use to test the controller layer using Junit.
 *  
 * @author M1033511
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {DBConfiguration.class, TaskManagementConfig.class,WebApplicationConfig.class})
@Transactional
public class TaskManagementControllerTest {
	
	private static final TaskManagementLogger LOGGER = LogFactory.getInstance(TaskManagementControllerTest.class);

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
		this.mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name(ApplicationConstants.HOME));
	}

	/**
	 * Test method for {@link com.mindtree.taskmanagement.controller.TaskManagementController#loadAssignTaskPage()}.
	 * @throws Exception 
	 */
	@Test
	public void testLoadAssignTaskPage() throws Exception {
		this.mockMvc.perform(get("/"+ApplicationConstants.ASSIGN_TASK)).andExpect(status().isOk()).andExpect(view().name(ApplicationConstants.ASSIGN_TASK))
			   .andExpect(model().attributeExists("projects")).andExpect(model().attribute("projects", hasSize(3)));
	}

	/**
	 * Test method for {@link com.mindtree.taskmanagement.controller.TaskManagementController#assignTask(com.mindtree.taskmanagement.domain.TaskManagement)}.
	 * @throws Exception 
	 */
	@Test
	@Rollback(false)
	public void testAssignTask() throws Exception {
		String[] employeeIds = new String[]{"M1033624","M1036367","M1223246"};
		this.mockMvc.perform(post("/"+ApplicationConstants.ASSIGN_TASK)
				.param("projectName", "Windows app UI")
				.param("description", "Junit Test")
				.param("startDate", "01-10-2107")
				.param("endDate", "01-12-2107")
				.param("employeeList", employeeIds))
				.andExpect(status().isOk()).andExpect(view().name(ApplicationConstants.HOME));
	}

	/**
	 * Test method for {@link com.mindtree.taskmanagement.controller.TaskManagementController#loadTaskViewPage()}.
	 * @throws Exception 
	 */
	@Test
	public void testLoadTaskViewPage() throws Exception {
		this.mockMvc.perform(get("/"+ApplicationConstants.TASK_VIEW)).andExpect(status().isOk()).andExpect(view().name(ApplicationConstants.TASK_VIEW))
		   .andExpect(model().attributeExists("projects")).andExpect(model().attribute("projects", hasSize(3)));
	}

	/**
	 * Test method for {@link com.mindtree.taskmanagement.controller.TaskManagementController#loadEmployees(int)}.
	 * @throws Exception 
	 */
	@Test
	public void testLoadEmployees() throws Exception {
		MvcResult employees = this.mockMvc.perform(get("/"+ApplicationConstants.LOAD_EMPLOYEES+"/{projectId}",2).accept(MediaType.APPLICATION_JSON)).andReturn();
		assertNotNull(employees.getResponse().getContentAsString());
		LOGGER.info("Load Employees on Project ID :: "+employees.getResponse().getContentAsString());
	}

	/**
	 * Test method for {@link com.mindtree.taskmanagement.controller.TaskManagementController#loadProjectEmployees(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testLoadProjectEmployees() throws Exception {
		MvcResult projects = this.mockMvc.perform(get("/"+ApplicationConstants.LOAD_PROJECT_EMPLOYEE+"/{projectName}","ALL").accept(MediaType.APPLICATION_JSON)).andReturn();
		assertNotNull(projects.getResponse().getContentAsString());
		LOGGER.info("Load Projects and Employees on Project Name :: "+projects.getResponse().getContentAsString());
	}

}
