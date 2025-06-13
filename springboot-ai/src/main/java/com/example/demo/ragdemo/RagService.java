package com.example.demo.ragdemo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RagService {

    private final VectorStore vectorStore;
    private final ChatClient chatClient;

    public RagService(VectorStore vectorStore, ChatClient chatClient) {
        this.vectorStore = vectorStore;
        this.chatClient = chatClient;
    }

    public String askQuestion(String question) {
        // 檢索最相關的 2 份文檔
        SearchRequest request = SearchRequest.builder()
                .query(question)
                .topK(2)
                .build();
        List<Document> relevantDocs = vectorStore.similaritySearch(request);

        // 組合提示詞（Spring AI 官方 Document 沒有 getFormattedContent，請用 getContentAsString）
        String context = relevantDocs.stream()
                .map(Document::getFormattedContent)
                .collect(Collectors.joining("\n"));

        PromptTemplate promptTemplate = new PromptTemplate("""
            請根據以下上下文回答問題：
            {context}
            
            問題：{question}
            答案：
            """);

        Prompt prompt = promptTemplate.create(
                Map.of("context", context, "question", question)
        );

        // 使用 Fluent API
        return chatClient
                .prompt(prompt)
                .call()
                .content();
    }
}

