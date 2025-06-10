package com.example.demo.aop;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
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
	
	// 環繞通知
	@Around("ptDiv()")
	public Object aroundAdvice(ProceedingJoinPoint joinPoint) {
		Object result = null;
		String threadName = Thread.currentThread().getName();
		String methodName = joinPoint.getSignature().getName(); // 方法名稱
		Object[] args = joinPoint.getArgs(); // 方法參數
		String dateTime = sdf.format(new Date());
		
		// 前置通知
		System.out.printf("Log 前置通知[%s]: %s %s %s %n", threadName, dateTime, methodName, Arrays.toString(args));
		
		try {
			// 執行目標方法
			result = joinPoint.proceed();
			// 正常返回通知
			System.out.printf("AfterReturning 正常返回通知[%s]: %s %s 返回結果:%s %n", threadName, dateTime, methodName, result);
			return result;
		} catch (Throwable ex) {
			// 異常返回通知
			System.out.printf("AfterThrowing 異常返回通知[%s]: %s %s 異常結果:%s %n", threadName, dateTime, methodName, ex);
		} finally {
			// 後置通知(不論是否有異常都會執行)
			System.out.printf("After 後置通知[%s]: %s %s %n", threadName, dateTime, methodName);
		}
		return null;
	}
	
	/*
	// 前置通知(Advice)
	//@Before(value = "execution(public Integer com.example.demo.proxy.CalcImpl.add(Integer, Integer))")
	//@Before(value = "execution(public Integer com.example.demo.proxy.CalcImpl.*(Integer, Integer))")
	//@Before(value = "execution(public Integer com.example.demo.proxy.CalcImpl.*(..))")
	//@Before(value = "execution(* com.example.demo.proxy.CalcImpl.*(..))")
	//@Before(value = "execution(* com.example.demo.proxy.*.*(..))")
	//@Before(value = "execution(* *(..))")
	//@Before(value = "ptAdd()")
	//@Before(value = "ptAdd() || ptDiv()")
	//@Before(value = "ptAll() && !ptDiv()") // 切入點表達式支援邏輯運算子: &&, ||, !
	@Before(value = "ptDiv()")
	// 前置通知
	public void beforeAdvice(JoinPoint joinPoint) {
		String threadName = Thread.currentThread().getName();
		String methodName = joinPoint.getSignature().getName(); // 方法名稱
		Object[] args = joinPoint.getArgs(); // 方法參數
		String dateTime = sdf.format(new Date());
		// log 紀錄
		System.out.printf("Log 前置通知[%s]: %s %s %s %n", threadName, dateTime, methodName, Arrays.toString(args));
	}
	
	@After(value = "ptDiv()") 
	// 不論是否會發生例外都會執行
	public void endAdvice(JoinPoint joinPoint) {
		String threadName = Thread.currentThread().getName();
		String methodName = joinPoint.getSignature().getName(); // 方法名稱
		String dateTime = sdf.format(new Date());
		System.out.printf("After 後置通知[%s]: %s %s %n", threadName, dateTime, methodName);
	}
	
	@AfterReturning(value = "ptDiv()", returning="result") 
	// 正常返回通知
	public void afterReturningAdvice(JoinPoint joinPoint, Object result) {
		String threadName = Thread.currentThread().getName();
		String methodName = joinPoint.getSignature().getName(); // 方法名稱
		String dateTime = sdf.format(new Date());
		System.out.printf("AfterReturning 正常返回通知[%s]: %s %s 返回結果:%s %n", threadName, dateTime, methodName, result);
	}
	
	// 異常返回通知
	@AfterThrowing(value = "ptDiv()", throwing = "ex")
	public void afterThrowingAdvice(JoinPoint joinPoint, Throwable ex) {
		String threadName = Thread.currentThread().getName();
		String methodName = joinPoint.getSignature().getName(); // 方法名稱
		String dateTime = sdf.format(new Date());
		System.out.printf("AfterThrowing 異常返回通知[%s]: %s %s 異常結果:%s %n", threadName, dateTime, methodName, ex);
	}
	*/
	
}
