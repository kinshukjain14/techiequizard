package com.yash.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.yash.controller.UserAuthController;
import com.yash.exception.AuthenticationException;
import com.yash.exception.UserAlreadyExistException;
import com.yash.exception.UserRegistrationException;
import com.yash.main.Application;
import com.yash.model.UserRequest;
import com.yash.model.UserResponse;
import com.yash.model.UserSession;
import com.yash.service.UserService;
import com.yash.validation.UserModelValidator;

@AutoConfigureMockMvc
@WebMvcTest
@ContextConfiguration(classes = { Application.class })
class TestUserAuthController {

	@Mock
	private UserService service;

	@Spy
	private HttpServletRequest request;

	@Mock
	private UserSession userSession;

	@InjectMocks
	@Spy
	private UserModelValidator validator;

	@InjectMocks
	private UserAuthController controller;

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@DisplayName("Test handleUserAuthentication : Positive")
	@Test
	void testHandleUserAuthentication_Positive() throws AuthenticationException {
		when(service.authenticateUser(anyString(), anyString())).then((invocation) -> {
			UserResponse userResponse = new UserResponse();
			userResponse.setUserId(10001);
			return userResponse;
		});
		ResponseEntity<UserResponse> userResponse = controller.handleUserAuthentication("kj@yash.com", "k1234",
				request);
		UserResponse body = userResponse.getBody();
		assertTrue(body.getUserId() != 0);
	}

	@DisplayName("Test handleUserAuthentication : Negative")
	@Test
	void testHandleUserAuthentication_Negative() throws AuthenticationException {
		when(service.authenticateUser(anyString(), anyString())).then((invocation) -> {
			return new UserResponse();
		});
		ResponseEntity<UserResponse> userResponse = controller.handleUserAuthentication("kj@yash.com", "k1234",
				request);
		UserResponse body = userResponse.getBody();
		assertTrue(body.getUserId() == 0);
	}

	@DisplayName("Test User Registration : Positive")
	@Test
	void testHandleUserRegistration_Positive() {
		UserRequest userRequest = new UserRequest();
		userRequest.setUserId(1001);
		userRequest.setFirstName("K");
		userRequest.setLastName("J");
		userRequest.setContactNo(9785039450l);
		userRequest.setEmail("kinshuk.jain@yash.com");
		userRequest.setAddress("11-B Azad nagar");
		userRequest.setGender("male");
		userRequest.setDateOfBirth(LocalDate.of(1999, 06, 14));
		userRequest.setCountry("1");
		userRequest.setState("2");
		userRequest.setCity("1");
		userRequest.setUserName("kinshuk.jain@yash.com");
		userRequest.setPassword("kinshu123");
		userRequest.setRegisteredOn(LocalDateTime.now());
		userRequest.setLastLogin(LocalDateTime.now());

		try {
			Errors errorsMock = Mockito.mock(Errors.class);
			when(service.checkUserContact(anyLong())).thenReturn(false);
			when(service.checkUserEmail(anyString())).thenReturn(false);
			when(errorsMock.hasErrors()).thenReturn(false);
			when(service.addUserRegistration(userRequest)).thenReturn(true);

			ResponseEntity<UserResponse> responseEntity = controller.handleUserRegistration(userRequest, errorsMock);
			UserResponse body = responseEntity.getBody();
			System.out.println(body);
			assertTrue(body.getUserId() != 0);
		} catch (UserRegistrationException | UserAlreadyExistException e) {
			assertTrue(false);
		}

	}

	@DisplayName("Test User Registration : Negative")
	@Test
	void testHandleUserRegistration_Negative() throws UserRegistrationException, UserAlreadyExistException {
		UserRequest userRequest = new UserRequest();
		userRequest.setUserId(1001);
		userRequest.setFirstName("K");
		userRequest.setLastName("J");
		userRequest.setContactNo(9785039450l);
		userRequest.setEmail("kinshuk.jain@yash.com");
		userRequest.setAddress("11-B Azad nagar");
		userRequest.setGender("male");
		userRequest.setDateOfBirth(LocalDate.of(1999, 06, 14));
		userRequest.setCountry("1");
		userRequest.setState("2");
		userRequest.setCity("1");
		userRequest.setUserName("kinshuk.jain@yash.com");
		userRequest.setPassword("kinshu123");
		userRequest.setRegisteredOn(LocalDateTime.now());
		userRequest.setLastLogin(LocalDateTime.now());

		Errors errorsMock = Mockito.mock(Errors.class);
		when(service.checkUserContact(anyLong())).thenReturn(false);
		when(service.checkUserEmail(anyString())).thenReturn(false);
		when(errorsMock.hasErrors()).thenReturn(false);
		when(service.addUserRegistration(userRequest)).then((invocation) -> {
			throw new UserRegistrationException("Unable to register");
		});

		ResponseEntity<UserResponse> responseEntity = controller.handleUserRegistration(userRequest, errorsMock);
		UserResponse body = responseEntity.getBody();
		assertTrue(body.getErrors() != null);
	}

	@DisplayName("Test handleUserAuthentication URI : Positive")
	@Test
	void testHandleUserAuthenticationURI_Positive() throws AuthenticationException {
		when(service.authenticateUser(anyString(), anyString())).then((invocation) -> {
			UserResponse userResponse = new UserResponse();
			userResponse.setUserId(1001);
			userResponse.setUserName("kinshuk.jain@yash.com");
			userResponse.setPassword("kinshu123");
			return userResponse;
		});
		try {
			mockMvc.perform(post("/rest/authenticate?username=kinshuk.jain@yash.com&password=kinshu123"))
					.andExpect(status().isOk()).andReturn();
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@DisplayName("Test handleUserAuthentication URI : Negative")
	@Test
	void testHandleUserAuthenticationURI_Negative() {

		try {
			when(service.authenticateUser(anyString(), anyString())).then((invocation) -> {
				throw new AuthenticationException("Exception");
			});
			mockMvc.perform(post("/rest/authenticate?username=kinshuk.jain@yash.com&password=kinshu123"))
					.andExpect(status().isExpectationFailed()).andReturn();
		} catch (Exception e) {
			assertTrue(false);
		}
	}

}
