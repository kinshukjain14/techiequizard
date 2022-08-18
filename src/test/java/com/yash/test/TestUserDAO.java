package com.yash.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;

import com.mysql.cj.jdbc.CallableStatement;
import com.yash.dao.JDBCUserDAOImpl;
import com.yash.dao.UserDAO;
import com.yash.entities.User;
import com.yash.exception.DAOException;
import com.yash.integrate.ConnectionManager;
import com.yash.main.Application;

@AutoConfigureMockMvc
@WebMvcTest
@ContextConfiguration(classes=Application.class)
class TestUserDAO 
{
	@Mock
	private ConnectionManager manager;
	
	@Mock
	private Connection connection;
	
	@Mock
	private CallableStatement statement;
	
	@Mock
	private ResultSet resultSet;
	
	@InjectMocks
	private JDBCUserDAOImpl daoImpl;
	
	@Mock
	private UserDAO dao;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@DisplayName("Check user credentials from data source : Positive")
	@Test
	void testUserCredentialsFromDataSource_Positive() throws DAOException, SQLException, ClassNotFoundException {
		String username = "kinshuk.jain@yash.com";
		String password = "kinshu123";
		when(manager.openConnection()).thenReturn(connection);
		when(connection.prepareCall(anyString())).thenReturn(statement);
		when(statement.getInt(3)).thenReturn(12211);
		when(dao.requestUserResponse(anyInt())).then((invocation)->
		{
			User user = new User();
			user.setUserId(11211);
			user.setFirstName("Kinshuk");
			user.setLastName("Jain");
			user.setContactNo(9785039450l);
			user.setEmail("kinshuk.jain@yash.com");
			
			Optional<User> userData = Optional.of(user);
			return userData;
		});		
		Optional<User> check = dao.checkUserCredentials(username, password);
		if(check.isPresent()) {
			assertTrue(true);
		}
		else {
			assertTrue(false);			
		}
	}

	@DisplayName("Check user credentials from data source : Negative")
	@Test
	void testUserCredentialsFromDataSource_Negative() throws SQLException, ClassNotFoundException, DAOException {
		String username = "kinshuk.jain";
		String password = "kinshu123";
		when(dao.requestUserResponse(12211)).then((invocation)->{
			return Optional.empty();
		});
		
		when(manager.openConnection()).thenReturn(connection);
		when(connection.prepareCall(anyString())).thenReturn(statement);
		when(statement.getInt(3)).thenReturn(0);
		Optional<com.yash.entities.User> actual = dao.checkUserCredentials(username, password);
		if(actual.isPresent()) {
			assertTrue(false);
		}
		else {
			assertTrue(true);			
		}
	}

	@DisplayName("Check user credentials from data source : Exception")
	@Test
	void testUserCredentialsFromDataSource_Exception() throws SQLException, ClassNotFoundException {
		try {
		String username = "kinshuk.jain";
		String password = "kinshu123";
		
		when(manager.openConnection()).thenReturn(connection);
		when(connection.prepareCall(anyString())).then((invocable)->{
			throw new RuntimeException();
		});
		dao.checkUserCredentials(username, password);
		}
		catch(DAOException e) {
			assertTrue(true);
		}
	}

	@DisplayName("Check if user data is available in data source or not : Positive")
	@Test
	void testCheckIfUserDataIsAvailableInDataSourceOrNot_Positive() throws DAOException, SQLException, ClassNotFoundException {
		
		when(manager.openConnection()).thenReturn(connection);
		when(connection.prepareCall(anyString())).thenReturn(statement);
		when(statement.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(true);
		when(dao.requestUserResponse(anyInt())).thenAnswer((invocation)->{
			return Optional.of(new User());
		});
		Optional<User> userResponse = dao.requestUserResponse(12331);
		assertTrue(userResponse.isPresent());
	}
	
	@DisplayName("Check if user data is available in data source or not : Negative")
	@Test
	void testCheckIfUserDataIsAvailableInDataSourceOrNot_Negative() throws DAOException, SQLException, ClassNotFoundException {
		when(manager.openConnection()).thenReturn(connection);
		when(connection.prepareCall(anyString())).thenReturn(statement);
		when(statement.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(false);
		Optional<User> userResponse = dao.requestUserResponse(12331);
		assertTrue(!userResponse.isPresent());
	}
	
	@DisplayName("User registration : Positive")
	@Test
	void testRegisterUser_positive() {
		
	}
	
}
