package com.yash.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yash.dao.QuizScoresDAO;
import com.yash.entities.Module;
import com.yash.entities.QuizChartAnalysis;
import com.yash.entities.QuizScores;
import com.yash.entities.User;
import com.yash.exception.DAOException;
import com.yash.model.QuizResultModel;
import com.yash.model.QuizScoreModel;

@Service
public class QuizScoreServiceImpl implements QuizScoreServices {

//	@Autowired@Qualifier("jDBCQuizScoresDAOImpl")
	@Autowired
	@Qualifier("hibernateQuizScoreDAOImpl")
	private QuizScoresDAO quizScoresDAO;

	public QuizScoreServiceImpl() {
	}

	@Override
	public QuizResultModel saveQuizScore(QuizScoreModel model) {
		Integer totalQuestions = model.getTotalQuestions();
		Integer totalAttempt = model.getTotalAttempt();
		Integer correctAttempt = model.getCorrectAttempt();
		Integer unattempted = model.getUnattempted();
		Integer incorrectAttempt = totalQuestions - correctAttempt;
		Double percentage = (double) ((correctAttempt * 100) / totalQuestions);
		String grade = computeGrade(percentage);
		String status = "FAIL";

		if (percentage.doubleValue() >= 80) {
			status = "PASS";
		}

		QuizScores quizScores = new QuizScores();
		User user = new User();
		Module module = new Module();
		user.setUserId(model.getUserId());
		module.setModuleId(model.getModuleId());
		module.setModuleName(model.getModuleName());
		quizScores.setUser(user);
		quizScores.setModule(module);
		quizScores.setCandidateId(model.getCandidateId());
		quizScores.setPercentage(percentage);
		quizScores.setGrade(grade);
		quizScores.setStatus(status);
		quizScores.setTimeTaken(model.getTimeTaken());
		quizScores.setAppearedOn(LocalDate.now());

		QuizResultModel quizResultModel = new QuizResultModel();

		try {
			boolean result = quizScoresDAO.saveQuizScores(quizScores);
			if (result) {
				quizResultModel.setCandidateId(model.getCandidateId().toString());
				quizResultModel.setModuleName(model.getModuleName());
				quizResultModel.setTotalQuestions(totalQuestions);
				quizResultModel.setAttempted(totalAttempt);
				quizResultModel.setCorrect(correctAttempt);
				quizResultModel.setUnanswered(unattempted);
				quizResultModel.setIncorrect(incorrectAttempt);
				quizResultModel.setPercentage(percentage);
				quizResultModel.setGrade(grade);
				quizResultModel.setStatus(status);
				quizResultModel.setLevel("Beginnner");
			}
		} catch (DAOException e) {
		}
		return quizResultModel;
	}

	@Override
	public String getQuizScore(int userId) {
		String quizScoresJson = "{}";
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			List<QuizScores> quizScores = quizScoresDAO.fetchQuizScores(userId);
			quizScoresJson = mapper.writeValueAsString(quizScores);
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return quizScoresJson;
	}

	@Override
	public String getQuizAnalysis(int userId) {
		String quizScoreAnalysis = "{}";
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			List<QuizChartAnalysis> quizAnalysis = quizScoresDAO.fetchQuizAnalysis(userId);
			Map<Integer,List<QuizChartAnalysis>> analysisMap;
			analysisMap = quizAnalysis.stream().collect(Collectors.groupingBy(QuizChartAnalysis::getModuleId));
			
			quizScoreAnalysis = mapper.writeValueAsString(analysisMap);
		} catch (DAOException e) {
//			e.printStackTrace();
		} catch (JsonProcessingException e) {
//			e.printStackTrace();
		}
		return quizScoreAnalysis;
	}

	@Override
	public String computeGrade(Double percentage) {
		if (percentage > 90)
			return "A";
		else if (percentage > 80 && percentage <= 90)
			return "B";
		else if (percentage > 70 && percentage <= 80)
			return "C";
		else if (percentage > 60 && percentage <= 70)
			return "D";
		else if (percentage > 50 && percentage <= 60)
			return "E";
		else
			return "F";
	}

}
