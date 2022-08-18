package com.yash.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserAuthSQLQueries {
	
	@Value(value = "${getUserIdProcedure}")
	private String getUserIdProcedure;
	
	@Value(value = "${getUserDetailsByUserIdProcedure}")
	private String getUserDetailsByUserIdProcedure;
	
	@Value(value = "${insertUserDetails}")
	private String insertUserDetails;
	
	@Value(value="${insertUserCredentials}")
	private String insertUserCredentials;
	
	@Value(value="${insertUserAuthorities}")
	private String insertUserAuthorities;
	
	@Value(value = "${checkUserExist}")
	private String checkUserExist;
		
	@Value(value = "${checkEmailExist}")
	private String checkEmailExist;
		
	@Value(value = "${checkContactExist}")
	private String checkContactExist;
	
	@Value(value="${updateUserProfile}")
	private String updateUserProfile;
	
	@Value(value="${updateUserName}")
	private String updateUserName;
	
	@Value(value="${updateUserPassword}")
	private String updateUserPassword;
	
	public String getInsertUserDetails() {
		return insertUserDetails;
	}
	public void setInsertUserDetails(String insertUserDetails) {
		this.insertUserDetails = insertUserDetails;
	}
	
	public String getInsertUserCredentials() {
		return insertUserCredentials;
	}
	public void setInsertUserCredentials(String insertUserCredentials) {
		this.insertUserCredentials = insertUserCredentials;
	}
	public String getInsertUserAuthorities() {
		return insertUserAuthorities;
	}
	public void setInsertUserAuthorities(String insertUserAuthorities) {
		this.insertUserAuthorities = insertUserAuthorities;
	}
	public String getGetUserIdProcedure() {
		return getUserIdProcedure;
	}
	public void setGetUserIdProcedure(String getUserIdProcedure) {
		this.getUserIdProcedure = getUserIdProcedure;
	}
	public String getGetUserDetailsByUserIdProcedure() {
		return getUserDetailsByUserIdProcedure;
	}
	public void setGetUserDetailsByUserIdProcedure(String getUserDetailsByUserIdProcedure) {
		this.getUserDetailsByUserIdProcedure = getUserDetailsByUserIdProcedure;
	}
	public String getCheckUserExist() {
		return checkUserExist;
	}
	public void setCheckUserExist(String checkUserExist) {
		this.checkUserExist = checkUserExist;
	}
	public String getCheckEmailExist() {
		return checkEmailExist;
	}
	public void setCheckEmailExist(String checkEmailExist) {
		this.checkEmailExist = checkEmailExist;
	}
	public String getCheckContactExist() {
		return checkContactExist;
	}
	public void setCheckContactExist(String checkContactExist) {
		this.checkContactExist = checkContactExist;
	}
	public String getUpdateUserProfile() {
		return updateUserProfile;
	}
	public void setUpdateUserProfile(String updateUserProfile) {
		this.updateUserProfile = updateUserProfile;
	}
	public String getUpdateUserName() {
		return updateUserName;
	}
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}
	public String getUpdateUserPassword() {
		return updateUserPassword;
	}
	public void setUpdateUserPassword(String updateUserPassword) {
		this.updateUserPassword = updateUserPassword;
	}
	
}
