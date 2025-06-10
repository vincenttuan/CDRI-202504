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
		Integer result1 = calc.add(20,  10);
		System.out.println(result1);
		
		Integer result2 = calc.div(20,  10);
		System.out.println(result2);
	}
	
}
