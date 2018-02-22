package com.mindtree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;

import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


/**
 * This class used for ApacheTomcat Configuraiton of port number and context root path etc..
 * 
 * @author M1033511
 *
 */
@PropertySource("classpath:application.properties")
@Component
public class CustomizationBean implements EmbeddedServletContainerCustomizer {
	@Autowired
	private Environment env;
    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {    	
    	int portNumber = Integer.parseInt(env.getProperty("springboot.port"));
    	String contextPath = env.getProperty("springboot.context_path");
        container.setPort(portNumber);  
        container.setContextPath(contextPath);
    }
}
