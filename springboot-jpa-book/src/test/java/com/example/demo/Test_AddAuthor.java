package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.entity.Author;
import com.example.demo.repository.AuthorRepository;

@SpringBootTest
public class Test_AddAuthor {
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Test
	public void addAuthor() {
		Author author = new Author();
		author.setName("王小明");
		
		authorRepository.save(author);
		
	}
	
}
