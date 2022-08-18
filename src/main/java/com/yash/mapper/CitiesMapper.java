package com.yash.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.yash.entities.Cities;

@Component("citiesMapper")
public class CitiesMapper implements RowMapper<Cities>{

	@Override
	public Cities mapRow(ResultSet rs, int rowNum) throws SQLException {
		Cities cities = new Cities(rs.getInt("id"), rs.getString("name"), rs.getInt("state_id"));
		return cities;
	}

}
