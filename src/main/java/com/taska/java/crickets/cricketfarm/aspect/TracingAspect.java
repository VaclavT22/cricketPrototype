package com.taska.java.crickets.cricketfarm.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TracingAspect {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@AfterThrowing(pointcut = "com.taska.java.crickets.cricketfarm.aspect.SystemArchitecture.Repository()",
			throwing = "ex")
	public void logRepositoryException(Exception ex) {
		
	}
	
//	@Before("execution(@org.springframework.stereotype.Repository * *(..))")
//	public void logRepositoryInfo(JoinPoint joinPoint) {
//		System.out.println("Repository call " + joinPoint.getStaticPart().getClass().toString());
//		logger.info("Repository call " + joinPoint.getStaticPart().getClass().toString());
//	}
	
	@Before("execution(* com.taska.java.crickets.cricketfarm..*Service*.*(..))")
	public void logServiceInfo(JoinPoint joinPoint) {
		logger.info("Service call for: " + joinPoint.getStaticPart().getSignature().toString());
	}
	
	@Before("execution(* com.taska.java.crickets.cricketfarm..*Controller.*(..))")
	public void logControllerInfo(JoinPoint joinPoint) {
		logger.info("Controller call for: " + joinPoint.getStaticPart().getSignature().toString());
	}
	
	
}
