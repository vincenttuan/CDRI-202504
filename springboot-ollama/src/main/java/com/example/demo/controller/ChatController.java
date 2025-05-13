package com.example.demo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {
	
	@Autowired
	private ChatClient chatClient;
	
	@GetMapping("/ask")
	public String ask(@RequestParam String q) {
		return chatClient.prompt().user(q).call().content();
	}
	
}
