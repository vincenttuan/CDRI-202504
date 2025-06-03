package com.example.demo.entity;

import lombok.Data;

@Data
public class ChatMessage {
	private String from; // 發送者名稱
	private String content; // 訊息內容
}
