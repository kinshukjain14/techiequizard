package com.yash.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.yash.entities.Countries;

@Component("countriesMapper")
public class CountriesMapper implements RowMapper<Countries> {

	@Override
	public Countries mapRow(ResultSet rs, int rowNum) throws SQLException {
		Countries countries = new Countries(rs.getInt("id"), rs.getString("shortname"), rs.getString("name"),
				rs.getInt("phonecode"));

		return countries;
	}

}
