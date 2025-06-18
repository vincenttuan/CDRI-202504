package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ErrorController {
	
	@GetMapping("/accessDenied")
	public String accessDenied() {
		return "accessDenied"; // 會對應到 templates/accessDenied.html
	} 
	
	
}
