package com.example.demo;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.entity.Author;
import com.example.demo.model.entity.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;

@SpringBootTest
public class Test_AddBook {
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Test
	public void addBook() {
		// 尋找作者
		Optional<Author> optAuthor = authorRepository.findById(1);
		if(optAuthor.isEmpty()) {
			System.out.println("查無作者");
			return;
		}
		// 取得作者
		Author author = optAuthor.get();
		
		// 建立書籍並關聯作者
		Book book1 = new Book();
		book1.setName("Java入門");
		book1.setAuthor(author); // 關聯作者
		
		Book book2 = new Book();
		book2.setName("Spring實戰");
		book2.setAuthor(author); // 關聯作者
		
		// 儲存
		bookRepository.save(book1);
		bookRepository.save(book2);
	}
	
}
