package com.example.demo;

import static org.assertj.core.api.Assertions.assertThatList;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.entity.Author;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;

@SpringBootTest
public class Test_Read {
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Test
	public void read() {
		// 查詢作者與書籍
		List<Author> authors = authorRepository.findAll();
		authors.forEach(author -> {
			System.out.printf("序號:%d 姓名:%s%n", author.getId(), author.getName());
			
		});
		
		authors.forEach(author -> {
			System.out.printf("序號:%d 姓名:%s 著作數量:%d%n", author.getId(), author.getName(), author.getBooks().size());
			
		});
	}
	
}
