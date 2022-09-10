package com.yash.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import com.yash.integrate.DataSource;

@Configuration
@ComponentScan(basePackages = "com.yash.*")
@PropertySource({"classpath:/db.properties","classpath:/sql.properties"})
public class QuizAppConfiguration {
	@Autowired
	private DataSource dataSource;
	
	@Bean 
	public DriverManagerDataSource driverManagerDataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setDriverClassName(dataSource.getDriver());
		driverManagerDataSource.setUrl(dataSource.getUrl());
		driverManagerDataSource.setUsername(dataSource.getUserName());
		driverManagerDataSource.setPassword(dataSource.getPassword());
		return driverManagerDataSource;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate() {	
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(driverManagerDataSource());
		return jdbcTemplate;
	}
	
	@Bean("sessionFactory")
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setPackagesToScan(new String[] {"com.yash.entities"});
		sessionFactory.setDataSource(driverManagerDataSource());
		return sessionFactory;
	}
	
}
