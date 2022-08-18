package com.yash.dao;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.yash.entities.Authorities;
import com.yash.entities.User;
import com.yash.entities.UserCredentials;
import com.yash.exception.DAOException;
import com.yash.exception.UserAlreadyExistException;

@Repository("hibernateUserDAOImpl")
public class HibernateUserDAOImpl implements UserDAO {

	@Autowired@Qualifier("sessionFactory")
	SessionFactory sessionFactory;
	
	@Override
	public Optional<User> checkUserCredentials(String userName, String password) throws DAOException {
		Session session = sessionFactory.openSession();
		Optional<User> userResponse = Optional.empty();
		Query<?> query = session.createNamedQuery("CheckUserCredentials");
		query.setParameter("id", userName);
		query.setParameter("password", password);
		UserCredentials userCredentials = (UserCredentials) query.getSingleResult();
		session.close();
		if(userCredentials.getUserId() != 0) {
			userResponse = requestUserResponse(userCredentials.getUserId());
		}
		return userResponse;
	}

	@Override
	public Optional<User> requestUserResponse(int userId) throws DAOException {
		Session session = sessionFactory.openSession();
		User user = (User)session.get(User.class, userId);
		Query<?> query = session.createQuery("from UserCredentials o where o.userId=:id");
		query.setParameter("id", userId);
		UserCredentials uc =  (UserCredentials) query.getSingleResult();
		user.setUserCredentials(uc);
		session.close();
		Optional<User> optional = Optional.of(user);
		return optional;
	}

	@Override
	public boolean registerUser(User user) throws DAOException, UserAlreadyExistException {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.getTransaction();
		UserCredentials userData = new UserCredentials();
		
		userData.setUserId(user.getUserId());
		userData.setUsername(user.getUserCredentials().getUsername());
		userData.setPassword(user.getUserCredentials().getPassword());
		userData.setAuthorities(user.getUserCredentials().getAuthorities());
		
		transaction.begin();
		session.save(user);
		session.save(userData);
		session.save(userData.getAuthorities());
		transaction.commit();
		User userCheck = (User)session.get(User.class, user.getUserId());
		Query<?> query = session.createQuery("from UserCredentials o where o.userId=:id");
		query.setParameter("id", user.getUserId());
		UserCredentials userCredentials =  (UserCredentials) query.getSingleResult();
		session.close();
		if(userCheck.getUserId() == user.getUserId()
				&& userCredentials.getUserId() == userData.getUserId()
				&& userCredentials.getAuthorities().getUsername().equals(userData.getAuthorities().getUsername())
				) 
		{
				return true;
		}
		return false;
	}

	@Override
	public boolean checkContactNumberAvailable(Long contactNo) {
		Session session = sessionFactory.openSession();
		Query<?> query = session.createNamedQuery("CheckUserContactAvailable");
		query.setParameter("contactNo", contactNo);
		List<User> usersList =  (List<User>) query.getResultList();
		session.close();
		if(usersList.isEmpty()) {
			return false;
		}
		if(usersList.get(0).getContactNo().longValue() == contactNo.longValue()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkEmailAvailable(String emailId) {
		Session session = sessionFactory.openSession();
		Query<?> query = session.createNamedQuery("CheckUserEmailAvailable");
		query.setParameter("email", emailId);
		List<User> usersList =  (List<User>) query.getResultList();
		session.close();
		if(usersList.isEmpty()) {
			return false;
		}
		if(usersList.get(0).getEmail().equals(emailId)) {
			return true;
		}
		return false;
	}

	@Override
	public Optional<User> updateProfile(User user) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.getTransaction();
		UserCredentials uc = user.getUserCredentials();
		
		transaction.begin();
		User updateduser = (User) session.merge(user);
		UserCredentials userCredentials = (UserCredentials) session.merge(uc);
		transaction.commit();
		
		Optional<User> userData = Optional.empty();
		if(updateduser.getUserId() == user.getUserId() && uc.getUsername().equals(userCredentials.getUsername())) 
		{
			updateduser.setUserCredentials(userCredentials);
			System.out.println("Updated User :"+updateduser);
			userData = Optional.of(updateduser);
		}
		session.close();
		
		return userData;
	}

	@Override
	public boolean updatePassword(User user) {
		
		return false;
	}

}
