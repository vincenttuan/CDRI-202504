package com.example.demo.proxy;

public class PersonTest {

	public static void main(String[] args) {
		// 一般用法
		//Person man = new Man();
		//Person woman = new Woman();
		//man.work();
		//woman.work();
		
		// 使用代理
		Person man = new PersonProxy(new Man());
		Person woman = new PersonProxy(new Woman());
		man.work();
		woman.work();
	}

}
