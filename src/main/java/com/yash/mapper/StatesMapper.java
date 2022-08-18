package com.yash.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.yash.entities.States;

@Component("statesMapper")
public class StatesMapper implements RowMapper<States> {

	@Override
	public States mapRow(ResultSet rs, int rowNum) throws SQLException {
		States states = new States(rs.getInt("id"), rs.getString("name"), rs.getInt("country_id"));
		return states;
	}

}
