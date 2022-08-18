package com.yash.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QuizQuestionsSQLQueries {
	@Value(value = "${selectModuleBasedOnName}")
	private String selectModuleBasedOnName;

	@Value(value="${selectAllModules}")
	private String selectAllModules;
	
	public String getSelectModuleBasedOnName() {
		return selectModuleBasedOnName;
	}

	public void setSelectModuleBasedOnName(String selectModuleBasedOnName) {
		this.selectModuleBasedOnName = selectModuleBasedOnName;
	}

	public String getSelectAllModules() {
		return selectAllModules;
	}

	public void setSelectAllModules(String selectAllModules) {
		this.selectAllModules = selectAllModules;
	} 
	
}
