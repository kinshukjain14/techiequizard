package com.yash.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.yash.integrate.ConnectionManager;

@Repository
public class UserAuthorityDAOImpl implements UserAuthorityDAO {

	@Autowired
	ConnectionManager connectionManager;
	
	@Override
	public UserDetails getUserAuthorityDetails(String username) 
	{
		UserDetails userDetails=null;
		try(Connection connection = connectionManager.openConnection()){
			PreparedStatement statement = connection.prepareStatement("SELECT uc.user_id,uc.username,uc.password,uc.active_status,a.authority \r\n" + 
					"FROM usercredentials uc \r\n" + 
					"JOIN authorities a \r\n" + 
					"ON uc.username=a.user_name \r\n" + 
					"WHERE a.user_name=?");
			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();
			ArrayList<GrantedAuthority> authoritiesGranted = new ArrayList<GrantedAuthority>();
			String userNameDB=null;
			String password=null;
			
			while(resultSet.next()) {
				GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(resultSet.getString("authority"));
				authoritiesGranted.add(grantedAuthority);
				userNameDB=resultSet.getString("username");
				password="{noop}"+resultSet.getString("password");
			}
			userDetails = new User(userNameDB, password, authoritiesGranted);
		}
		catch(SQLException | ClassNotFoundException e) {
//			e.printStackTrace();
		}
		catch(Exception e) {
			
		}
		return userDetails;
	}

}
