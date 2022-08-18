package com.yash.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.yash.entities.Module;
import com.yash.entities.QuizChartAnalysis;
import com.yash.entities.QuizScores;
import com.yash.entities.User;
import com.yash.exception.DAOException;

@Repository("hibernateQuizScoreDAOImpl")
public class HibernateQuizScoreDAOImpl implements QuizScoresDAO {

	@Autowired@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public boolean saveQuizScores(QuizScores score) throws DAOException {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		session.persist(score);
		transaction.commit();
		QuizScores quizScores = (QuizScores)session.get(QuizScores.class, score.getCandidateId());
		session.close();
		if(quizScores.getCandidateId() == score.getCandidateId()) {
			return true;
		}
		return false;
	}

	@Override
	public List<QuizScores> fetchQuizScores(int userId) throws DAOException {
		Session session = sessionFactory.openSession();
		Query<?> query = session.createQuery("from QuizScores o where o.user.userId=?1");
		query.setParameter(1, userId);
		List<QuizScores> quizScoresList = (List<QuizScores>) query.getResultList();
		for (QuizScores quizScores : quizScoresList) 
		{
			Module module = quizScores.getModule();
			module.setQuestions(null);
		}
		session.close();
		return quizScoresList;
	}

	@Override
	public List<QuizChartAnalysis> fetchQuizAnalysis(int userId) throws DAOException {
		Session session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
		Root<QuizScores> root = criteriaQuery.from(QuizScores.class);
		criteriaQuery.multiselect(root.get("user"),root.get("module"),root.get("status"),builder.count(root.get("status")));
		criteriaQuery.groupBy(root.get("module"),root.get("status"));
		criteriaQuery.orderBy(builder.asc(root.get("module")));
		Query<Object[]> query = session.createQuery(criteriaQuery);
		List<Object[]> resultList = query.getResultList();
		List<QuizChartAnalysis> analysisList = new ArrayList<>();
		resultList.forEach((item)->{
			User user = (User)item[0];
			Module module = (Module)item[1];
			QuizChartAnalysis analysis = new QuizChartAnalysis();
			analysis.setUserId(user.getUserId());
			analysis.setModuleId(module.getModuleId());
			analysis.setModuleName(module.getModuleName());
			analysis.setStatus((String)item[2]);
			analysis.setCount((Long)item[3]);
			analysisList.add(analysis);
		});
		session.close();
		return analysisList;
	}

}
