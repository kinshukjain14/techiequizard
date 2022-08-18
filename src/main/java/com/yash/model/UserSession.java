package com.yash.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS) 
public class UserSession {
	
	private Map<String,Object> sessionObject = new HashMap<String, Object>();
//	
//	public Map<String, Object> getSessionObject() {
//		return this.sessionObject;
//	}
//
//	public void setSessionObject(Map<String, Object> sessionObject) {
//		this.sessionObject = sessionObject;
//	}

	
	public Object getAttribute(String key) {
		return this.sessionObject.get(key);
	}

	public void setAttribute(String key,Object value) {
		this.sessionObject.put(key, value);
	}
	
}
