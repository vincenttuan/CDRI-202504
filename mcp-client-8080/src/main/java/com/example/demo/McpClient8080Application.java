package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;

@SpringBootApplication
public class McpClient8080Application {

	public static void main(String[] args) {
		SpringApplication.run(McpClient8080Application.class, args);
	}
	
	@Bean
	public CommandLineRunner runner(ChatClient.Builder chatClientBuilder, SyncMcpToolCallbackProvider toolCallbackProvider) {
		return args -> {
			// 建立 ChatClient 並將 callback provider 註冊進去
			ChatClient chatClient = chatClientBuilder.defaultTools(toolCallbackProvider).build();
			
			String prompt1 = "今天似乎運氣不錯想買5張樂透彩券";
			String response1 = chatClient.prompt().user(prompt1).call().content();
			
			System.out.println("AI Agent 回應:" + response1);
		};
	}
}
