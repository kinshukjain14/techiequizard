package com.yash.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yash.entities.Module;
import com.yash.entities.QuizChartAnalysis;
import com.yash.entities.QuizScores;
import com.yash.entities.User;
import com.yash.exception.DAOException;
import com.yash.helper.QuizScoresSQLQueries;
import com.yash.integrate.ConnectionManager;

@Repository("jDBCQuizScoresDAOImpl")
public class JDBCQuizScoreDAOImpl implements QuizScoresDAO {

	@Autowired
	private ConnectionManager connectionManager;
	
	@Autowired
	private QuizScoresSQLQueries quizScoresSQLQueries;
	
	@Override
	public boolean saveQuizScores(QuizScores score) throws DAOException {
		try(
			Connection connection = connectionManager.openConnection();	
				){
			PreparedStatement statement = connection.prepareStatement(quizScoresSQLQueries.getSaveQuizScores());
			statement.setLong(1, score.getCandidateId());
			statement.setInt(2,score.getUser().getUserId());
			statement.setInt(3, score.getModule().getModuleId());
			statement.setDouble(4, score.getPercentage());
			statement.setString(5, score.getStatus());
			statement.setString(6, score.getGrade());
			statement.setInt(7, score.getTimeTaken());
			statement.setDate(8,java.sql.Date.valueOf(score.getAppearedOn()));
			int i = statement.executeUpdate();
			if(i>0) {
				return true;
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			throw new DAOException(e, "DAO Exception : failed to store quiz scores");
		}
		return false;
	}

	@Override
	public List<QuizScores> fetchQuizScores(int userId) throws DAOException {
		List<QuizScores> scoresList = new ArrayList<>();
		try(
				Connection connection = connectionManager.openConnection();	
					){
			CallableStatement statement = connection.prepareCall(quizScoresSQLQueries.getGetQuizScoresProcedure());
			statement.setInt(1, userId);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) 
			{
				QuizScores quizScores = new QuizScores();
				User user = new User();
				Module module = new Module();
				user.setUserId(resultSet.getInt("user_id"));
				quizScores.setUser(user);
				quizScores.setCandidateId(resultSet.getLong("quiz_id"));
				quizScores.setPercentage(resultSet.getDouble("percentage"));
				quizScores.setGrade(resultSet.getString("grade"));
				quizScores.setTimeTaken(resultSet.getInt("time_taken"));
				quizScores.setAppearedOn(resultSet.getDate("appeared_on").toLocalDate());
				quizScores.setStatus(resultSet.getString("status"));
				module.setModuleId(resultSet.getInt("module_id"));
				module.setModuleName(resultSet.getString("module_name"));
				quizScores.setModule(module);
				scoresList.add(quizScores);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return scoresList;
	}

	@Override
	public List<QuizChartAnalysis> fetchQuizAnalysis(int userId) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
