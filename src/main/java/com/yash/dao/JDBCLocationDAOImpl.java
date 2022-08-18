package com.yash.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yash.entities.Cities;
import com.yash.entities.Countries;
import com.yash.entities.States;
import com.yash.exception.DAOException;
import com.yash.integrate.ConnectionManager;

@Repository("jDBCLocationDAOImpl")
public class JDBCLocationDAOImpl implements LocationDAO {
	
	@Autowired
	private ConnectionManager connectionManager;

	@Override
	public List<Countries> getAllCountries() throws DAOException {
		List<Countries> countriesList = new ArrayList<>();
		try(Connection connection = connectionManager.openConnection()){
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from countries");
			while(resultSet.next()) {
				Countries countries = new Countries(resultSet.getInt("id"),
						resultSet.getString("shortname"), 
						resultSet.getString("name"), 
						resultSet.getInt("phonecode"));
				countriesList.add(countries);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new DAOException(e,"DAO Exception : unable to fetch countries");
		}
		return countriesList;
	}

	@Override
	public List<States> getAllStates(int countryId) throws DAOException {
		List<States> statesList = new ArrayList<>();
		try(Connection connection = connectionManager.openConnection()){
			PreparedStatement statement = connection.prepareStatement("select * from states where country_id=?");
			statement.setInt(1, countryId);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				States states = new States(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("country_id"));
				statesList.add(states);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new DAOException(e,"DAO Exception : unable to fetch states");
		}
		return statesList;
	}

	@Override
	public List<Cities> getAllCities(int stateId) throws DAOException {
		List<Cities> statesList = new ArrayList<>();
		try(Connection connection = connectionManager.openConnection()){
			PreparedStatement statement = connection.prepareStatement("select * from cities where state_id=?");
			statement.setInt(1, stateId);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				Cities cities = new Cities(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("state_id"));
				statesList.add(cities);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new DAOException(e,"DAO Exception : unable to fetch cities");
		}
		return statesList;
	}

}
	