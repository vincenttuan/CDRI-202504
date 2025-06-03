package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

// 負責啟用 STOMP 協定並設定路由規則
@Configuration
@EnableWebSocketMessageBroker // 啟用 STOMP WebSocket 支援
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	// 註冊 STOMP 端點, 前端可以透過此端點建立 WebSocket 連線
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/chat-websocket").withSockJS();
	}
	
	// 設定訊息代理, 定義訊息如何發送與接收
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// 可以處理所有 /topic 開頭的訊息
		registry.enableSimpleBroker("/topic");
		// 前端傳送到 /app 開頭的訊息會被路由到 @MessageMapping
		// 例如: 前端針對 /app/chat/{roomId} 發送訊息, 該訊息會被廣播到 /topic/messages/{roomId}
		registry.setApplicationDestinationPrefixes("/app");
	}
	
	
	
}
