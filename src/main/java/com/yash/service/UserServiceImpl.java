package com.yash.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yash.dao.UserDAO;
import com.yash.entities.Authorities;
import com.yash.entities.User;
import com.yash.entities.UserCredentials;
import com.yash.exception.AuthenticationException;
import com.yash.exception.DAOException;
import com.yash.exception.UserAlreadyExistException;
import com.yash.exception.UserRegistrationException;
import com.yash.model.UserRequest;
import com.yash.model.UserResponse;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

//	@Autowired@Qualifier("springJDBCUserDAOImpl")
//	@Autowired@Qualifier("jDBCUserDAOImpl")
	@Autowired@Qualifier("hibernateUserDAOImpl")
	private UserDAO userDAO;
	
	public UserServiceImpl() {
		super();
	}

	@Override
	public UserResponse authenticateUser(String userName, String password) throws AuthenticationException {
		UserResponse model = new UserResponse();
			try {
				Optional<User> userData = userDAO.checkUserCredentials(userName, password);
				if(userData.isPresent()) {
					User user = userData.get();
					model.setFirstName(user.getFirstName());
					model.setLastName(user.getLastName());
					model.setContactNo(user.getContactNo());
					model.setEmail(user.getEmail());
					model.setAddress(user.getAddress());
					model.setDateOfBirth(user.getDateOfBirth());
					model.setCountry(user.getCountryId().toString());
					model.setState(user.getStateId().toString());
					model.setCity(user.getCityId().toString());
					model.setUserName(user.getUserCredentials().getUsername());
					model.setPassword(user.getUserCredentials().getPassword());
					model.setUserId(user.getUserId());
					model.setGender(user.getGender());
					model.setRegisteredOn(user.getRegisteredOn());
					model.setLastLogin(user.getLastLogin());
				}
				else
					throw new AuthenticationException("== Invalid username or password ==");
			} catch (DAOException e) {
				
			}
		return model;
	}

	
	@Override
	public boolean addUserRegistration(UserRequest usermodel) throws UserRegistrationException, UserAlreadyExistException {
		try {
			int userId = (int)(Math.random()*10000000);
			usermodel.setUserId(userId);
			int countryId = 0;
			if(usermodel.getCountry()!=null) {
				countryId = Integer.parseInt(usermodel.getCountry());
			}
			int stateId = 0;
			if(usermodel.getState()!=null) {
				stateId = Integer.parseInt(usermodel.getState());
			}
			int cityId = 0;
			if(usermodel.getCity()!=null) {
				cityId = Integer.parseInt(usermodel.getCity());
			}	
			
			User user = new User();
			UserCredentials userCredentials = new UserCredentials();
			Authorities auths = new Authorities();
			auths.setAuthority("ROLE_CANDIDATE");
			auths.setUsername(usermodel.getUserName());

			userCredentials.setAuthorities(auths);
			userCredentials.setUserId(usermodel.getUserId());
			userCredentials.setUsername(usermodel.getUserName());
			userCredentials.setPassword(usermodel.getPassword());
			userCredentials.setActiveStatus(1);
			
			user.setUserCredentials(userCredentials);
			user.setUserId(usermodel.getUserId());
			user.setFirstName(usermodel.getFirstName());
			user.setLastName(usermodel.getLastName());
			user.setEmail(usermodel.getEmail());
			user.setContactNo(usermodel.getContactNo());
			user.setGender(usermodel.getGender());
			user.setDateOfBirth(usermodel.getDateOfBirth());
			user.setAddress(usermodel.getAddress());
			user.setCountryId(countryId);
			user.setStateId(stateId);
			user.setCityId(cityId);
			user.setRegisteredOn(usermodel.getRegisteredOn());
			user.setLastLogin(usermodel.getLastLogin());
			boolean registerUser = userDAO.registerUser(user);
			System.out.println(registerUser);
			return registerUser;
		} catch (DAOException e) 
		{
			e.printStackTrace();
			throw new UserRegistrationException("Unable to register user data");
		} catch (UserAlreadyExistException e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public boolean checkUserEmail(String email) {
		return userDAO.checkEmailAvailable(email);
	
	}

	@Override
	public boolean checkUserContact(Long contactNo) {
		return userDAO.checkContactNumberAvailable(contactNo);
	}

	@Override
	public UserResponse updateUserProfile(UserRequest userRequest) {
		User user = new User();
		UserCredentials userCredentials = new UserCredentials();
		Authorities auths = new Authorities();
		int countryId = 0;
		if(userRequest.getCountry()!=null) {
			countryId = Integer.parseInt(userRequest.getCountry());
		}
		int stateId = 0;
		if(userRequest.getState()!=null) {
			stateId = Integer.parseInt(userRequest.getState());
		}
		int cityId = 0;
		if(userRequest.getCity()!=null) {
			cityId = Integer.parseInt(userRequest.getCity());
		}	
		
		user.setUserId(userRequest.getUserId());		
		user.setFirstName(userRequest.getFirstName());
		user.setLastName(userRequest.getLastName());
		user.setContactNo(userRequest.getContactNo());
		user.setEmail(userRequest.getEmail());
		user.setGender(userRequest.getGender());
		user.setDateOfBirth(userRequest.getDateOfBirth());
		user.setAddress(userRequest.getAddress());
		user.setCountryId(countryId);
		user.setStateId(stateId);
		user.setCityId(cityId);
		user.setRegisteredOn(userRequest.getRegisteredOn());
		user.setLastLogin(userRequest.getLastLogin());
		auths.setUsername(userRequest.getEmail());
		userCredentials.setAuthorities(auths);
		userCredentials.setUserId(userRequest.getUserId());
		userCredentials.setUsername(userRequest.getEmail());
		userCredentials.setPassword(userRequest.getPassword());
		
		user.setUserCredentials(userCredentials);
		
		Optional<User> updateProfile = Optional.empty();
		UserResponse userResponse = new UserResponse();
		try {
			updateProfile = userDAO.updateProfile(user);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		if(updateProfile.isPresent()) 
		{
			User updatedUser = updateProfile.get();
			userResponse.setUserId(updatedUser.getUserId());
			userResponse.setUserName(updatedUser.getUserCredentials().getUsername());
			userResponse.setPassword(updatedUser.getUserCredentials().getPassword());
			userResponse.setFirstName(updatedUser.getFirstName());
			userResponse.setLastName(updatedUser.getLastName());
			userResponse.setContactNo(updatedUser.getContactNo());
			userResponse.setEmail(updatedUser.getEmail());
			userResponse.setGender(updatedUser.getGender());
			userResponse.setAddress(updatedUser.getAddress());
			userResponse.setDateOfBirth(updatedUser.getDateOfBirth());
			userResponse.setCountry(updatedUser.getCountryId().toString());
			userResponse.setState(updatedUser.getStateId().toString());
			userResponse.setCity(updatedUser.getCityId().toString());
			userResponse.setLastLogin(updatedUser.getLastLogin());
			userResponse.setRegisteredOn(updatedUser.getRegisteredOn());
		}
		return userResponse;
	}

	@Override
	public boolean updatePassword(UserRequest userRequest) {
		return false;
	}

}
