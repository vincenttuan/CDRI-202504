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
	
	/**
	幫我先買進 2454 聯發科 6 股，再查詢目前持股。
	AI Agent 回應:已賣出 聯發科 6 股，成交金額：7200 元。
	持股明細：
	▸ 鴻海 (2317) 數量: 50 股 | 現價: 120元
	▸ 聯發科 (2454) 數量: 16 股 | 現價: 1200元
	▸ 富邦金 (2881) 數量: 30 股 | 現價: 70元
	▸ 台積電 (2330) 數量: 20 股 | 現價: 850元
	
	幫我先賣出 2454 聯發科 9 股，再查詢目前持股。
	AI Agent 回應:已賣出 聯發科 9 股，成交金額：10800 元。
	持股明細：
	▸ 鴻海 (2317) 數量: 50 股 | 現價: 120元
	▸ 聯發科 (2454) 數量: 7 股 | 現價: 1200元
	▸ 富邦金 (2881) 數量: 30 股 | 現價: 70元
	▸ 台積電 (2330) 數量: 20 股 | 現價: 850元
	 * s
	 * */
	
	@Bean
	public CommandLineRunner runner(ChatClient.Builder chatClientBuilder, SyncMcpToolCallbackProvider toolCallbackProvider) {
		return args -> {
			
			// 建立 ChatClient 並將 callback provider 註冊進去
			ChatClient chatClient = chatClientBuilder.defaultTools(toolCallbackProvider).build();
			
			//String prompt1 = "請先加2顆'橘子'到購物車，再加1瓶'牛奶'，然後顯示購物車內容，最後幫我'結帳'。";
			String prompt1 = "幫我先買入 2454 聯發科 6 股，再查詢目前持股。";
			//String prompt1 = "幫我先賣出 2454 聯發科 9 股，再查詢目前持股。";
			
			System.out.println(prompt1);
			
			String response1 = chatClient.prompt().user(prompt1).call().content();
			
			System.out.println("AI Agent 回應:" + response1);
		};
	}
	
}
