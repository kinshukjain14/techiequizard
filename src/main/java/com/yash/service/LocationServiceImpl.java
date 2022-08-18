package com.yash.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yash.dao.LocationDAO;
import com.yash.entities.Cities;
import com.yash.entities.Countries;
import com.yash.entities.States;
import com.yash.exception.DAOException;
import com.yash.model.CitiesModel;
import com.yash.model.CountriesModel;
import com.yash.model.StatesModel;

@Service("locationServiceImpl")
public class LocationServiceImpl implements LocationService {

//	@Autowired@Qualifier("jDBCLocationDAOImpl")
//	@Autowired@Qualifier("hibernateLocationDAOImpl")
	@Autowired@Qualifier("springJDBCLocationDAOImpl")
	private LocationDAO locationDAO;
	
	public LocationServiceImpl() {
		
	}
	
	@Override
	public List<CountriesModel> getCountries() {
		List<CountriesModel> countriesList = new ArrayList<>();
		try {
			List<Countries> allCountries = locationDAO.getAllCountries();
			allCountries.forEach((x)->{
				CountriesModel model = new CountriesModel();
				model.setCountryId(x.getCountryId());
				model.setCountryName(x.getCountryName());
				model.setShortName(x.getShortName());
				model.setPhoneCode(x.getPhoneCode());
				countriesList.add(model);
			});
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return countriesList;
	}

	@Override
	public List<StatesModel> getStates(int countryId) {
		List<StatesModel> statesList = new ArrayList<>();
		try {
			List<States> allStates = locationDAO.getAllStates(countryId);
			allStates.forEach((x)->{
				StatesModel model = new StatesModel();
				model.setStateId(x.getStateId());
				model.setStateName(x.getStateName());
				model.setCountryId(x.getCountryId());
				statesList.add(model);
			});
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return statesList;
	}

	@Override
	public List<CitiesModel> getCities(int stateId) {
		List<CitiesModel> citiesList = new ArrayList<>();
		try {
			List<Cities> allCities = locationDAO.getAllCities(stateId);
			allCities.forEach((x)->{
				CitiesModel model = new CitiesModel();
				model.setCityId(x.getCityId());
				model.setCityName(x.getCityName());
				model.setStateId(x.getStateId());
				citiesList.add(model);
			});
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return citiesList;
	}

}
