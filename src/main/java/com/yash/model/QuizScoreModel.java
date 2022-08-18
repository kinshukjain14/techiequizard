package com.yash.model;

public class QuizScoreModel 
{
	private int userId;
	private Long candidateId;
	private String candidateName;
	private int moduleId;
	private String moduleName;
	private int timeTaken;
	private int correctAttempt;
	private int totalAttempt;
	private int totalQuestions;
	private int unattempted;
	
	public QuizScoreModel() {
		super();
	}
	public Long getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}
	public String getCandidateName() {
		return candidateName;
	}
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getTimeTaken() {
		return timeTaken;
	}
	public void setTimeTaken(int timeTaken) {
		this.timeTaken = timeTaken;
	}
	public int getModuleId() {
		return moduleId;
	}
	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}
	public int getTotalAttempt() {
		return totalAttempt;
	}
	public void setTotalAttempt(int totalAttempt) {
		this.totalAttempt = totalAttempt;
	}
	public int getTotalQuestions() {
		return totalQuestions;
	}
	public void setTotalQuestions(int totalQuestions) {
		this.totalQuestions = totalQuestions;
	}
	public int getUnattempted() {
		return unattempted;
	}
	public void setUnattempted(int unattempted) {
		this.unattempted = unattempted;
	}
	public int getCorrectAttempt() {
		return correctAttempt;
	}
	public void setCorrectAttempt(int correctAttempt) {
		this.correctAttempt = correctAttempt;
	}
	
	
}
