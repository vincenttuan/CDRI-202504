package com.example.demo.proxy;

public class CalcTest {

	public static void main(String[] args) {
		Calc calc = new CalcImpl();
		Calc calcProxy = new CalcProxy(calc);
		System.out.println(calcProxy.add(10, 20));
		System.out.println(calcProxy.add(null, 20));
		
	}

}
