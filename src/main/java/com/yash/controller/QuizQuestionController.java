package com.yash.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yash.exception.QuestionParsingException;
import com.yash.model.ModuleDataModel;
import com.yash.model.ModulesData;
import com.yash.service.QuizServices;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*",allowedHeaders = "*",allowCredentials = "true",methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
@EnableAspectJAutoProxy
public class QuizQuestionController {
	@Autowired
	private QuizServices quizServices;
	
//	@Autowired
//	private UserSession userSession;
	
	@GetMapping("modules/{moduleId}")
	public ResponseEntity<ModuleDataModel> handleQuizQuestionsRequest(
			@PathVariable("moduleId") int moduleId,
			HttpServletRequest request)
	{
		try {
			ModuleDataModel moduleQuestions = quizServices.getModuleQuestions(moduleId);
			HttpSession session = request.getSession(false);
//			System.out.println(session);
//			System.out.println(session.getAttribute("userModelResponse"));
//			System.out.println(userSession.getAttribute("userModelResponse"));
		
			if(!moduleQuestions.getQuestionsList().isEmpty()) {
//				userSession.setAttribute("moduleDataModel", moduleQuestions);
				session.setAttribute("moduleDataModel", moduleQuestions);
				return new ResponseEntity<ModuleDataModel>(moduleQuestions,HttpStatus.OK);				
			}
			return new ResponseEntity<ModuleDataModel>(moduleQuestions,HttpStatus.NO_CONTENT);				
			
		} catch (QuestionParsingException e) {
			e.printStackTrace();
			return new ResponseEntity<ModuleDataModel>(new ModuleDataModel(null),HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("modules")
	public ResponseEntity<List<ModulesData>> handleGetAllModules(){
		List<ModulesData> modulesList = quizServices.getAllModules();
		if(!modulesList.isEmpty()) {
			return new ResponseEntity<List<ModulesData>>(modulesList,HttpStatus.OK);
		}
		return new ResponseEntity<List<ModulesData>>(modulesList,HttpStatus.NO_CONTENT);
	}
	
}
