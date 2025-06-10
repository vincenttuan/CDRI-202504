package com.example.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(1)
public class AccessAspect {
	
	@Autowired
	private AccessController accessController;
	
	//@Around(value = "execution(* com.example.demo.aop.FloorService.enterFloor(..))")
	@Around(value = "execution(* com.example.demo.aop.FloorServiceImpl.enterFloor(..))")
	public Object checkAccess(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = null;
		
		Object[] args = joinPoint.getArgs();
		String username = (String)args[0];
		int floor = (int)args[1];
		
		if(accessController.hasAccess(username, floor)) {
			System.out.printf("[AOP] %s 通過門禁, 進入 %d 樓%n", username, floor);
			result = joinPoint.proceed();
			return result;
		} else {
			System.err.printf("[AOP] %s 未授權進入 %d 樓%n", username, floor);
			throw new SecurityException("未授權進入該層");
		}
		
	}
	
}
