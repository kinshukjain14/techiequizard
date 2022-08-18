package com.yash.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yash.entities.Module;
import com.yash.entities.Option;
import com.yash.entities.Question;
import com.yash.exception.DAOException;
import com.yash.helper.QuizQuestionsSQLQueries;
import com.yash.integrate.ConnectionManager;

@Repository("jDBCQuizQuestionsDAOImpl")
public class JDBCQuizQuestionsDAOImpl implements QuizQuestionsDAO{
	@Autowired
	private ConnectionManager connectionManager;
	
	@Autowired
	private QuizQuestionsSQLQueries quizQuestionsSQLQueries;
	
	@Override
	public Module retriveModuleQuestions(int moduleId) throws DAOException 
	{
//		moduleName=moduleName.toUpperCase();
		
		Module module=null ;
		try(Connection connection = connectionManager.openConnection())
		{
			CallableStatement statement = connection.prepareCall(quizQuestionsSQLQueries.getSelectModuleBasedOnName(),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			statement.setInt(1, moduleId);
			ResultSet resultSet = statement.executeQuery();	
			List<Question> qList=new ArrayList<Question>();			
			int module_id=0;
			String moduleName="";
			String moduleDescription=null;
			while(resultSet.next())
			{
				int ques_id = resultSet.getInt("ques_id");
				module_id = resultSet.getInt("module_id");
				moduleName = resultSet.getString("module_name");
				moduleDescription=resultSet.getString("module_desc");
				String ques_desc=resultSet.getString("ques_desc");
				Question question = new Question();
				question.setQuestionId(ques_id);
				question.setQuestions(ques_desc);
				List<Option> optionList = new ArrayList<>();
//				Option op = new Option();
					do
					{
						Option op = new Option();
						int tempId = resultSet.getInt("ques_id");
						int option_id = resultSet.getInt("option_id");						
						String option_desc=resultSet.getString("option_desc");
						int check = resultSet.getInt("Correct");
						
						if(tempId == ques_id) 
						{
							op.setOptionId(option_id);
							op.setOptionDescription(option_desc);
							op.setCorrect(check==1);
							optionList.add(op);
//							op.setOption(option_desc, check==1);
//							question.setOption(op);
							if(resultSet.isLast()) {
								question.setOptionsList(optionList);
								qList.add(question);
							}
							continue;
						}
						else 
						{
							question.setOptionsList(optionList);
							qList.add(question);
							resultSet.previous();
							break;
						}
					}while(resultSet.next());
			}
			
			module = new Module();
			module.setModuleId(module_id);
			module.setModuleName(moduleName);
			module.setModuleDescription(moduleDescription);
			module.setQuestions(qList);
			
		} catch (ClassNotFoundException | SQLException e) 
		{
			throw new DAOException(e, "DAO Exception : unable to load questions");
		}
		return module;
	}

	@Override
	public List<Module> retrieveAllModules() throws DAOException {
		List<Module> modulesList = new ArrayList<>();
		try(Connection connection = connectionManager.openConnection()){
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(quizQuestionsSQLQueries.getSelectAllModules());
			while(resultSet.next()) {
				Module module = new Module();
				module.setModuleId(resultSet.getInt("module_id"));
				module.setModuleName(resultSet.getString("module_name"));
				module.setModuleDescription(resultSet.getString("module_desc"));
				module.setQuestions(null);
				modulesList.add(module);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new DAOException(e, "DAO Exception : unable to load modules");
		}
		return modulesList;
	}

}
