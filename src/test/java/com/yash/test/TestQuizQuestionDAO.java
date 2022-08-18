package com.yash.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.yash.dao.QuizQuestionsDAO;
import com.yash.entities.Question;
import com.yash.entities.Module;
import com.yash.exception.DAOException;
import com.yash.helper.QuizFactory;
import com.yash.integrate.ConnectionManager;

class TestQuizQuestionDAO {

	@InjectMocks
	private static QuizQuestionsDAO dao;
	
	@Mock
	private ConnectionManager manager;
	
	@Mock
	private Connection connection;
	
	@Mock
	private PreparedStatement statement;
	
	@Mock
	private ResultSet resultSet;
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		dao = QuizFactory.newDAOInstance();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		dao=null;
	}
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@DisplayName("Check the Subject requested is available for quiz : Positive")
	@Test
	void testCheckSubjectRequestedIsAvailable() {
		try {
			when(manager.openConnection()).thenReturn(connection);
			when(connection.prepareStatement(anyString())).thenReturn(statement);
			when(statement.executeQuery()).thenReturn(resultSet);
			when(resultSet.next()).thenReturn(true).thenReturn(false);
			Module subjects = dao.retriveModuleQuestions(1);
			List<Question> questions = subjects.getQuestions();
			
			assertTrue(questions.size()>0);
		} catch (DAOException | SQLException | ClassNotFoundException e) {
			assertFalse(true);
		}
	}
	@DisplayName("Check the Subject requested is not available for quiz : Negative")
	@Test
	void testSubjectRequestedisNotAvailable() {
		try {
			when(manager.openConnection()).thenReturn(connection);
			when(connection.prepareStatement(anyString())).thenReturn(statement);
			when(statement.executeQuery()).thenReturn(resultSet);
			when(resultSet.next()).thenReturn(false);
			Module subjects = dao.retriveModuleQuestions(1);
			List<Question> actual = subjects.getQuestions();
			assertTrue(!(actual.size()>0));
		} catch (DAOException | ClassNotFoundException | SQLException e) {
		}
	}

}
