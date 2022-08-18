package com.yash.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.yash.entities.Module;
import com.yash.exception.DAOException;

@Repository("hibernateQuizQuestionsDAOImpl")
public class HibernateQuizQuestionsDAOImpl implements QuizQuestionsDAO {

	@Autowired@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public Module retriveModuleQuestions(int moduleId) throws DAOException {
		Session session = sessionFactory.openSession();
		Query<?> query = session.createQuery("from Module o where o.moduleId=:moduleId");
		query.setParameter("moduleId",moduleId);
		Module module = (Module) query.getSingleResult();
		session.close();
		return module;
	}

	@Override
	public List<Module> retrieveAllModules() throws DAOException {
		Session session = sessionFactory.openSession();
		Query<?> query = session.createQuery("from Module o");
		List<Module> modulesList = (List<Module>) query.getResultList();
		modulesList.forEach(x->x.setQuestions(null));
		return modulesList;
	}
	
	
	
}
