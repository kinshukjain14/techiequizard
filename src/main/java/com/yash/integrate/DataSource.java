package com.yash.integrate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DataSource {
	@Value(value = "${driver}")
	private String driver;
	@Value(value = "${url}")
	private String url;
	@Value(value = "${authname}")
	private String userName;
	@Value(value = "${authpassword}")
	private String password;
	
	public DataSource() {
	}

	public String getDriver() {
		return driver;
	}

	public String getUrl() {
		return url;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "DataSource [driver=" + driver + ", url=" + url + ", userName=" + userName + ", password=" + password
				+ "]";
	}
	
	
}
