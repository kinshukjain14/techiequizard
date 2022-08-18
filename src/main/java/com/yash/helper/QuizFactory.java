package com.yash.helper;

import com.yash.dao.JDBCLocationDAOImpl;
import com.yash.dao.JDBCQuizQuestionsDAOImpl;
import com.yash.dao.JDBCQuizScoreDAOImpl;
import com.yash.dao.JDBCUserDAOImpl;
import com.yash.dao.LocationDAO;
import com.yash.dao.QuizQuestionsDAO;
import com.yash.dao.QuizScoresDAO;
import com.yash.dao.UserDAO;
import com.yash.service.LocationService;
import com.yash.service.LocationServiceImpl;
import com.yash.service.QuizScoreServiceImpl;
import com.yash.service.QuizScoreServices;
import com.yash.service.QuizServices;
import com.yash.service.UserService;
import com.yash.service.UserServiceImpl;
import com.yash.service.QuizServicesImpl;

public class QuizFactory
{
	public static QuizQuestionsDAO newDAOInstance() {
//		return new XMLQuizQuestionsDAOImpl();
		return new JDBCQuizQuestionsDAOImpl();
	}
	
	public static QuizServices newServicesInstance() {
		return new QuizServicesImpl();
	}
	
	public static QuizScoresDAO newQuizScoresDAO() {
//		return new FileQuizScoreDAOImpl();
		return new JDBCQuizScoreDAOImpl();
	}
	
	public static QuizScoreServices newQuizScoreServices() {
		return new QuizScoreServiceImpl();
	}
	
	public static UserDAO newUserDao() {
//		return new MemoryUserDAOImpl();
		return new JDBCUserDAOImpl();
	}
	public static UserService newUserAuthService() {
		return new UserServiceImpl();
	}
	
	public static LocationDAO newLocationDaoInstance() {
		return new JDBCLocationDAOImpl();
	}
	
	public static LocationService newLocationService() {
		return new LocationServiceImpl();
	}
}
