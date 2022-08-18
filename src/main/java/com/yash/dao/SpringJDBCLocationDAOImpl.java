package com.yash.dao;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yash.entities.Cities;
import com.yash.entities.Countries;
import com.yash.entities.States;
import com.yash.exception.DAOException;
import com.yash.helper.LocationSQLQueries;

@Repository("springJDBCLocationDAOImpl")
public class SpringJDBCLocationDAOImpl implements LocationDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private LocationSQLQueries locationSQLQueries;
	
	@Autowired@Qualifier("countriesMapper")
	private RowMapper<Countries> countriesMapper;
	@Autowired@Qualifier("statesMapper")
	private RowMapper<States> statesMapper;
	@Autowired@Qualifier("citiesMapper")
	private RowMapper<Cities> citiesMapper;
	
	@Override
	public List<Countries> getAllCountries() throws DAOException {
		List<Countries> countriesList = jdbcTemplate.query(locationSQLQueries.getSelectcountresQuery(), countriesMapper);
		return countriesList;
	}

	@Override
	public List<States> getAllStates(int countryId) throws DAOException {
		List<States> statesList = jdbcTemplate.query(locationSQLQueries.getSelectstatesQuery(),new Object[] {countryId},statesMapper);
		return statesList;
	}

	@Override
	public List<Cities> getAllCities(int stateId) throws DAOException {
		List<Cities> citiesList = jdbcTemplate.query(locationSQLQueries.getSelectcitiesQuiery(),new Object[] {stateId},citiesMapper);
		return citiesList;
	}

}
