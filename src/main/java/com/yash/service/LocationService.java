package com.yash.service;

import java.util.List;

import com.yash.model.CitiesModel;
import com.yash.model.CountriesModel;
import com.yash.model.StatesModel;

public interface LocationService {
	public List<CountriesModel> getCountries();
	List<StatesModel> getStates(int countryId);
	public List<CitiesModel> getCities(int stateId);
}
