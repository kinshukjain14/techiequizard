package com.yash.model;

import java.io.Serializable;
import java.util.List;

public class ModuleDataModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int moduleId;
	private String moduleName;
	List<QuestionModel> questionsList;

	public ModuleDataModel(List<QuestionModel> questionsList) {
		super();
		this.questionsList = questionsList;
	}

	public List<QuestionModel> getQuestionsList() {
		return questionsList;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public void setQuestionsList(List<QuestionModel> questionsList) {
		this.questionsList = questionsList;
	}
	
}
