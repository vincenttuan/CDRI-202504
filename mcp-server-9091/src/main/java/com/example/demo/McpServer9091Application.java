package com.example.demo;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.service.StockOrderService;

@SpringBootApplication
public class McpServer9091Application {

	public static void main(String[] args) {
		SpringApplication.run(McpServer9091Application.class, args);
	}
	
	@Bean
	public ToolCallbackProvider toolCallbackProvider(StockOrderService stockOrderService) {
		return MethodToolCallbackProvider.builder().toolObjects(stockOrderService).build();
	}
}
