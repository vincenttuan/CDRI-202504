package com.example.demo.proxy;

public class DynProxyTest {

	public static void main(String[] args) {
		// 動態代理測試
		DynProxy dynProxy = new DynProxy(new CalcImpl()); // 動態代理
		// 取得代理物件
		Calc calc = (Calc)dynProxy.getProxy(); // 取得代理物件
		System.out.println(calc.add(10, 20));
		System.out.println(calc.add(null, 20));
		System.out.println(calc.div(10, 0));
		

	}

}
