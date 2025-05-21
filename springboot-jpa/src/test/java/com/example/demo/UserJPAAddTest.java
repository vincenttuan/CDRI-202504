package com.example.demo;

import java.util.Scanner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.entity.Room;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@SpringBootTest
public class UserJPAAddTest {
	
	@Autowired
	private UserService userService;
	
	@Test
	public void testUserAdd() {
		userService.addUser("john", "1234", "john@gmail.com", true, "admin");
		userService.addUser("mary", "1234", "mary@gmail.com", true, "user");
		System.out.println("User add ok!");
	}
	
}
