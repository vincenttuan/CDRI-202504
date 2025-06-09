package com.example.demo.proxy;

public class CalcTest {

	public static void main(String[] args) {
		Calc calc = new CalcImpl();
		System.out.println(calc.add(10, 20));
		System.out.println(calc.add(10, 0));
		System.out.println(calc.add(null, 0));
	}

}
