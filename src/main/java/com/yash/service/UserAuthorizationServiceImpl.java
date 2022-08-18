package com.yash.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yash.dao.UserAuthorityDAO;

@Service("userAuthorizationServiceImpl")
public class UserAuthorizationServiceImpl implements UserDetailsService{

	@Autowired
	private UserAuthorityDAO userAuthorityDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails userDetails = null;
		try {
			userDetails = userAuthorityDAO.getUserAuthorityDetails(username);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return userDetails;
	}

}
