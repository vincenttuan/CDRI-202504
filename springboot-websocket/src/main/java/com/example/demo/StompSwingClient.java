package com.example.demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class StompSwingClient extends JFrame {
    private JTextField fromField;
    private JTextArea messageArea;
    private JTextField inputField;
    private JButton sendButton;
    private StompSession session; // 存放 session

    public StompSwingClient() {
        setTitle("STOMP WebSocket Chat Client");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        fromField = new JTextField("後台", 10);
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        inputField = new JTextField(20);
        sendButton = new JButton("Send");

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("From:"));
        topPanel.add(fromField);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(inputField);
        bottomPanel.add(sendButton);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(messageArea), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        connectWebSocket();
    }

    private void connectWebSocket() {
        try {
            String url = "ws://localhost:8080/chat-websocket/websocket";
            WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
            stompClient.setMessageConverter(new MappingJackson2MessageConverter());

            ListenableFuture<StompSession> future = stompClient.connect(url, new StompSessionHandlerAdapter() {
                @Override
                public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                    StompSwingClient.this.session = session;
                    appendMessage("Connected to server.");
                }

                @Override
                public void handleTransportError(StompSession session, Throwable exception) {
                    appendMessage("Connection error: " + exception.getMessage());
                }
            });

            future.get();
        } catch (Exception ex) {
            appendMessage("Failed to connect: " + ex.getMessage());
        }
    }

    private void sendMessage() {
        if (session == null || !session.isConnected()) {
            appendMessage("Not connected.");
            return;
        }
        String from = fromField.getText().trim();
        String content = inputField.getText().trim();
        if (content.isEmpty()) {
            return;
        }
        ChatMessage message = new ChatMessage();
        message.setFrom(from);
        message.setContent(content);
        session.send("/app/chat", message);
        appendMessage("You: " + content);
        inputField.setText("");
    }

    private void appendMessage(String msg) {
        messageArea.append(msg + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StompSwingClient client = new StompSwingClient();
            client.setVisible(true);
        });
    }

}
