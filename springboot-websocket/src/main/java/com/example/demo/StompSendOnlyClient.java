package com.example.demo;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class StompSendOnlyClient {

	public static void main(String[] args) throws Exception {
		String url = "ws://localhost:8080/chat-websocket/websocket";
		WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		
		// 練建立連線並發送訊息
		stompClient.connect(url, new StompSessionHandlerAdapter() {

			@Override
			public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
				
				for(int i=1;i<=10;i++) {
				ChatMessage message = new ChatMessage();
					message.setFrom("AI");
					message.setContent("現在有大特價..." + i);
					session.send("/app/chat", message);
					System.out.println("訊息已發送");
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				session.disconnect();
			}
			
		});
		
	}

}
