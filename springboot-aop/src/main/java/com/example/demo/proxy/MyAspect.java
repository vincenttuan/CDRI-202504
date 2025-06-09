package com.example.demo.proxy;

import java.lang.reflect.Method;
import java.util.Arrays;

// 用來集中所有的公用邏輯
// Aspect 切面程式 (AOP)
public class MyAspect {
	
	// 前置通知(Advice)
	public static void before(Method method, Object[] args) {
		System.out.printf("前置通知-方法名稱:%s 方法參數:%s%n", method.getName(), Arrays.toString(args));
	}
	
	// 例外通知(Advice)
	public static void throwing(Method method, Object[] args, Throwable e) {
		System.out.printf("例外通知-方法名稱:%s 方法參數:%s 例外訊息:%s%n", method.getName(), Arrays.toString(args), e);
	}
	
	// 後置通知(Advice)
	public static void end(Method method, Object[] args) {
		System.out.printf("後置通知-方法名稱:%s 方法參數:%s%n", method.getName(), Arrays.toString(args));
	}
}
