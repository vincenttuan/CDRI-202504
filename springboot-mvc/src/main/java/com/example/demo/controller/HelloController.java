package com.example.demo.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class HelloController {
	
	@GetMapping("/hello")
	@ResponseBody
	public String hello() {
		return "Hello " + new Date();
	}
	
	@GetMapping("/hi")
	@ResponseBody
	public String hi() {
		return "Hi " + new Date();
	}
	
}
