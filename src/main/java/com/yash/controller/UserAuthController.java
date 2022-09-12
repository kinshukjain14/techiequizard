package com.yash.controller;

import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yash.exception.AuthenticationException;
import com.yash.exception.UserAlreadyExistException;
import com.yash.exception.UserRegistrationException;
import com.yash.model.UserDataErrors;
import com.yash.model.UserRequest;
import com.yash.model.UserResponse;
import com.yash.service.UserService;

@RestController
@RequestMapping("rest")
@CrossOrigin(origins = "*" ,allowedHeaders = "*" ,allowCredentials = "true")
@EnableAspectJAutoProxy
public class UserAuthController {
	@Autowired@Qualifier("userServiceImpl")
	private UserService userService;
	
//	@Autowired
//	private UserSession userSession;
//	
	@Autowired@Qualifier("userModelValidator")
	private Validator validator;
	
	@GetMapping("session")
	public ResponseEntity<Void> handleSessionCheck(HttpServletRequest request)
	{	
		HttpSession session = request.getSession(false);
		if(session == null) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "authenticateuser", produces = {"application/xml"},method = RequestMethod.POST)
	public ResponseEntity<UserResponse> handleUserAuthenticationXML(
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			HttpServletRequest request
			){
		UserResponse userModelResponse=new UserResponse();
		try {
			userModelResponse = userService.authenticateUser(username, password);
//			userSession.setAttribute("userModelResponse", userModelResponse);
			HttpSession session = request.getSession(true);
			session.setAttribute("userModelResponse", userModelResponse);
			return new ResponseEntity<UserResponse>(userModelResponse,HttpStatus.OK);
			
		} catch (AuthenticationException e) {
			return new ResponseEntity<UserResponse>(userModelResponse,HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value="authenticate")
	public ResponseEntity<UserResponse> handleUserAuthentication(
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			HttpServletRequest request) 
	{
		UserResponse userModelResponse=new UserResponse();
		try {
			userModelResponse = userService.authenticateUser(username, password);
//			userSession.setAttribute("userModelResponse", userModelResponse);
			HttpSession session = request.getSession();
			session.setAttribute("userModelResponse", userModelResponse);
			System.out.println("Login Session : "+session);
			System.out.println("Login Session : "+session.getId());
			return new ResponseEntity<UserResponse>(userModelResponse,HttpStatus.OK);
		} catch (AuthenticationException e) {
			
			return new ResponseEntity<UserResponse>(userModelResponse,HttpStatus.EXPECTATION_FAILED);
		}
	}
	
//	@RequestMapping(value = "authuser" ,method=RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = "authuser")
	public ResponseEntity<UserResponse> handleUserRegistration(
			@RequestBody UserRequest userRequest,
			Errors errors)
	{
//		System.out.println(userRequest);
//		System.out.println("Method called");
		ResponseEntity<UserResponse> responseEntity=null;
		userRequest.setRegisteredOn(LocalDateTime.now());
		userRequest.setLastLogin(LocalDateTime.now());		
		ValidationUtils.invokeValidator(validator, userRequest, errors);
		UserResponse userResponse = new UserResponse();
		
		if(errors.hasErrors())
		{
//			System.out.println("Has errors");
			FieldError fieldError = errors.getFieldError();
			UserDataErrors dataErrors = new UserDataErrors();
			dataErrors.setErrorCode(fieldError.getCode());
			dataErrors.setErrorDescription(fieldError.getDefaultMessage());
			userResponse.setErrors(dataErrors);
			responseEntity = new ResponseEntity<UserResponse>(userResponse,HttpStatus.ALREADY_REPORTED);
		}
		else {
			try {
				boolean result = userService.addUserRegistration(userRequest);
				if(result) {
					userResponse.setUserId(userRequest.getUserId());
					userResponse.setFirstName(userRequest.getFirstName());
					userResponse.setLastName(userRequest.getLastName());
					userResponse.setEmail(userRequest.getEmail());
					userResponse.setContactNo(userRequest.getContactNo());
					userResponse.setGender(userRequest.getGender());
					userResponse.setDateOfBirth(userRequest.getDateOfBirth());
					userResponse.setRegisteredOn(userRequest.getRegisteredOn());
					userResponse.setLastLogin(userRequest.getLastLogin());
					userResponse.setAddress(userRequest.getAddress());
					userResponse.setCountry(userRequest.getCountry());
					userResponse.setState(userRequest.getState());
					userResponse.setCity(userRequest.getCity());
					responseEntity= new ResponseEntity<UserResponse>(userResponse,HttpStatus.CREATED);
					System.out.println("Status code : "+responseEntity.getStatusCodeValue());
				}
			} catch (UserRegistrationException | UserAlreadyExistException e) {
				UserDataErrors dataErrors = new UserDataErrors();
				e.printStackTrace();
				dataErrors.setErrorCode("808");
				dataErrors.setErrorDescription("Something Went Wrong");
				userResponse.setErrors(dataErrors);
				responseEntity= new ResponseEntity<UserResponse>(userResponse,HttpStatus.ALREADY_REPORTED);
				System.out.println("Status code : "+responseEntity.getStatusCodeValue());
			}
			catch(Exception e) {
				e.printStackTrace();
				responseEntity= new ResponseEntity<UserResponse>(userResponse,HttpStatus.ALREADY_REPORTED);
				System.out.println("Status code : "+responseEntity.getStatusCodeValue());
			}
		}
//		System.out.println("Status code : "+responseEntity.getStatusCodeValue());
		return responseEntity;
	}
	
	@DeleteMapping("authenticate")
	public ResponseEntity<Void> destroySession(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		System.out.println("Logout Session : "+session);
		System.out.println("Logout Session : "+session.getId());
		if(session!=null)
			session.invalidate();
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PutMapping("update")
	public ResponseEntity<UserResponse> handleUserProfileUpdate(@RequestBody UserRequest userRequest,Errors errors,HttpServletRequest request){
		HttpSession session = request.getSession(false);
		UserResponse user = (UserResponse) session.getAttribute("userModelResponse");
//		UserResponse user = (UserResponse) userSession.getAttribute("userModelResponse");
		UserRequest userModel = new UserRequest();
		userModel.setUserId(user.getUserId());
		userModel.setFirstName(userRequest.getFirstName());
		userModel.setLastName(userRequest.getLastName());
		userModel.setContactNo(userRequest.getContactNo());
		userModel.setEmail(userRequest.getEmail());
		userModel.setGender(userRequest.getGender());
		userModel.setDateOfBirth(userRequest.getDateOfBirth());
		userModel.setAddress(userRequest.getAddress());
		userModel.setCountry(userRequest.getCountry());
		userModel.setState(userRequest.getState());
		userModel.setCity(userRequest.getCity());
		userModel.setPassword(userRequest.getPassword());
		userModel.setLastLogin(user.getLastLogin());
		userModel.setRegisteredOn(user.getRegisteredOn());
		UserResponse userResponse = new UserResponse();
//		ValidationUtils.invokeValidator(validator, userRequest, errors);
//		if(errors.hasErrors()) 
//		{
//			FieldError fieldError = errors.getFieldError();
//			UserDataErrors dataErrors = new UserDataErrors();
//			dataErrors.setErrorCode(fieldError.getCode());
//			dataErrors.setErrorDescription(fieldError.getDefaultMessage());
//			userResponse.setErrors(dataErrors);
//			return new ResponseEntity<UserResponse>(userResponse,HttpStatus.CONFLICT);
//		}
		userResponse = userService.updateUserProfile(userModel);
		if(userResponse.getUserId()!=0) {
			session.setAttribute("userModelResponse", userResponse);
//			userSession.setAttribute("userModelResponse", userResponse);
			return new ResponseEntity<UserResponse>(userResponse,HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<UserResponse>(userResponse,HttpStatus.NOT_MODIFIED);
	}
	
	
}
