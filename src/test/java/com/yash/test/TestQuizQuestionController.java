package com.yash.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Matchers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.yash.controller.QuizQuestionController;
import com.yash.exception.QuestionParsingException;
import com.yash.main.Application;
import com.yash.model.UserSession;
import com.yash.model.ModuleDataModel;
import com.yash.model.ModulesData;
import com.yash.model.QuestionModel;
import com.yash.service.QuizServices;

@AutoConfigureMockMvc
@WebMvcTest
@ContextConfiguration(classes = { Application.class })
class TestQuizQuestionController {

	@Mock
	private QuizServices quizServices;
	
	@Spy
	private HttpServletRequest request;
	
	@Mock
	private UserSession userSession;
	
	@InjectMocks
	private QuizQuestionController quizQuestionController;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(quizQuestionController).build();
	}

	@DisplayName("Test Handle get all modules details : Positive")
	@Test
	void testHandleGetAllModules_Positive() {
		when(quizServices.getAllModules()).then((invocation)->{
			List<ModulesData> modulesList = new ArrayList<ModulesData>();
			modulesList.add(new ModulesData());
			modulesList.add(new ModulesData());
			modulesList.add(new ModulesData());
			modulesList.add(new ModulesData());
			return modulesList;
		});
		
		ResponseEntity<List<ModulesData>> getAllModules = quizQuestionController.handleGetAllModules();
		List<ModulesData> body = getAllModules.getBody();
		assertTrue(body.size()>0);
	}
	
	@DisplayName("Test Handle get all modules details : Negative")
	@Test
	void testHandleGetAllModules_Negative() {
		when(quizServices.getAllModules()).then((invocation)->{
			List<ModulesData> modulesList = new ArrayList<ModulesData>();
			
			return modulesList;
		});
		
		ResponseEntity<List<ModulesData>> getAllModules = quizQuestionController.handleGetAllModules();
		List<ModulesData> body = getAllModules.getBody();
		assertTrue(body.size()==0);
	}
	
	@DisplayName("Test Handle get all modules details URI : Positive")
	@Test
	void testHandleGetAllModulesURI_Positive() {
		when(quizServices.getAllModules()).then((invocation)->{
			List<ModulesData> modulesList = new ArrayList<ModulesData>();
			modulesList.add(new ModulesData());
			modulesList.add(new ModulesData());
			modulesList.add(new ModulesData());
			modulesList.add(new ModulesData());
			return modulesList;
		});
		
		try {
			mockMvc.perform(get("/api/modules")).andExpect(status().isOk()).andReturn();
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	@DisplayName("Test Handle get all modules details URI : Negative")
	@Test
	void testHandleGetAllModulesURI_Negative() {
		when(quizServices.getAllModules()).then((invocation)->{
			List<ModulesData> modulesList = new ArrayList<ModulesData>();
			
			return modulesList;
		});
		
		try {
			mockMvc.perform(get("/api/modules")).andExpect(status().isNoContent()).andReturn();
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	
	@DisplayName("Test Handle Quiz Question Request : Positive")
	@Test
	void handleQuizQuestionsRequest_Positive() throws QuestionParsingException {
		QuestionModel questionModel = new QuestionModel("Question 1", new HashMap<>());
		List<QuestionModel> questionsList = new ArrayList<QuestionModel>();
		questionsList.add(questionModel);
		questionsList.add(questionModel);
		questionsList.add(questionModel);
		ModuleDataModel moduleDataModel = new ModuleDataModel(questionsList);
		when(quizServices.getModuleQuestions(anyInt())).thenReturn(moduleDataModel);
		ResponseEntity<ModuleDataModel> quizQuestionsRequest = quizQuestionController.handleQuizQuestionsRequest(1, request);
		ModuleDataModel body = quizQuestionsRequest.getBody();
		int size = body.getQuestionsList().size();
		assertTrue(size>0);
	}
	
	@DisplayName("Test Handle Quiz Question Request : Negative")
	@Test
	void handleQuizQuestionsRequest_Negative() throws QuestionParsingException {
		List<QuestionModel> questionsList = new ArrayList<QuestionModel>();
		ModuleDataModel moduleDataModel = new ModuleDataModel(questionsList);
		when(quizServices.getModuleQuestions(anyInt())).thenReturn(moduleDataModel);
		ResponseEntity<ModuleDataModel> quizQuestionsRequest = quizQuestionController.handleQuizQuestionsRequest(1, request);
		ModuleDataModel body = quizQuestionsRequest.getBody();
		int size = body.getQuestionsList().size();
		assertTrue(size==0);
	}
	
	@DisplayName("Test Handle Quiz Question Request URI : Positive")
	@Test
	void handleQuizQuestionsRequestURI_Positive() throws QuestionParsingException {
		QuestionModel questionModel = new QuestionModel("Question 1", new HashMap<>());
		List<QuestionModel> questionsList = new ArrayList<QuestionModel>();
		questionsList.add(questionModel);
		questionsList.add(questionModel);
		questionsList.add(questionModel);
		ModuleDataModel moduleDataModel = new ModuleDataModel(questionsList);
		when(quizServices.getModuleQuestions(anyInt())).thenReturn(moduleDataModel);
		
		try {
			mockMvc.perform(get("/api/modules/{moduleId}",1)).andExpect(status().isOk()).andReturn();
		} catch (Exception e) {
			assertTrue(false);
			
		}
		
	}
	
	@DisplayName("Test Handle Quiz Question Request URI : Negative")
	@Test
	void handleQuizQuestionsRequestURI_Negative() throws QuestionParsingException {
		
		List<QuestionModel> questionsList = new ArrayList<QuestionModel>();
		
		ModuleDataModel moduleDataModel = new ModuleDataModel(questionsList);
		when(quizServices.getModuleQuestions(anyInt())).thenReturn(moduleDataModel);
		
		try {
			mockMvc.perform(get("/api/modules/{moduleId}",1)).andExpect(status().isNoContent()).andReturn();
		} catch (Exception e) {
			assertTrue(false);
			
		}
		
	}
	

}
