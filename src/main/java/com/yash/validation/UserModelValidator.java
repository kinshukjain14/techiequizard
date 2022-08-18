package com.yash.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.yash.integrate.ErrorSource;
import com.yash.model.UserRequest;
import com.yash.service.UserService;

@Component("userModelValidator")
public class UserModelValidator implements Validator{
	
	@Autowired
	private ErrorSource errorSource;
	
	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(UserRequest.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		UserRequest model = (UserRequest) target;
		if(userService.checkUserEmail(model.getEmail())) {
			errors.rejectValue("email","email.error",errorSource.getEmailError());
			
		}
		if(userService.checkUserContact(model.getContactNo())) {
			errors.rejectValue("contactNo","contactNo.error",errorSource.getContactNoError());
		}
	}

}
