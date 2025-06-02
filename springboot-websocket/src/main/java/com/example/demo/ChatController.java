package com.example.demo;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
	@MessageMapping("/chat") // 有訊息到 /app/chat
	@SendTo("/topic/messages") // 訂閱一個主題:/topic, 主題叫做:/messages
	public ChatMessage send(ChatMessage message) {
		return message;
	}
	
	@MessageMapping("/chat2") // 有訊息到 /app/chat2
	@SendTo("/topic/messages2") // 訂閱一個主題:/topic, 主題叫做:/messages2
	public ChatMessage send2(ChatMessage message) {
		return message;
	}
}
