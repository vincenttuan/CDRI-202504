package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.aop.FloorService;

@SpringBootTest
public class AOPFloorTest {
	
	@Autowired
	private FloorService floorService;
	
	@Test
	public void test() {
		String username = "alice";
		int floor = 2;
		
		String result = floorService.enterFloor(username, floor);
		System.out.println(result);
	}
	
}
