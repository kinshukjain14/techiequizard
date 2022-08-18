package com.yash.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.yash.dao.LocationDAO;
import com.yash.entities.Countries;
import com.yash.exception.DAOException;
import com.yash.model.CountriesModel;
import com.yash.service.LocationServiceImpl;

class TestLocationService {
	
	@Mock
	private LocationDAO dao;
	
	@InjectMocks
	private LocationServiceImpl service;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGetAllCountries_positive() throws DAOException {
		when(dao.getAllCountries()).then((invocation)->{
			Countries countries = new Countries(1, "IN", "India", 91);
			ArrayList<Countries> countriesList = new ArrayList<Countries>();
			countriesList.add(countries);
			countriesList.add(countries);
			countriesList.add(countries);
			countriesList.add(countries);
			
			return countriesList;
		});
		List<CountriesModel> countries = service.getCountries();
		assertTrue(countries.size()!=0);
	}
	
	@Test
	void testGetAllCountries_negative() throws DAOException {
		when(dao.getAllCountries()).then((invocation)->{
			Countries countries = new Countries(1, "IN", "India", 91);
			ArrayList<Countries> countriesList = new ArrayList<Countries>();
			
			return countriesList;
		});
		List<CountriesModel> countries = service.getCountries();
		assertTrue(countries.size()==0);
	}
	
	@Test
	void testGetAllCountries_exception() {
		
		try {
			when(dao.getAllCountries()).then((invocation)->{
				try {
					throw new RuntimeException();
				}
				catch(RuntimeException e) {
					throw new DAOException(e, "DAOException");
				}
			});
			List<CountriesModel> countries = service.getCountries();			
			assertTrue(true);
		}
		catch(DAOException e) {
			assertTrue(false);
		}
	}	

}
