package com.yash.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.anyInt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.yash.dao.QuizQuestionsDAO;
import com.yash.entities.Module;
import com.yash.entities.Question;
import com.yash.entities.Option;
import com.yash.exception.DAOException;
import com.yash.exception.QuestionParsingException;
import com.yash.model.QuestionModel;
import com.yash.model.ModuleDataModel;
import com.yash.service.QuizServicesImpl;

class TestQuizService {

	@Mock
	private QuizQuestionsDAO dao;
	
	@InjectMocks
	private QuizServicesImpl quizServices;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@DisplayName("Get Questions list : positive")
	@Test
	void testGetQuestionsListFromServiceIfAvailable() {
		try {
			when(dao.retriveModuleQuestions(anyInt())).then((invocation)->{
				List<Option> optionList = new ArrayList<>();
				Option option = new Option();
				option.setOptionId(1);
				option.setOptionDescription("question");
				option.setCorrect(false);
				
				optionList.add(option);
				optionList.add(option);
				optionList.add(option);
				optionList.add(option);
				
				List<Question> quesList = new ArrayList<>();
				Question question = new Question();
				question.setOptionsList(optionList);
				quesList.add(question);
				quesList.add(question);
				quesList.add(question);
				quesList.add(question);
				quesList.add(question);	
				
				Module module = new Module();
				module.setModuleId(1);
				module.setModuleName("Java");
				module.setQuestions(quesList);
				return module;
			});
			ModuleDataModel subjectQuestions = quizServices.getModuleQuestions(1);
			List<QuestionModel> questionsList = subjectQuestions.getQuestionsList();
			assertTrue(questionsList.size()!=0);
		} catch (QuestionParsingException | DAOException e) {
			assertTrue(false);
		}
	}
	
	@DisplayName("Get Questions list : Negative")
	@Test
	void testGetQuestionsListFromServiceIfNotAvailable() {
		try {
			when(dao.retriveModuleQuestions(anyInt())).then((invocation)->{
				List<Question> quesList = new ArrayList<>();
				
				Module module = new Module();
				module.setModuleId(1);
				module.setModuleName("Java");
				module.setQuestions(quesList);
				return module;
			});
			ModuleDataModel subjectQuestions = quizServices.getModuleQuestions(1);
			List<QuestionModel> questionsList = subjectQuestions.getQuestionsList();
			assertTrue(!(questionsList.size()!=0));
		} catch (QuestionParsingException | DAOException e) {
		}
		assertTrue(true);
	}
	
	@DisplayName("Check Question having 4 options")
	@Test
	void testtestCheckQuestionsFromServiceHaving4Options() {
		boolean expectedFlag=true;
		try {
			when(dao.retriveModuleQuestions(anyInt())).then((invocation)->
			{
				List<Option> optionList = new ArrayList<>();
				Option option = new Option();
				option.setOptionId(1);
				option.setOptionDescription("");
				option.setCorrect(false);
				
				optionList.add(option);
				optionList.add(option);
				optionList.add(option);
				optionList.add(option);
				
				List<Question> quesList = new ArrayList<>();
				Question question = new Question();
				question.setOptionsList(optionList);
				quesList.add(question);
				quesList.add(question);
				quesList.add(question);
				quesList.add(question);
				quesList.add(question);				
				
				Module module = new Module();
				module.setModuleId(1);
				module.setModuleName("Java");
				module.setQuestions(quesList);
				
				return module;
			});
			ModuleDataModel subjectQuestions = quizServices.getModuleQuestions(1);
			List<QuestionModel> questionsList = subjectQuestions.getQuestionsList();
			for (QuestionModel questionModel : questionsList) {
				Map<String, Boolean> optionsMap = questionModel.getOptionsMap();
				if(optionsMap.size()==0) {
					expectedFlag=false;
					break;
				}
			}
		} catch (QuestionParsingException | DAOException e) {
		}
		assertTrue(expectedFlag);
	}

}
