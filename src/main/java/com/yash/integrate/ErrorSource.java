package com.yash.integrate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ErrorSource {
	@Value("${com.yash.model.UserModelRequest.email.error}")
	private String emailError;
	@Value("${com.yash.model.UserModelRequest.contactNo.error}")
	private String contactNoError;
	public String getEmailError() {
		return emailError;
	}
	public void setEmailError(String emailError) {
		this.emailError = emailError;
	}
	public String getContactNoError() {
		return contactNoError;
	}
	public void setContactNoError(String contactNoError) {
		this.contactNoError = contactNoError;
	}
	
}
