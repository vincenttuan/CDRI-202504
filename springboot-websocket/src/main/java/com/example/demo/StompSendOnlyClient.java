package com.example.demo;

import java.util.concurrent.CompletableFuture;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class StompSendOnlyClient {

	public static void main(String[] args) throws Exception {
        String url = "ws://localhost:8080/chat-websocket/websocket";
        WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        ListenableFuture<StompSession> future = stompClient.connect(url, new StompSessionHandlerAdapter() {});
        StompSession session = future.get(); // 等待連線完成

        for (int i = 1; i <= 10; i++) {
            ChatMessage message = new ChatMessage();
            message.setFrom("AI");
            message.setContent("現在有大特價..." + i);
            session.send("/app/chat", message);
            System.out.println("訊息已發送: " + i);
            Thread.sleep(1000);
        }
        session.disconnect();
    }

}
