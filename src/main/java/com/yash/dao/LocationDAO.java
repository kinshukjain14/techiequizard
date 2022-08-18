package com.yash.dao;

import java.util.List;

import com.yash.entities.Cities;
import com.yash.entities.Countries;
import com.yash.entities.States;
import com.yash.exception.DAOException;

public interface LocationDAO {
	public List<Countries> getAllCountries() throws DAOException;
	public List<States> getAllStates(int countryId) throws DAOException ;
	public List<Cities> getAllCities(int stateId) throws DAOException;
}
