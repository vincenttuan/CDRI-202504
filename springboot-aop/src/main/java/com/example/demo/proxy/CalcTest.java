package com.example.demo.proxy;

public class CalcTest {

	public static void main(String[] args) {
		Calc calc = new CalcImpl();
		Calc calcProxy = new CalcProxy(calc);
		// 加法
		System.out.println(calcProxy.add(10, 20));
		System.out.println(calcProxy.add(null, 20));
		// 除法
		System.out.println(calcProxy.div(20, 10)); // 正常
		System.out.println(calcProxy.div(null, 10));
		System.out.println(calcProxy.div(20, 0));
	}

}
