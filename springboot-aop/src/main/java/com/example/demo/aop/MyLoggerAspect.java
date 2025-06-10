package com.example.demo.aop;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component // 被 Spring 來管理
@Aspect // 切面程式
@Order(1) // 調用順序
public class MyLoggerAspect {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	// 建立一個切入點邏輯方法
	@Pointcut(value = "execution(public Integer com.example.demo.proxy.CalcImpl.add(Integer, Integer))")
	public void ptAdd() {}
	
	@Pointcut(value = "execution(public Integer com.example.demo.proxy.CalcImpl.div(Integer, Integer))")
	public void ptDiv() {}
	
	@Pointcut(value = "execution(public Integer com.example.demo.proxy.CalcImpl.*(..))")
	public void ptAll() {}
	
	// 前置通知(Advice)
	//@Before(value = "execution(public Integer com.example.demo.proxy.CalcImpl.add(Integer, Integer))")
	//@Before(value = "execution(public Integer com.example.demo.proxy.CalcImpl.*(Integer, Integer))")
	//@Before(value = "execution(public Integer com.example.demo.proxy.CalcImpl.*(..))")
	//@Before(value = "execution(* com.example.demo.proxy.CalcImpl.*(..))")
	//@Before(value = "execution(* com.example.demo.proxy.*.*(..))")
	//@Before(value = "execution(* *(..))")
	//@Before(value = "ptAdd()")
	//@Before(value = "ptAdd() || ptDiv()")
	@Before(value = "ptAll() && !ptDiv()") // 切入點表達式支援邏輯運算子: &&, ||, !
	public void beforeAdvice(JoinPoint joinPoint) {
		String threadName = Thread.currentThread().getName();
		String methodName = joinPoint.getSignature().getName(); // 方法名稱
		Object[] args = joinPoint.getArgs(); // 方法參數
		String dateTime = sdf.format(new Date());
		// log 紀錄
		System.out.printf("Log 前置通知[%s]: %s %s %s %n", threadName, dateTime, methodName, Arrays.toString(args));
	}
	
	@After(value = "ptAdd()")
	public void endAdvice(JoinPoint joinPoint) {
		String threadName = Thread.currentThread().getName();
		String methodName = joinPoint.getSignature().getName(); // 方法名稱
		String dateTime = sdf.format(new Date());
		System.out.printf("End 前置通知[%s]: %s %s %n", threadName, dateTime, methodName);
	}
	
}
