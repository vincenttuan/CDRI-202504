package com.example.demo.proxy;

public class Man implements Person {

	@Override
	public void work() {
		// 公用邏輯
		System.out.println("出門戴口罩");
		
		// 業務邏輯
		System.out.println("Man 上班工作");
		
		// 公用邏輯
		System.out.println("回家脫口罩");
	}

}
