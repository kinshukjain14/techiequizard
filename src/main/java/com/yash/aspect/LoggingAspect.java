package com.yash.aspect;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect
{
	
	Logger logger = Logger.getLogger("LoggingAspect");
	
	@AfterThrowing(pointcut = "execution(* com.yash.*.*.*(..))",throwing = "error")
	public void afterThrowing(JoinPoint joinPoint,Throwable error) {
		logger.log(Level.SEVERE,"After throwing aspect -- "+joinPoint.getSignature().getName()+" Error :- "+error);		
	}
	
	@Pointcut(value = "execution(* com.yash.controller.*.*(..))")
	public void getPointCut() {
	}
	
	@Around(value = "getPointCut()")
	public Object aroundAdvice(ProceedingJoinPoint joinPoint) {
		logger.log(Level.INFO,"-- "+joinPoint.getSignature().getName()+" execution started --");
		Object proceed = null;
		try {
			 proceed = joinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		logger.log(Level.INFO," Value Returned : "+proceed);
		logger.log(Level.INFO,"-- "+joinPoint.getSignature().getName()+" execution finished --");
		return proceed;
	}
}
