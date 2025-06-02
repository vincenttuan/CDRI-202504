package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

// WebSocket 的配置資訊
@Configuration
@EnableWebSocketMessageBroker // 啟用 STOMP
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	// 定義訊息路由規則
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/topic"); // 發送到以 /topic 開頭的目的地訊息, 訊息代理的前綴字(例如:/topic/messages)
		registry.setApplicationDestinationPrefixes("/app"); // 應用程式前綴, 前端發送 "/app/chat" 會被對應到 @MessageChat("/chat)
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/chat-websocket").withSockJS(); // WebSocket 端點
	}

	
	
	
}
