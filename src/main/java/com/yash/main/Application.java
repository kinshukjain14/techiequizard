package com.yash.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import com.yash.configuration.JDBCSpringSecurityConfig;
import com.yash.configuration.QuizAppConfiguration;

@SpringBootApplication(scanBasePackages="com.yash.*")
@Import({QuizAppConfiguration.class,JDBCSpringSecurityConfig.class})
@PropertySource({"classpath:error.properties"})
public class Application 
{
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
