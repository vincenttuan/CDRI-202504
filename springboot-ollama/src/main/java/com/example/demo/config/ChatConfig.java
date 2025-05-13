package com.example.demo.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {
	
	@Bean // 此物件是被 Spring 管理, 其他程式可以透過 @Autowired 自動綁定來取得該物件(不需要 new)
	public ChatClient chatClient(ChatClient.Builder builder) {
		return builder.build();
	}
	
}
