package com.yash.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Matchers.*;

import com.yash.controller.LocationController;
import com.yash.main.Application;
import com.yash.model.CountriesModel;
import com.yash.model.StatesModel;
import com.yash.model.CitiesModel;
import com.yash.service.LocationService;


@AutoConfigureMockMvc
@WebMvcTest
@ContextConfiguration(classes = { Application.class })
class TestLocationController {

	@Mock
	private LocationService locationService;
	
	@InjectMocks
	private LocationController locationController;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(locationController).build();
	}

	@DisplayName("Test Get all countries : Positive")
	@Test
	void testGetAllCountries_Positive() {
		when(locationService.getCountries()).then((invocation)->{
			List<CountriesModel> countriesList = new ArrayList<>();
			CountriesModel countriesModel = new CountriesModel();
			countriesList.add(countriesModel);
			countriesList.add(countriesModel);
			countriesList.add(countriesModel);
			countriesList.add(countriesModel);
			return countriesList;
		});
		
		ResponseEntity<List<CountriesModel>> allCountries = locationController.getAllCountries();
		List<CountriesModel> body = allCountries.getBody();
		assertTrue(body.size()!=0);
	}
	
	@DisplayName("Test Get all countries : Negative")
	@Test
	void testGetAllCountries_negative() {
		when(locationService.getCountries()).then((invocation)->{
			List<CountriesModel> countriesList = new ArrayList<>();
			return countriesList;
		});
		
		ResponseEntity<List<CountriesModel>> allCountries = locationController.getAllCountries();
		List<CountriesModel> body = allCountries.getBody();
		assertTrue(body.size()==0);
	}
	
	@DisplayName("Test Get all states : Positive")
	@Test
	void testGetAllStates_positive() {
		when(locationService.getStates(anyInt())).then((invocation)->{
			List<StatesModel> statesList = new ArrayList<>();
			StatesModel statesModel = new StatesModel();
			statesList.add(statesModel);
			statesList.add(statesModel);
			statesList.add(statesModel);
			return statesList;
		});
		
		ResponseEntity<List<?>> responseEntity = locationController.getLocationData("country", 1);
		List<StatesModel> body = (List<StatesModel>) responseEntity.getBody();
		assertTrue(body.size() > 1);
	}
	
	@DisplayName("Test Get all states : Negative")
	@Test
	void testGetAllStates_negative() {
		when(locationService.getStates(anyInt())).then((invocation)->{
			List<StatesModel> statesList = new ArrayList<>();
			return statesList;
		});
		
		ResponseEntity<List<?>> responseEntity = locationController.getLocationData("country", 1);
		List<StatesModel> body = (List<StatesModel>) responseEntity.getBody();
		assertTrue( body.size() <= 1 );
	}
	
	@DisplayName("Test Get all cities : Positive")
	@Test
	void testGetAllCities_positive() {
		when(locationService.getStates(anyInt())).then((invocation)->{
			List<CitiesModel> citiesList = new ArrayList<>();
			CitiesModel citiesModel = new CitiesModel();
			citiesList.add(citiesModel);
			citiesList.add(citiesModel);
			citiesList.add(citiesModel);
			citiesList.add(citiesModel);
			return citiesList;
		});
		
		ResponseEntity<List<?>> responseEntity = locationController.getLocationData("country", 1);
		List<CitiesModel> body = (List<CitiesModel>) responseEntity.getBody();
		assertTrue( body.size() > 1 );
	}
	
	@DisplayName("Test Get all cities : Negative")
	@Test
	void testGetAllCities_negative() {
		when(locationService.getStates(anyInt())).then((invocation)->{
			List<CitiesModel> citiesList = new ArrayList<>();
			
			return citiesList;
		});
		
		ResponseEntity<List<?>> responseEntity = locationController.getLocationData("country", 1);
		List<CitiesModel> body = (List<CitiesModel>) responseEntity.getBody();
		assertTrue( body.size() <= 1 );
	}
	
	@DisplayName("Test Get all countries URI : Positive")
	@Test
	void testGetAllCountriesURI_Positive() {
		when(locationService.getCountries()).then((invocation)->{
			List<CountriesModel> countriesList = new ArrayList<>();
			CountriesModel countriesModel = new CountriesModel();
			countriesList.add(countriesModel);
			countriesList.add(countriesModel);
			countriesList.add(countriesModel);
			countriesList.add(countriesModel);
			return countriesList;
		});
		
		try {
			mockMvc.perform(get("/location/data")).andExpect(status().isOk()).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@DisplayName("Test Get all countries URI : Negative")
	@Test
	void testGetAllCountriesURI_negative() {
		when(locationService.getCountries()).then((invocation)->{
			List<CountriesModel> countriesList = new ArrayList<>();
			
			return countriesList;
		});
		try {
			mockMvc.perform(get("/location/data")).andExpect(status().isNotFound()).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@DisplayName("Test Get all states URI : Positive")
	@Test
	void testGetAllStatesURI_positive() {
		when(locationService.getStates(anyInt())).then((invocation)->{
			List<StatesModel> statesList = new ArrayList<>();
			StatesModel statesModel = new StatesModel();
			statesList.add(statesModel);
			statesList.add(statesModel);
			statesList.add(statesModel);
			return statesList;
		});
		try {
			mockMvc.perform(get("/location/data/{type}/{code}","country",1)).andExpect(status().isOk()).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@DisplayName("Test Get all cities URI : Positive")
	@Test
	void testGetAllCitiesURI_positive() {
		when(locationService.getStates(anyInt())).then((invocation)->{
			List<CitiesModel> citiesList = new ArrayList<>();
			CitiesModel citiesModel = new CitiesModel();
			citiesList.add(citiesModel);
			citiesList.add(citiesModel);
			citiesList.add(citiesModel);
			return citiesList;
		});
		try {
			mockMvc.perform(get("/location/data/{type}/{code}","state",1)).andExpect(status().isOk()).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
