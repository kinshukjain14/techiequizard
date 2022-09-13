package com.yash.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yash.model.ModuleDataModel;
import com.yash.model.QuizResultModel;
import com.yash.model.QuizScoreModel;
import com.yash.model.UserResponse;
import com.yash.service.QuizScoreServices;

@RestController
@RequestMapping("store")
@CrossOrigin(origins = "*",allowedHeaders = "*",allowCredentials = "true",methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
@EnableAspectJAutoProxy
public class QuizScoreController {
	
	@Autowired
	private QuizScoreServices quizScoreServices;
	
//	@Autowired
//	private UserSession userSession;
	
	@RequestMapping(path = "scores/{userId}",method = RequestMethod.GET,produces = "application/json")
	public ResponseEntity<String> getAllQuizScores(@PathVariable("userId") int userId) {
		String quizScore = quizScoreServices.getQuizScore(userId);
		if(quizScore.equals("{}")) {
			return new ResponseEntity<String>(quizScore,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(quizScore,HttpStatus.OK);
	}
	
	@RequestMapping(path = "analysis/{userId}",method = RequestMethod.GET,produces = "application/json")
	public ResponseEntity<String> getQuizAnalysis(@PathVariable("userId") int userId){
		String quizAnalysis = quizScoreServices.getQuizAnalysis(userId);
		if(quizAnalysis.equals("{}")) {
			return new ResponseEntity<String>(quizAnalysis,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(quizAnalysis,HttpStatus.OK);
	}
	
	@PostMapping("scores")
	public ResponseEntity<QuizResultModel> saveQuizScore(
			@RequestParam("correct") int correct,
			@RequestParam("totalAttempt") int totalAttempt,
			@RequestParam("totalQuestions") int totalQuestions,
			@RequestParam("timeTaken") int timeTaken,
			@RequestParam("candidateID") Long candidateID,
			HttpServletRequest request
			) 
	{
		
		HttpSession session = request.getSession(false);
		ModuleDataModel moduleDetails = (ModuleDataModel) session.getAttribute("moduleDataModel");
		UserResponse userData = (UserResponse) session.getAttribute("userModelResponse");
		
//		ModuleDataModel moduleDetails = (ModuleDataModel)userSession.getAttribute("moduleDataModel");
//		UserResponse userData = (UserResponse) userSession.getAttribute("userModelResponse");
		
//		ModuleDataModel moduleDetails = new ModuleDataModel(null);
//		UserResponse userData = new UserResponse();
//		userData.setUserId(11232);
//		userData.setFirstName("A");
//		userData.setLastName("K");
//		moduleDetails.setModuleId(1);
//		moduleDetails.setModuleName("JAVA");
		
		Integer userId = userData.getUserId() ;
		String candidateName =userData.getFirstName()+" "+userData.getLastName();
		int moduleId = moduleDetails.getModuleId();
		String moduleName = moduleDetails.getModuleName();
		
		QuizScoreModel model = new QuizScoreModel();
		model.setUserId(userId);
		model.setCandidateId(candidateID);
		model.setCandidateName(candidateName);
		model.setModuleId(moduleId);
		model.setModuleName(moduleName);
		model.setTotalAttempt(totalAttempt);
		model.setCorrectAttempt(correct);
		model.setUnattempted(totalQuestions-totalAttempt);
		model.setTotalQuestions(totalQuestions);
		model.setTimeTaken(timeTaken);
		
		QuizResultModel quizResultModel = quizScoreServices.saveQuizScore(model);
		if(quizResultModel.getCandidateId() != null) {
			return new ResponseEntity<QuizResultModel>(quizResultModel,HttpStatus.OK);			
		}
		else {
			return new ResponseEntity<QuizResultModel>(quizResultModel,HttpStatus.EXPECTATION_FAILED);
		}
	}
	
}
