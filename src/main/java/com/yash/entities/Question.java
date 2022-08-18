package com.yash.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "question")
public class Question 
{
	@Id
	@Column(name = "ques_id")
	private int questionId;
	@Column(name = "ques_desc")
	private String question;
//	transient private Option option;
	
	@OneToMany(targetEntity = Option.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "question_id")
	private List<Option> optionsList;
	
	@ManyToOne
	@JoinColumn(name = "module_id")
	private Module module;
	
	public Question() {
	}

	public String getQuestions() {
		return question;
	}

	public void setQuestions(String questions) {
		this.question = questions;
	}

//	public Option getOption() {
//		return option;
//	}
//
//	public void setOption(Option option) {
//		this.option = option;
//	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public List<Option> getOptionsList() {
		return optionsList;
	}

	public void setOptionsList(List<Option> optionsList) {
		this.optionsList = optionsList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((module == null) ? 0 : module.hashCode());
		result = prime * result + ((optionsList == null) ? 0 : optionsList.hashCode());
		result = prime * result + ((question == null) ? 0 : question.hashCode());
		result = prime * result + questionId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (module == null) {
			if (other.module != null)
				return false;
		} else if (!module.equals(other.module))
			return false;
		if (optionsList == null) {
			if (other.optionsList != null)
				return false;
		} else if (!optionsList.equals(other.optionsList))
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		if (questionId != other.questionId)
			return false;
		return true;
	}


}
