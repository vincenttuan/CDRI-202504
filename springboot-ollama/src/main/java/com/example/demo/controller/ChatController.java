package com.example.demo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import reactor.core.publisher.Flux;

import org.springframework.http.MediaType;

@RestController
@RequestMapping("/chat")
public class ChatController {
	
	@Autowired
	private ChatClient chatClient;
	
	// 測試:http://localhost:8081/chat/ask?q=Hello
	@GetMapping("/ask")
	public String ask(@RequestParam String q) {
		return chatClient.prompt().user(q).call().content();
	}
	
	// 逐字回報版
	// SSE: Server-Send-Events
	// Emitter: 發射器
	@GetMapping(value = "/ask2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter ask2(@RequestParam String q) {
		// 建立發射器
		SseEmitter emitter = new SseEmitter();
		
		// 使用 ChatClient 的 stream 方法來獲取串流回應
		Flux<String> responseFlex = chatClient.prompt().user(q).stream().content();
		
		// 透過 Flex 的訂閱機制將資料逐字傳送給前端
		responseFlex.subscribe(
				word -> {
					try {
						emitter.send(word); // 逐字發送
					} catch (Exception e) {
						emitter.completeWithError(e); // 回報錯誤
					}
				},
				emitter::completeWithError,
				emitter::complete);
		
		return emitter;
	}
	
}
