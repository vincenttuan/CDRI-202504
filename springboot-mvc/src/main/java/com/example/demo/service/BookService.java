package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Book;

public interface BookService {
	List<Book> findAllBooks();
	Optional<Book> getBookById(Integer id);
	void addBook(Book book);
	
	void updateBook(Integer id, Book book);
	void updateBookName(Integer id, String name);
	void updateBookPrice(Integer id, Integer price);
	void updateBookNameAndPrice(Integer id, String name, Integer price);
	
	void deleteBook(Integer id);
}
