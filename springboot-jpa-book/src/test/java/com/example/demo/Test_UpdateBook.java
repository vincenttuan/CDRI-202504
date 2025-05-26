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
public class Test_UpdateBook {
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	// 變更書籍作者
	@Test
	public void updateBookName() {
		Optional<Author> optAuthor = authorRepository.findById(2);
		if(optAuthor.isEmpty()) {
			System.out.println("查無作者");
			return;
		}
		
		Optional<Book> optBook = bookRepository.findById(1);
		if(optBook.isEmpty()) {
			System.out.println("查無書籍");
			return;
		}
		
		Author author = optAuthor.get();
		Book book = optBook.get();
		
		// 更新設定
		book.setAuthor(author);
		
		// 儲存更新(save 就是將最新物件狀態寫回給資料表)
		bookRepository.save(book);
	}
	
}
