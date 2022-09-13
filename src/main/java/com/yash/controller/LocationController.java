package com.yash.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yash.model.CitiesModel;
import com.yash.model.CountriesModel;
import com.yash.model.StatesModel;
import com.yash.service.LocationService;

@RestController
@RequestMapping("location")
@CrossOrigin(origins = "*",allowedHeaders = "*",allowCredentials = "true",methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class LocationController {
	
	@Autowired@Qualifier("locationServiceImpl")
	LocationService locationService;
	
	@GetMapping("data/{type}/{code}")
	public ResponseEntity<List<?>> getLocationData(@PathVariable("type") String type,@PathVariable("code") Integer code){
		ResponseEntity<List<?>> responseList=null;
		if(type.equals("country")) {
			List<StatesModel> states = locationService.getStates(code);
			if(states.isEmpty()) {
				StatesModel model = new StatesModel();
				model.setStateId(-1);
				model.setStateName("Other");
				model.setCountryId(code);
				states.add(model);
			}	
			responseList = new ResponseEntity<List<?>>(states,HttpStatus.OK);
		}
		else if(type.equals("state")) {
			List<CitiesModel> cities = locationService.getCities(code);
			if(cities.isEmpty()) 
			{
				CitiesModel model = new CitiesModel();
				model.setCityId(-1);
				model.setCityName("Other");
				model.setStateId(code);
			}
			responseList = new ResponseEntity<List<?>>(cities,HttpStatus.OK);
		}
		else {
			responseList = new ResponseEntity<List<?>>(new ArrayList<>(),HttpStatus.NOT_FOUND);
		}
		return responseList;
	}
	
	@GetMapping("data")
	public ResponseEntity<List<CountriesModel>> getAllCountries(){
		List<CountriesModel> countries = locationService.getCountries();
		
		if(!countries.isEmpty()) {
			return new ResponseEntity<List<CountriesModel>>(countries,HttpStatus.OK);
		}
		return new ResponseEntity<List<CountriesModel>>(countries,HttpStatus.NOT_FOUND);
	}
	
}
