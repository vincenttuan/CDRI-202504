package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 免去撰寫 @ResponseBody, 但若要透過 jsp 渲染則不適用
@RequestMapping("/api") // 以下路徑統一都有 URL 前綴 "/api"
public class ApiController {
	
	/**
	 * 1. 首頁
	 * 路徑: /home
	 * 路徑: /
	 * 網址: http://localhost:8080/api/home
	 * 網址: http://localhost:8080/api/
	 * */
	@GetMapping(value = {"/home", "/"})
	public String home() {
		return "我是首頁";
	}
	
}
