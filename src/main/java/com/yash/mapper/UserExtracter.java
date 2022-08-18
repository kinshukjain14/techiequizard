package com.yash.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.yash.entities.Authorities;
import com.yash.entities.User;
import com.yash.entities.UserCredentials;

@Component("userExtracter")
public class UserExtracter implements ResultSetExtractor<User> {

	@Override
	public User extractData(ResultSet rs) throws SQLException, DataAccessException {
		User user = new User();
		UserCredentials userCredentials = new UserCredentials();
		Authorities auths = new Authorities();
		if (rs.next()) {
			java.sql.Date dob = rs.getDate("date_of_birth");
			Timestamp registeredDate = rs.getTimestamp("registered_on");
			Timestamp lastLoginDate = rs.getTimestamp("last_login");
			LocalDate dateOfBirth = LocalDate.now();
			LocalDateTime registeredOn = LocalDateTime.now();
			LocalDateTime lastLogin = LocalDateTime.now();
			if (dob != null) {
				dateOfBirth = dob.toLocalDate();
			}
			if (registeredDate != null) {
				registeredOn = registeredDate.toLocalDateTime();
			}
			if (lastLoginDate != null) {
				lastLogin = lastLoginDate.toLocalDateTime();
			}
			auths.setUsername(rs.getString("username"));
			auths.setAuthority(rs.getString("authority"));
			
			userCredentials.setAuthorities(auths);
			userCredentials.setUserId(rs.getInt("user_id"));
			userCredentials.setUsername(rs.getString("username"));
			userCredentials.setPassword(rs.getString("password"));
			
			user.setUserCredentials(userCredentials);
			user.setUserId(rs.getInt("user_id"));
			user.setFirstName(rs.getString("first_name"));
			user.setLastName(rs.getString("last_name"));
			user.setEmail(rs.getString("email"));
			user.setContactNo(rs.getLong("contact"));
			user.setGender(rs.getString("gender"));
			user.setDateOfBirth(dateOfBirth);
			user.setAddress(rs.getString("address"));
			user.setCountryId(rs.getInt("country_id"));
			user.setStateId(rs.getInt("state_id"));
			user.setCityId(rs.getInt("city_id"));
			user.setRegisteredOn(registeredOn);
			user.setLastLogin(lastLogin);
		}
		return user;
	}

}
