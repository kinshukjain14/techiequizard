package com.yash.test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.yash.controller.QuizScoreController;
import com.yash.main.Application;
import com.yash.model.ModuleDataModel;
import com.yash.model.ModulesData;
import com.yash.model.QuestionModel;
import com.yash.model.QuizResultModel;
import com.yash.model.QuizScoreModel;
import com.yash.model.UserResponse;
import com.yash.model.UserSession;
import com.yash.service.QuizScoreServices;

@AutoConfigureMockMvc
@WebMvcTest
@ContextConfiguration(classes = { Application.class })
class TestQuizScoresController {

	@Mock
	private QuizScoreServices quizScoreService;
	
	@Spy
	private HttpServletRequest request;
	
	@Mock
	private UserSession userSession;
	
	@InjectMocks
	private QuizScoreController quizScoresController;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception 
	{
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(quizScoresController).build();
	}

	@DisplayName("Test Get all quiz score : Positive")
	@Test
	void testGetAllQuizScores_Positive() {
		when(quizScoreService.getQuizScore(anyInt())).thenReturn("{userID : 1121}");
		ResponseEntity<String> quizScores = quizScoresController.getAllQuizScores(11231);
		assertFalse(quizScores.getBody().equals("{}"));
	}

	@DisplayName("Test Get all quiz score : Negative")
	@Test
	void testGetAllQuizScores_Negative() {
		when(quizScoreService.getQuizScore(anyInt())).thenReturn("{}");
		ResponseEntity<String> quizScores = quizScoresController.getAllQuizScores(11231);
		assertTrue(quizScores.getBody().equals("{}"));
	}
	
	@DisplayName("Test Get all quiz score URI : Positive")
	@Test
	void testGetAllQuizScoresURI_Positive() {
		when(quizScoreService.getQuizScore(anyInt())).thenReturn("{userID : 1121}");
		
		try {
			mockMvc.perform(get("/store/scores/{userId}",1121)).andExpect(status().isOk()).andReturn();
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@DisplayName("Test Get all quiz score URI : Negative")
	@Test
	void testGetAllQuizScoresURI_Negative() 
	{
		when(quizScoreService.getQuizScore(anyInt())).thenReturn("{}");
		try {
			mockMvc.perform(get("/store/scores/{userId}",1121)).andExpect(status().isNotFound()).andReturn();
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@DisplayName("Test save quiz scores : Positive")
	@Test
	void testSaveQuizScore_Positive() 
	{
		when(userSession.getAttribute("moduleDataModel")).then((invocation)->{
			return new ModuleDataModel(new ArrayList<QuestionModel>());
		});
		
		when(userSession.getAttribute("userModelResponse")).then((invocation)->{
			return new UserResponse();
		});
		QuizResultModel mock = new  QuizResultModel();
		mock.setCandidateId("111112232");
		when(quizScoreService.saveQuizScore(any(QuizScoreModel.class))).thenReturn(mock);

		ResponseEntity<QuizResultModel> quizScore = quizScoresController.saveQuizScore(10, 10, 10, 30, 22331122332l, request);
		QuizResultModel body = quizScore.getBody();
		assertTrue(body.getCandidateId() != null);
		
	}
	@DisplayName("Test save quiz scores : Negative")
	@Test
	void testSaveQuizScore_Negative() 
	{
		when(userSession.getAttribute("moduleDataModel")).then((invocation)->{
			return new ModuleDataModel(new ArrayList<QuestionModel>());
		});
		
		when(userSession.getAttribute("userModelResponse")).then((invocation)->{
			return new UserResponse();
		});
		QuizResultModel mock = new  QuizResultModel();
		when(quizScoreService.saveQuizScore(any(QuizScoreModel.class))).thenReturn(mock);

		ResponseEntity<QuizResultModel> quizScore = quizScoresController.saveQuizScore(10, 10, 10, 30, 22331122332l, request);
		QuizResultModel body = quizScore.getBody();
		assertTrue(body.getCandidateId() == null);
		
	}
	
	@DisplayName("Test save quiz scores URI : Negative")
	@Test
	void testSaveQuizScoreURI_Negative() 
	{
		when(userSession.getAttribute("moduleDataModel")).then((invocation)->{
			return new ModuleDataModel(new ArrayList<QuestionModel>());
		});
		
		when(userSession.getAttribute("userModelResponse")).then((invocation)->{
			return new UserResponse();
		});
		QuizResultModel mock = new  QuizResultModel();
		when(quizScoreService.saveQuizScore(any(QuizScoreModel.class))).thenReturn(mock);

		try {
			mockMvc.perform(post("/store/scores?correct=5&totalAttempt=10&totalQuestions=10&timeTaken=30&candidateID=11223221"))
					.andExpect(status().isExpectationFailed()).andReturn();
		} catch (Exception e) {
			assertTrue(false);
		}
		
	}
	@DisplayName("Test save quiz scores URI : Positive")
	@Test
	void testSaveQuizScoreURI_Positive() 
	{
		when(userSession.getAttribute("moduleDataModel")).then((invocation)->{
			return new ModuleDataModel(new ArrayList<QuestionModel>());
		});
		
		when(userSession.getAttribute("userModelResponse")).then((invocation)->{
			return new UserResponse();
		});
		QuizResultModel mock = new  QuizResultModel();
		mock.setCandidateId("11123322");
		when(quizScoreService.saveQuizScore(any(QuizScoreModel.class))).thenReturn(mock);

		try {
			mockMvc.perform(post("/store/scores?correct=5&totalAttempt=10&totalQuestions=10&timeTaken=30&candidateID=11223221"))
					.andExpect(status().isOk()).andReturn();
		} catch (Exception e) {
			assertTrue(false);
		}
		
	}
	
}
