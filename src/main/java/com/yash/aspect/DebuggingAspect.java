package com.yash.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

//@Aspect
//@Component
public class DebuggingAspect 
{
	
//	@Pointcut(value = "execution(* com.yash.controller.*.*(..))")
//	public void getPointCut() {
//	}
//	
//	@Around(value = "getPointCut()")
//	public Object aroundAdvice(ProceedingJoinPoint joinPoint) {
//		System.out.println("-- "+joinPoint.getSignature().getName()+" execution started --");
//		Object proceed = null;
//		try {
//			 proceed = joinPoint.proceed();
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
//		System.out.println(" Value Returned : "+proceed);
//		System.out.println("-- "+joinPoint.getSignature().getName()+" execution finished --");
//		return proceed;
//	}
}
