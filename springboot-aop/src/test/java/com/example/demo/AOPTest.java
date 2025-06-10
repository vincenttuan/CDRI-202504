package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.proxy.Calc;

@SpringBootTest
public class AOPTest {
	
	@Autowired
	private Calc calc;
	
	@Test
	public void test() {
		String threadName = Thread.currentThread().getName();
		
		Integer result1 = calc.add(20,  10);
		System.out.printf("[%s] %s%n", threadName, result1);
		
		Integer result2 = calc.div(20,  10);
		System.out.printf("[%s] %s%n", threadName, result2);
		
		Integer result3 = calc.div(20,  0);
		System.out.printf("[%s] %s%n", threadName, result3);
	}
	
}
