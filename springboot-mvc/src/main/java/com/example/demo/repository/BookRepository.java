package com.example.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Book;

@Repository
public class BookRepository {
	// InMemory 版
	private List<Book> books = new CopyOnWriteArrayList<>();
	
	// 初始資料有 4 本書
	{
		books.add(new Book(1, "機器貓小叮噹", 12.5, 20, false));
		books.add(new Book(2, "老夫子", 10.5, 30, false));
		books.add(new Book(3, "好小子", 8.5, 40, true));
		books.add(new Book(4, "尼羅河的女兒", 14.5, 50, true));
	}
	
	private List<Book> findAllBooks() {
		return books;
	}
	
	private Optional<Book> getBookById(Integer id) {
		return books.stream().filter(book -> book.getId().equals(id)).findFirst();
	}
	
	private boolean addBook(Book book) {
		return books.add(book);
	}
	
	public boolean updateBook(Integer id, Book book) {
		Book uptBook = books.set(id, book);
		return uptBook != null;
	}
	
	public boolean deleteBook(Integer id) {
		Optional<Book> optBook = getBookById(id);
		if(optBook.isPresent()) {
			return books.remove(optBook.get());
		}
		return false;
	}
	
	
}
