package com.example.demo.proxy;

public class PersonTest {

	public static void main(String[] args) {
		// 一般用法
		Person man = new Man();
		Person woman = new Woman();
		man.work();
		woman.work();
	}

}
