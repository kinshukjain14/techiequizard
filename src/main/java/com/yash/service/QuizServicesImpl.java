package com.yash.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yash.dao.QuizQuestionsDAO;
import com.yash.entities.Module;
import com.yash.entities.Option;
import com.yash.entities.Question;
import com.yash.exception.DAOException;
import com.yash.exception.QuestionParsingException;
import com.yash.model.QuestionModel;
import com.yash.model.ModuleDataModel;
import com.yash.model.ModulesData;

@Service
public class QuizServicesImpl implements QuizServices{

//	@Autowired@Qualifier("jDBCQuizQuestionsDAOImpl")
//	@Autowired@Qualifier("hibernateQuizQuestionsDAOImpl")
	@Autowired@Qualifier("springJDBCQuizQuestionsDAOImpl")
	private QuizQuestionsDAO quizQuestionsDAO;

	@Override
	public ModuleDataModel getModuleQuestions(int moduleId) throws QuestionParsingException{
		List<QuestionModel> qModels = new ArrayList<>();
		Module module;
		try {
			module = quizQuestionsDAO.retriveModuleQuestions(moduleId);
			List<Question> questions = module.getQuestions();
			questions.forEach(x->{
				String question = x.getQuestions();
				List<Option> optionsList = x.getOptionsList();
				Map<String,Boolean> map = new LinkedHashMap<>();
				for (Option option : optionsList) {
					map.put(option.getOptionDescription(), option.isCorrect());
				}
				qModels.add(new QuestionModel(question, map));
				
			});
			Collections.shuffle(qModels);
			ModuleDataModel model = new ModuleDataModel(qModels);
			model.setModuleId(module.getModuleId());
			model.setModuleName(module.getModuleName());
			return model;
		} catch (DAOException e) {
			throw new QuestionParsingException("Error in processing the quiz questions");
		}
	}

	@Override
	public List<ModulesData> getAllModules() {
		List<ModulesData> modulesDataList = new ArrayList<ModulesData>();
		try {
			List<Module> allModules = quizQuestionsDAO.retrieveAllModules();
			allModules.forEach(x->{
				ModulesData modulesData = new ModulesData();
				modulesData.setModuleId(x.getModuleId());
				modulesData.setModuleName(x.getModuleName());
				modulesData.setModuleDescription(x.getModuleDescription());
				modulesDataList.add(modulesData);
			});
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		return modulesDataList;
	}
}
