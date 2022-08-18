package com.yash.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.yash.entities.Module;

@Component("moduleMapper")
public class ModuleMapper implements RowMapper<Module> {

	@Override
	public Module mapRow(ResultSet rs, int rowNum) throws SQLException {
		Module module = new Module();
		module.setModuleId(rs.getInt("module_id"));
		module.setModuleName(rs.getString("module_name"));
		module.setModuleDescription(rs.getString("module_desc"));
		module.setQuestions(null);
		return module;
	}

}
