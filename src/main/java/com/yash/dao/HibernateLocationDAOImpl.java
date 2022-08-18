package com.yash.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.yash.entities.Cities;
import com.yash.entities.Countries;
import com.yash.entities.States;
import com.yash.exception.DAOException;

@Repository("hibernateLocationDAOImpl")
public class HibernateLocationDAOImpl implements LocationDAO {

	@Autowired@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public List<Countries> getAllCountries() throws DAOException {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Countries o");
		List<Countries> countriesList = (List<Countries>) query.getResultList();
		session.close();
		return countriesList;
	}

	@Override
	public List<States> getAllStates(int countryId) throws DAOException {
		Session session = sessionFactory.openSession();
		Query<?> query = session.createQuery("from States o where o.countryId=:id");
		query.setParameter("id", countryId);
		List<States> statesList = (List<States>) query.getResultList();
		session.close();
		return statesList;
	}

	@Override
	public List<Cities> getAllCities(int stateId) throws DAOException {
		Session session = sessionFactory.openSession();
		Query<?> query = session.createQuery("from Cities o where o.stateId=:id");
		query.setParameter("id", stateId);
		List<Cities> citiesList = (List<Cities>) query.getResultList();
		session.close();
		return citiesList;
	}

}
