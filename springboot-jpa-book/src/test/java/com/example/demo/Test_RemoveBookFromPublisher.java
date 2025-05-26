package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.example.demo.model.entity.Book;
import com.example.demo.model.entity.Publisher;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.PublisherRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class Test_RemoveBookFromPublisher {
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private PublisherRepository publisherRepository;
	
	@Test
	@Transactional
	@Commit // 在測試環境中使用 @Transactional 要配合使用 @Commit 讓事務提交
	public void remove() {
		
		Book book1 = bookRepository.findById(1).get();
		Publisher publisher = publisherRepository.findById(5).get();
		publisherRepository.deleteBookFromPublisher(publisher.getId(), book1.getId());
		
	}
	
}
