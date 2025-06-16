package com.example.demo.ragdemo;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
public class DocumentLoader implements CommandLineRunner {

    private final VectorStore vectorStore;

    public DocumentLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void run(String... args) {
    	List<Document> docs = List.of(
    		    new Document("台灣是位於東亞的島嶼國家，首都為台北。",
    		        Map.of("source", "doc1")),
    		    new Document("春天是四季之一，氣候溫暖，萬物復甦。",
    		        Map.of("source", "doc2")),
    		    new Document("Java 是一種廣泛使用的程式語言，適用於各種應用開發。",
    		        Map.of("source", "doc3")),
    		    new Document("段維瀚老師是台灣知名的資訊工程教育者，專長於人工智慧與軟體工程，經常在大學教授相關課程。",
    		        Map.of("source", "doc4"))
    		);
    	vectorStore.add(docs);

        vectorStore.add(docs);
        System.out.println("文檔已成功載入向量庫！");
    }
}

