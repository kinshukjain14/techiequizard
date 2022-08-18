package com.yash.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yash.entities.QuizChartAnalysis;
import com.yash.entities.QuizScores;
import com.yash.exception.DAOException;
import com.yash.helper.QuizScoresSQLQueries;

@Repository("springJDBCQuizScoreDAOImpl")
public class SpringJDBCQuizScoreDAOImpl implements QuizScoresDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private QuizScoresSQLQueries queries;
	
	@Autowired@Qualifier("quizScoresMapper")
	private RowMapper<QuizScores> quizScoresMapper;
	
	@Override
	public boolean saveQuizScores(QuizScores score) throws DAOException {
		int update = jdbcTemplate.update(queries.getSaveQuizScores(),
				score.getCandidateId(),
				score.getUser().getUserId(),
				score.getModule().getModuleId(),
				score.getPercentage(),
				score.getStatus(),
				score.getGrade(),
				score.getTimeTaken(),
				java.sql.Date.valueOf(score.getAppearedOn())
				);
		if(update>=1)
			return true;
		return false;
	}

	@Override
	public List<QuizScores> fetchQuizScores(int userId) throws DAOException {
		List<QuizScores> quizScoresList = jdbcTemplate.query(queries.getGetQuizScoresProcedure(), quizScoresMapper);
		return quizScoresList;
	}

	@Override
	public List<QuizChartAnalysis> fetchQuizAnalysis(int userId) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
}
