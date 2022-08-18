package com.yash.dao;

import java.util.List;

import com.yash.entities.Module;
import com.yash.exception.DAOException;

public interface QuizQuestionsDAO 
{
	public Module retriveModuleQuestions(int moduleId) throws DAOException;
	public List<Module> retrieveAllModules() throws DAOException;
}
