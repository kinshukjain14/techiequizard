package com.yash.entities;

public class QuizChartAnalysis {
	private int userId;
	private int moduleId;
	private String moduleName;
	private String status;
	private Long count;
	
	public QuizChartAnalysis() {
		super();
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getModuleId() {
		return moduleId;
	}
	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "QuizChartAnalysis [userId=" + userId + ", moduleId=" + moduleId + ", moduleName=" + moduleName
				+ ", status=" + status + ", count=" + count + "]";
	}
	
	
}
