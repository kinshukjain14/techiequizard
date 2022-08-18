package com.yash.dao;

import java.util.Optional;

import com.yash.entities.User;
import com.yash.exception.DAOException;
import com.yash.exception.UserAlreadyExistException;

public interface UserDAO
{
	public Optional<User> checkUserCredentials(String userName, String password) throws DAOException;
	public Optional<User> requestUserResponse(int userId) throws DAOException;
	public boolean registerUser(User user) throws DAOException, UserAlreadyExistException;
	public boolean checkContactNumberAvailable(Long contactNo);
	public boolean checkEmailAvailable(String emailId);
	public Optional<User> updateProfile(User user) throws DAOException;
	public boolean updatePassword(User user);	
}
