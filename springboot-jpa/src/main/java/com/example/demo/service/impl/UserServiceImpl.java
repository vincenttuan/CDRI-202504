package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.dto.UserDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDto getUser(String username) {
		return null;
	}

	@Override
	public void addUser(String username, String password, String email, boolean active, String role) {
		// TODO Auto-generated method stub
		
	}
	
}
