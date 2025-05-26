package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.entity.Book;
import com.example.demo.model.entity.Publisher;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.PublisherRepository;

@SpringBootTest
public class Test_AddPublisher {
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private PublisherRepository publisherRepository;
	
	@Test
	public void add() {
		Book book1 = bookRepository.findById(1).get();
		Book book2 = bookRepository.findById(2).get();
		
		Publisher publisher1 = new Publisher();
		publisher1.setName("碁峰資訊");
		publisher1.addBook(book1);
		publisher1.addBook(book2);
		
		Publisher publisher2 = new Publisher();
		publisher2.setName("第三波資訊");
		publisher2.addBook(book1);
		
		Publisher publisher3 = new Publisher();
		publisher3.setName("天下文化");
		
		publisherRepository.save(publisher1);
		publisherRepository.save(publisher2);
		publisherRepository.save(publisher3);
		
	}
	
	
}
