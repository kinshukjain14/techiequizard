package com.yash.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.yash.entities.Module;
import com.yash.entities.Option;
import com.yash.entities.Question;

@Component("questionMapper")
public class QuestionMapper implements ResultSetExtractor<Module>{

	@Override
	public Module extractData(ResultSet rs) throws SQLException, DataAccessException {
		Module module=null ;
		List<Question> qList=new ArrayList<Question>();			
		int module_id=0;
		String moduleName = "";
		String moduleDescription=null;
		while(rs.next())
		{
			int ques_id = rs.getInt("ques_id");
			module_id = rs.getInt("module_id");
			moduleName = rs.getString("module_name");
			moduleDescription=rs.getString("module_desc");
			String ques_desc=rs.getString("ques_desc");
			Question question = new Question();
			question.setQuestionId(ques_id);
			question.setQuestions(ques_desc);
			List<Option> optionList = new ArrayList<>();
//			Option op = new Option();
				do
				{
					Option op = new Option();
					int tempId = rs.getInt("ques_id");
					int option_id = rs.getInt("option_id");						
					String option_desc=rs.getString("option_desc");
					int check = rs.getInt("Correct");
					
					if(tempId == ques_id) 
					{
						op.setOptionId(option_id);
						op.setOptionDescription(option_desc);
						op.setCorrect(check==1);
						optionList.add(op);
//						op.setOption(option_desc, check==1);
//						question.setOption(op);
						if(rs.isLast()) {
							question.setOptionsList(optionList);
							qList.add(question);
						}
						continue;
					}
					else 
					{
						question.setOptionsList(optionList);
						qList.add(question);
						rs.previous();
						break;
					}
				}while(rs.next());
		}
		
		module = new Module();
		module.setModuleId(module_id);
		module.setModuleName(moduleName);
		module.setModuleDescription(moduleDescription);
		module.setQuestions(qList);
		
		return module;
	}


}
