package com.example.demo;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.service.ShoppingService;

@SpringBootApplication
public class McpServer9090Application {

	public static void main(String[] args) {
		SpringApplication.run(McpServer9090Application.class, args);
	}
	
	@Bean
	public ToolCallbackProvider toolCallbackProvider(ShoppingService shoppingService) {
		return MethodToolCallbackProvider.builder().toolObjects(shoppingService).build();
	}
	
}
