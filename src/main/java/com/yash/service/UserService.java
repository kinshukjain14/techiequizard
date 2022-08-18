package com.yash.service;

import com.yash.exception.AuthenticationException;
import com.yash.exception.UserAlreadyExistException;
import com.yash.exception.UserRegistrationException;
import com.yash.model.UserRequest;
import com.yash.model.UserResponse;

public interface UserService {
	public UserResponse authenticateUser(String userName,String password) throws AuthenticationException;
	public boolean addUserRegistration(UserRequest user) throws UserRegistrationException, UserAlreadyExistException;
	public boolean checkUserEmail(String email);
	public boolean checkUserContact(Long contactNo);
	public UserResponse updateUserProfile(UserRequest userRequest);
	public boolean updatePassword(UserRequest userRequest);
}
