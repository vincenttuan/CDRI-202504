package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.service.UserService;

@SpringBootTest
public class UserJPAReadTest {
	
	@Autowired
	private UserService userService;
	
	@Test
	public void testUserAdd() {
		System.out.println(userService.getUser("john"));
		System.out.println(userService.getUser("mary"));
		System.out.println(userService.getUser("jack")); // null
	}
	
}
