package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.BookException;
import com.example.demo.model.Book;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<Book>>> findAllBooks() {
		List<Book> books = bookService.findAllBooks();
		if(books.size() == 0) {
			return ResponseEntity.badRequest().body(ApiResponse.error("查無此書"));
		}
		return ResponseEntity.ok(ApiResponse.success("查詢成功:", books));
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<Book>> getBookById(Integer id) {
		try {
			Book book = bookService.getBookById(id);
			return ResponseEntity.ok(ApiResponse.success("查詢成功:", book));
		} catch (BookException e) {
			return ResponseEntity.badRequest().body(ApiResponse.error("查無此書"));
		}
	}
	
}
