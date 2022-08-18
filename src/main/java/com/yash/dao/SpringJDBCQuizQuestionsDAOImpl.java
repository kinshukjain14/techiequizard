package com.yash.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yash.entities.Module;
import com.yash.exception.DAOException;
import com.yash.helper.QuizQuestionsSQLQueries;

@Repository("springJDBCQuizQuestionsDAOImpl")
public class SpringJDBCQuizQuestionsDAOImpl implements QuizQuestionsDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private QuizQuestionsSQLQueries queries;
	
	@Autowired@Qualifier("questionMapper")
	private ResultSetExtractor<Module> questionMapper;
	
	@Autowired@Qualifier("moduleMapper")
	private RowMapper<Module> moduleMapper;
	
	@Override
	public Module retriveModuleQuestions(int moduleId) throws DAOException {
//		moduleName=moduleName.toUpperCase();
		Module module = jdbcTemplate.query(queries.getSelectModuleBasedOnName(),new Object[] {moduleId} ,questionMapper);
		return module;
	}

	@Override
	public List<Module> retrieveAllModules() throws DAOException {
		List<Module> moduleList = jdbcTemplate.query(queries.getSelectAllModules(), moduleMapper);
		return moduleList;
	}

}
