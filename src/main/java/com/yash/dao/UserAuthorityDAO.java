package com.yash.dao;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserAuthorityDAO {
	public UserDetails getUserAuthorityDetails(String username);
}
