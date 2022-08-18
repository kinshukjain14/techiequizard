package com.yash.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QuizScoresSQLQueries {
	@Value(value = "${saveQuizScores}")
	private String saveQuizScores;
	@Value(value = "${getQuizScoresProcedure}")
	private String getQuizScoresProcedure;
	public String getSaveQuizScores() {
		return saveQuizScores;
	}
	public void setSaveQuizScores(String saveQuizScores) {
		this.saveQuizScores = saveQuizScores;
	}
	public String getGetQuizScoresProcedure() {
		return getQuizScoresProcedure;
	}
	public void setGetQuizScoresProcedure(String getQuizScoresProcedure) {
		this.getQuizScoresProcedure = getQuizScoresProcedure;
	}
	
	
}
