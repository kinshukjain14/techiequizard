package com.yash.integrate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConnectionManager {
	
	@Autowired
	private DataSource dataSource;
	
	private Connection connection=null;
	
	public ConnectionManager() 
	{
		
	}
	
	public Connection openConnection() throws ClassNotFoundException, SQLException {
		Class.forName(dataSource.getDriver());
		connection=DriverManager.getConnection(dataSource.getUrl(),dataSource.getUserName(),dataSource.getPassword());
		return connection;
	}

	public void closeConnection() throws SQLException {
		connection.close();
	}
}
