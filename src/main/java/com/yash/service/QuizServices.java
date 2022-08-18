package com.yash.service;

import java.util.List;

import com.yash.exception.QuestionParsingException;
import com.yash.model.ModuleDataModel;
import com.yash.model.ModulesData;

public interface QuizServices 
{
	public ModuleDataModel getModuleQuestions(int moduleId) throws QuestionParsingException;
	public List<ModulesData> getAllModules();
}
