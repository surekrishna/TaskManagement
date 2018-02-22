package com.mindtree.taskmanagement.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * This class is used for communication from DAO Layer to Database
 * 
 * @author M1033511
 *
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class DBConfiguration {
	@Autowired
	private Environment env;

	@Bean
	public HibernateTemplate hibernateTemplate() {
		return new HibernateTemplate(sessionFactory());
	}

	/**
	 *  This method is used to return the hibernate sessionFactory 
	 * @return lsfb
	 */
	@Bean
	public SessionFactory sessionFactory() {
		LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
		lsfb.setDataSource(getDataSource());
		lsfb.setPackagesToScan("com.mindtree");
		lsfb.setHibernateProperties(hibernateProperties());
		try {
			lsfb.afterPropertiesSet();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lsfb.getObject();
	}

	/**
	 * This method is used for data base properties 
	 * @return dataSource
	 */
	@Bean
	public DataSource getDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("database.driver"));
		dataSource.setUrl(env.getProperty("database.url"));
		dataSource.setUsername(env.getProperty("database.username"));
		dataSource.setPassword(env.getProperty("database.password"));		
		return dataSource;
	}

	@Bean
	public HibernateTransactionManager hibTransMan() {
		return new HibernateTransactionManager(sessionFactory());
	}
	
	/**
	 * This method is used to load the hibernate properties from the properties file
	 * @return properties
	 */
	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		properties.put("hibernate.jdbc.batch_size", env.getProperty("hibernate.jdbc.batch_size"));
		
		//Setting connection pool (c3p0)  properties 
		properties.put("hibernate.c3p0.min_size", env.getProperty("hibernate.c3p0.min_size"));
		properties.put("hibernate.c3p0.max_size", env.getProperty("hibernate.c3p0.max_size"));
		properties.put("hibernate.c3p0.timeout", env.getProperty("hibernate.c3p0.timeout"));
		properties.put("hibernate.c3p0.max_statements", env.getProperty("hibernate.c3p0.max_statements"));
		
		return properties;
	}
}
