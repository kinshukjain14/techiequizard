package com.yash.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yash.entities.Authorities;
import com.yash.entities.User;
import com.yash.entities.UserCredentials;
import com.yash.exception.DAOException;
import com.yash.exception.UserAlreadyExistException;
import com.yash.helper.UserAuthSQLQueries;
import com.yash.integrate.ConnectionManager;

@Repository("jDBCUserDAOImpl")
public class JDBCUserDAOImpl implements UserDAO {

	private static int userId;

	@Autowired
	private ConnectionManager connectionManager;
	@Autowired
	private UserAuthSQLQueries userAuthSQLQueries;
	
	@Override
	public Optional<User> checkUserCredentials(String userName, String password) throws DAOException {
		Optional<User> userResponse=Optional.empty();
		try(
				Connection connection = connectionManager.openConnection();
				)
		{
			CallableStatement statement = connection.prepareCall(userAuthSQLQueries.getGetUserIdProcedure());
			statement.setString(1, userName);
			statement.setString(2, password);
			statement.registerOutParameter(3, Types.INTEGER);
			statement.execute();
			userId=statement.getInt(3);
			userResponse = requestUserResponse(userId);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new DAOException(e,"DAO Exception : username or password does not exist");
		}
	return userResponse;
	}

	@Override
	public Optional<User> requestUserResponse(int userId) throws DAOException {
		Optional<User> userData=Optional.empty();
		try(
				Connection connection = connectionManager.openConnection();
				){
			CallableStatement statement = connection.prepareCall(userAuthSQLQueries.getGetUserDetailsByUserIdProcedure());
			statement.setInt(1, userId);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				java.sql.Date dob = resultSet.getDate("date_of_birth");
				Timestamp registeredDate = resultSet.getTimestamp("registered_on");
				Timestamp lastLoginDate = resultSet.getTimestamp("last_login");
				LocalDate dateOfBirth = LocalDate.now();
				LocalDateTime registeredOn = LocalDateTime.now();
				LocalDateTime lastLogin = LocalDateTime.now();
				if(dob!=null) {
					dateOfBirth = dob.toLocalDate();
				}
				if(registeredDate!=null) {
					registeredOn = registeredDate.toLocalDateTime();
				}
				if(lastLoginDate!=null) {
					lastLogin = lastLoginDate.toLocalDateTime();
				}
				
				User user = new User();
				UserCredentials userCredentials = new UserCredentials();
				Authorities auths = new Authorities();
				
				auths.setUsername(resultSet.getString("username"));
				auths.setAuthority(resultSet.getString("authority"));
				
				userCredentials.setAuthorities(auths);
				userCredentials.setUserId(resultSet.getInt("user_id"));
				userCredentials.setUsername(resultSet.getString("username"));
				userCredentials.setPassword(resultSet.getString("password"));
				
				user.setUserCredentials(userCredentials);
				user.setUserId(resultSet.getInt("user_id"));
				user.setFirstName(resultSet.getString("first_name"));
				user.setLastName(resultSet.getString("last_name"));
				user.setEmail(resultSet.getString("email"));
				user.setContactNo(resultSet.getLong("contact"));
				user.setGender(resultSet.getString("gender"));
				user.setDateOfBirth(dateOfBirth);
				user.setAddress(resultSet.getString("address"));
				user.setCountryId(resultSet.getInt("country_id"));
				user.setStateId(resultSet.getInt("state_id"));
				user.setCityId(resultSet.getInt("city_id"));
				user.setRegisteredOn(registeredOn);
				user.setLastLogin(lastLogin);
				userData=Optional.of(user);
			}
			
		} catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
			throw new DAOException(e,"DAO Exception : username or password does not exist");
		}
		return userData;
	}

	@Override
	public boolean registerUser(User user) throws DAOException, UserAlreadyExistException {
		try(
				Connection connection = connectionManager.openConnection();
				){
			connection.setAutoCommit(false);
			CallableStatement call = connection.prepareCall(userAuthSQLQueries.getCheckUserExist());
			call.setString(1, user.getEmail());
			call.setLong(2, user.getContactNo());
			ResultSet resultSet = call.executeQuery();
			if(resultSet.next()) {
				throw new UserAlreadyExistException("Email or Contact number already exist");
			}
			PreparedStatement statement1=connection.prepareStatement(userAuthSQLQueries.getInsertUserDetails());
			PreparedStatement statement2=connection.prepareStatement(userAuthSQLQueries.getInsertUserCredentials());
			PreparedStatement statement3=connection.prepareStatement(userAuthSQLQueries.getInsertUserAuthorities());
			
			UserCredentials userCredentials = user.getUserCredentials();
			statement1.setInt(1, user.getUserId());
			statement1.setString(2, user.getFirstName());
			statement1.setString(3, user.getLastName());
			statement1.setString(4, user.getEmail());
			statement1.setLong(5, user.getContactNo());
			statement1.setTimestamp(6, java.sql.Timestamp.valueOf(user.getRegisteredOn()));
			statement1.setTimestamp(7, java.sql.Timestamp.valueOf(user.getLastLogin()));
			statement1.setString(8, user.getGender());
			statement1.setDate(9, java.sql.Date.valueOf(user.getDateOfBirth()));
			statement1.setString(10, user.getAddress());
			statement1.setInt(11, user.getCountryId());
			statement1.setInt(12, user.getStateId());
			statement1.setInt(13, user.getCityId());
			
			int rows1=statement1.executeUpdate();
			
			statement2.setInt(1,user.getUserId());
			statement2.setString(2, userCredentials.getUsername());
			statement2.setString(3, userCredentials.getPassword());
			statement2.setInt(4, userCredentials.getActiveStatus());
			
			int rows2 = statement2.executeUpdate();
			
			statement3.setString(1, userCredentials.getAuthorities().getUsername());
			statement3.setString(2, userCredentials.getAuthorities().getAuthority());
			
			int rows3 = statement3.executeUpdate();
			
			if(rows1>0 && rows2>0 && rows3>0) {
				connection.commit();
				return true;
			}
			connection.rollback();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new DAOException(e, "DAO Exception : unable to add record");
		}
		return false;
	}

	@Override
	public boolean checkContactNumberAvailable(Long contactNo) {
		try(
				Connection connection = connectionManager.openConnection();
				){
			CallableStatement statement = connection.prepareCall(userAuthSQLQueries.getCheckContactExist());
			statement.setLong(1, contactNo);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				return true;
			}
		}
		catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkEmailAvailable(String emailId) {
		try(
				Connection connection = connectionManager.openConnection();
				){
			CallableStatement statement = connection.prepareCall(userAuthSQLQueries.getCheckEmailExist());
			statement.setString(1, emailId);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				return true;
			}
		}
		catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Optional<User> updateProfile(User user) throws DAOException {
		Optional<User> userResponse =Optional.empty();
		try(Connection connection = connectionManager.openConnection()){
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(userAuthSQLQueries.getUpdateUserProfile());
			PreparedStatement statement2 = connection.prepareStatement(userAuthSQLQueries.getUpdateUserName());
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setString(3, user.getEmail());
			statement.setLong(4, user.getContactNo());
			statement.setString(5, user.getGender());
			statement.setDate(6, java.sql.Date.valueOf(user.getDateOfBirth()));
			statement.setString(7, user.getAddress());
			statement.setInt(8, user.getCountryId());
			statement.setInt(9, user.getStateId());
			statement.setInt(10, user.getCityId());
			statement.setInt(11, user.getUserId());
			
			int update1 = statement.executeUpdate();
			
			statement2.setString(1, user.getEmail());
			statement2.setInt(2, user.getUserId());
			int update2 = statement2.executeUpdate();
			
			if(update1>0 && update2>0) {
				connection.commit();
				userResponse = requestUserResponse(user.getUserId());
			}
			else {
				connection.rollback();
				throw new SQLException();
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new DAOException(e, "DAO Exception : unable to update profile");
		}
		
		return userResponse;
	}

	@Override
	public boolean updatePassword(User user) {
		return false;
	}

	

}
