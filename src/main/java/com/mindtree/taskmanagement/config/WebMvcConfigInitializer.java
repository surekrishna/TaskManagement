package com.mindtree.taskmanagement.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mindtree.taskmanagement.logger.LogFactory;
import com.mindtree.taskmanagement.logger.TaskManagementLogger;

/**
 * This class is created for starting the dispatcher servlet
 * @author M1033511
 *
 */
@Configuration
@EnableWebMvc
@PropertySource("classpath:application.properties")
@EnableScheduling
@EnableAsync
public class WebMvcConfigInitializer implements WebApplicationInitializer {
	private static final TaskManagementLogger LOGGER = LogFactory.getInstance(WebMvcConfigInitializer.class);
	@Override
	public void onStartup(ServletContext container) {
		// Create the 'root' Spring application context
		LOGGER.debug("Started  configuring  dispatcher servlet");
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(DBConfiguration.class);
		rootContext.register(TaskManagementConfig.class);
		/*rootContext.register(ApplicationConfig.class);
    	rootContext.register(PolicyHubConfig.class);
        rootContext.register(TailorWellConfig.class);*/		

		// Manage the lifecycle of the root application context
		container.addListener(new ContextLoaderListener(rootContext));

		// Create the dispatcher servlet's Spring application context
		AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
		dispatcherContext.register(WebApplicationConfig.class);

		// Register and map the dispatcher servlet
		ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher",
				new DispatcherServlet(dispatcherContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
		LOGGER.debug("Dispatcher servlet configured successfully");
	}

}
