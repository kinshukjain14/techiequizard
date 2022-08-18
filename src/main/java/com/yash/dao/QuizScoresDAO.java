package com.yash.dao;

import java.util.List;

import com.yash.entities.QuizChartAnalysis;
import com.yash.entities.QuizScores;
import com.yash.exception.DAOException;

public interface QuizScoresDAO {
	public boolean saveQuizScores(QuizScores score) throws DAOException;
	public List<QuizScores> fetchQuizScores(int userId)throws DAOException;
	public List<QuizChartAnalysis> fetchQuizAnalysis(int userId) throws DAOException;
}
