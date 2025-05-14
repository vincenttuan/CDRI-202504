package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.BookException;
import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public List<Book> findAllBooks() {
		return bookRepository.findAllBooks();
	}

	@Override
	public Book getBookById(Integer id) throws BookException {
		Optional<Book> optBook = bookRepository.getBookById(id);
		if(optBook.isEmpty()) {
			throw new BookException("id: " + id + ", 查無此書");
		}
		return optBook.get();
	}

	@Override
	public void addBook(Book book) throws BookException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBook(Integer id, Book book) throws BookException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBookName(Integer id, String name) throws BookException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBookPrice(Integer id, Integer price) throws BookException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBookNameAndPrice(Integer id, String name, Integer price) throws BookException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteBook(Integer id) throws BookException {
		// TODO Auto-generated method stub
		
	}
	
}
