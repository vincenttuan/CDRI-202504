package com.example.demo.service;

import java.util.List;

import com.example.demo.exception.BookException;
import com.example.demo.model.Book;

public interface BookService {
	List<Book> findAllBooks();
	Book getBookById(Integer id) throws BookException;
	void addBook(Book book) throws BookException;
	
	void updateBook(Integer id, Book book) throws BookException;
	void updateBookName(Integer id, String name) throws BookException;
	void updateBookPrice(Integer id, Double price) throws BookException;
	void updateBookNameAndPrice(Integer id, String name, Double price) throws BookException;
	
	void deleteBook(Integer id) throws BookException;
}
