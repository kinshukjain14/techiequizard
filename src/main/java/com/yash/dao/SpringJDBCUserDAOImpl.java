package com.yash.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.yash.entities.User;
import com.yash.exception.DAOException;
import com.yash.exception.UserAlreadyExistException;
import com.yash.helper.UserAuthSQLQueries;

@Repository("springJDBCUserDAOImpl")
public class SpringJDBCUserDAOImpl implements UserDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private UserAuthSQLQueries userAuthSQLQueries;
	
	@Autowired@Qualifier("userExtracter")
	private ResultSetExtractor<User> userExtracter;
	
	@Override
	public Optional<User> checkUserCredentials(String userName, String password) throws DAOException {
		int userId=0;
		try(Connection connection = jdbcTemplate.getDataSource().getConnection()){
			CallableStatement statement = connection.prepareCall(userAuthSQLQueries.getGetUserIdProcedure());
			statement.setString(1, userName);
			statement.setString(2, password);
			statement.registerOutParameter(3, Types.INTEGER);
			statement.execute();
			userId=statement.getInt(3);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Optional<User> userResponse = requestUserResponse(userId);
		return userResponse;
	}

	@Override
	public Optional<User> requestUserResponse(int userId) throws DAOException {
		User user = jdbcTemplate.query(userAuthSQLQueries.getGetUserDetailsByUserIdProcedure(),new Object[] {userId},userExtracter);
		Optional<User> userData = Optional.of(user);
		
		return userData;
	}

	@Override
	public boolean registerUser(User user) throws DAOException, UserAlreadyExistException {
		
		int insert = jdbcTemplate.update(userAuthSQLQueries.getInsertUserDetails(),
				user.getUserId(),
				user.getFirstName(),
				user.getLastName(),
				user.getEmail(),
				user.getContactNo(),
				user.getUserCredentials().getUsername(),
				user.getUserCredentials().getPassword(),
				java.sql.Timestamp.valueOf(user.getRegisteredOn()),
				java.sql.Timestamp.valueOf(user.getLastLogin()),
				user.getGender(),
				java.sql.Date.valueOf(user.getDateOfBirth()),
				user.getAddress(),
				user.getCountryId(),
				user.getStateId(),
				user.getCityId());
		
		if(insert>0)
			return true;
		return false;
	}

	@Override
	public boolean checkContactNumberAvailable(Long contactNo) {
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(userAuthSQLQueries.getCheckContactExist(), new Object[] {contactNo});
		if(rowSet.next())
			return true;
		return false;
	}

	@Override
	public boolean checkEmailAvailable(String emailId) {
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(userAuthSQLQueries.getCheckEmailExist(),new Object[] {emailId});
		if(rowSet.next())
			return true;
		return false;
	}

	@Override
	public Optional<User> updateProfile(User user) throws DAOException {
		Optional<User> userResponse = Optional.empty();
		int update = jdbcTemplate.update(userAuthSQLQueries.getUpdateUserProfile(),
				user.getFirstName(),
				user.getLastName(),
				user.getEmail(),
				user.getContactNo(),
				user.getUserCredentials().getUsername(),
				user.getGender(),
				java.sql.Date.valueOf(user.getDateOfBirth()),
				user.getAddress(),
				user.getCountryId(),
				user.getStateId(),
				user.getCityId(),
				user.getUserId());
		if(update > 0)
		{
			userResponse = requestUserResponse(user.getUserId());
			
		}
		else {
			throw new DAOException(new Exception(), "DAO Exception : unable to update data");
		}
		return userResponse;
	}

	@Override
	public boolean updatePassword(User user) {
		
		return false;
	}

}
