package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public UserDto getUser(String username) {
		User user = userRepository.getUser(username);
		if(user == null) {
			return null;
		}
		return userMapper.toDto(user);
	}

	@Override
	public void addUser(String username, String password, String email, boolean active, String role) {
		// TODO Auto-generated method stub
		
	}
	
}
