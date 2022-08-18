package com.yash.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import com.yash.dao.UserDAO;
import com.yash.entities.User;
import com.yash.entities.UserCredentials;
import com.yash.exception.AuthenticationException;
import com.yash.exception.DAOException;
import com.yash.exception.UserAlreadyExistException;
import com.yash.exception.UserRegistrationException;
import com.yash.model.UserRequest;
import com.yash.model.UserResponse;
import com.yash.service.UserServiceImpl;

class TestUserAuthService {

	@Mock
	private UserDAO userDao;
	
	@InjectMocks
	private UserServiceImpl service;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@DisplayName("Test user authentication : Positive")
	@Test
	void testUserAuthentication_positive() {
		String username = "kinshuk.jain14";
		String password = "kinshu123";
		
		try {
			when(userDao.checkUserCredentials(anyString(), anyString())).then((invocation)->{
				User user = new User();
				UserCredentials userCredentials = new UserCredentials();
				userCredentials.setUsername("k");
				userCredentials.setPassword("p");
				user.setUserId(1120);
				user.setFirstName("Kinshuk");
				user.setLastName("Jain");
				user.setAddress("11-B");
				user.setDateOfBirth(LocalDate.now());
				user.setEmail("kinshuk.jain14@gmail.com");
				user.setContactNo(9785039450l);
				user.setGender("M");
				user.setRegisteredOn(LocalDateTime.now());
				user.setLastLogin(LocalDateTime.now());
				user.setCountryId(1);
				user.setStateId(1);
				user.setCityId(1);
				user.setUserCredentials(userCredentials);
				
				return Optional.of(user);
			});
			UserResponse userResponse = service.authenticateUser(username, password);
			if(userResponse.getUserId() != 0) {
				assertTrue(true);
			}
			else {
				assertTrue(false);
			}
		} catch (AuthenticationException | DAOException e) {
			assertTrue(false);
		}
	}

	@DisplayName("Test user authentication : Negative")
	@Test
	void testUserAuthentication_negative() throws DAOException {
		String username = "ashutosh12";
		String password = "123";
		try {
			when(userDao.checkUserCredentials(anyString(), anyString())).then((invocation)->{
				return Optional.empty();
			});
			service.authenticateUser(username, password);
		} catch (AuthenticationException e) {
			assertTrue(true);
		}
	}

	@DisplayName("Test user authentication : Exception")
	@Test
	void testUserAuthentication_exception() throws DAOException {
		String username = "ashutosh12";
		String password = "123";
		
		try {
			when(userDao.checkUserCredentials(anyString(), anyString())).then((invocation)->{
				try {
					throw new RuntimeException();
				}
				catch(RuntimeException e) {
					throw new DAOException(e, "DAO Exception");
				}
			});
			service.authenticateUser(username, password);
			assertTrue(true);
		} catch (AuthenticationException e) {
			assertTrue(false);
		} 
	}
	
	@DisplayName("Test user registration : Positive")
	@Test
	void testAddUserRegistration_Positive() throws DAOException, UserAlreadyExistException {
		
		UserRequest userRequest = new UserRequest();
		userRequest.setUserId(11122);
		userRequest.setFirstName("A");
		userRequest.setLastName("J");
		userRequest.setContactNo(9944321234l);
		userRequest.setEmail("aj@yash.com");
		userRequest.setGender("M");
		userRequest.setDateOfBirth(LocalDate.of(1999, 6, 6));
		userRequest.setAddress("CM-II 230");
		userRequest.setCountry("22");
		userRequest.setState("34");
		userRequest.setCity("32");
		userRequest.setUserName("aj@yash.com");
		userRequest.setPassword("Ashu@123");
		userRequest.setRegisteredOn(LocalDateTime.now());
		userRequest.setLastLogin(LocalDateTime.now());
		
		try {
			when(userDao.registerUser(Mockito.mock(User.class))).thenReturn(true);
			boolean result = service.addUserRegistration(userRequest);
			assertTrue(result);
		} catch (UserRegistrationException | UserAlreadyExistException e) {
			e.printStackTrace();
		}
	}
	
	@DisplayName("Test user registration : Negative")
	@Test
	void testAddUserRegistration_Negative() throws DAOException, UserAlreadyExistException {
		
		UserRequest userRequest = new UserRequest();
		userRequest.setUserId(11122);
		userRequest.setFirstName("A");
		userRequest.setLastName("J");
		userRequest.setContactNo(9944321234l);
		userRequest.setEmail("aj@yash.com");
		userRequest.setGender("M");
		userRequest.setDateOfBirth(LocalDate.of(1999, 6, 6));
		userRequest.setAddress("CM-II 230");
		userRequest.setCountry("22");
		userRequest.setState("34");
		userRequest.setCity("32");
		userRequest.setUserName("aj@yash.com");
		userRequest.setPassword("Ashu@123");
		userRequest.setRegisteredOn(LocalDateTime.now());
		userRequest.setLastLogin(LocalDateTime.now());
		
		try {
			when(userDao.registerUser(Mockito.mock(User.class))).thenReturn(false);
			boolean result = service.addUserRegistration(userRequest);
			assertFalse(result);
		} catch (UserRegistrationException | UserAlreadyExistException e) {
			e.printStackTrace();
		}
	}
	
}
