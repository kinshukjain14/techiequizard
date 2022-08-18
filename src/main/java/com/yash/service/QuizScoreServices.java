package com.yash.service;

import com.yash.model.QuizResultModel;
import com.yash.model.QuizScoreModel;

public interface QuizScoreServices {
	public QuizResultModel saveQuizScore(QuizScoreModel model);
	public String getQuizScore(int userId);
	public String computeGrade(Double percentage);
	public String getQuizAnalysis(int userId);
}
