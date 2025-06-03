package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // 啟動排程機制
public class SpringbootWebsocketChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootWebsocketChatApplication.class, args);
	}

}
