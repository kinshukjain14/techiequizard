package com.yash.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.yash.entities.Module;
import com.yash.entities.QuizScores;
import com.yash.entities.User;

@Component("quizScoresMapper")
public class QuizScoresMapper implements RowMapper<QuizScores> {

	@Override
	public QuizScores mapRow(ResultSet rs, int rowNum) throws SQLException {
		QuizScores quizScores = new QuizScores();
		User user = new User();
		Module module = new Module();
		user.setUserId(rs.getInt("user_id"));
		quizScores.setUser(user);
		quizScores.setCandidateId(rs.getLong("quiz_id"));
		quizScores.setPercentage(rs.getDouble("percentage"));
		quizScores.setGrade(rs.getString("grade"));
		quizScores.setTimeTaken(rs.getInt("time_taken"));
		quizScores.setAppearedOn(rs.getDate("appeared_on").toLocalDate());
		quizScores.setStatus(rs.getString("status"));
		module.setModuleId(rs.getInt("module_id"));
		module.setModuleName(rs.getString("module_name"));
		quizScores.setModule(module);
		
		return quizScores;
	}

}
