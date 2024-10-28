package com.yedam.app.aop.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect // AOP의 설정
@Component
public class BeforeAdvice {
	// 조인포인트 중에서 Advice(횡단관심, 부가기능)이 적용될 메소드
	//@Pointcut("execution(* com.yedam..*Impl.*(..))")
	@Pointcut("within(com.yedam.app.emp.service.impl.*)")
	public void appPointcut() {}
	
	@Before("appPointcut()") // 동작시점
	public void beforeLog(JoinPoint jp) { // 어드바이스
		String methodName = jp.getSignature().getName();
		System.out.println("[사전처리] beforLog " + methodName);
	}
	
}
