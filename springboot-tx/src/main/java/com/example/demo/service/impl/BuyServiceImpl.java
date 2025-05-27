package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.InsufficientAmountException;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;
import com.example.demo.service.BuyService;

import jakarta.transaction.Transactional;

@Service // 交易服務
public class BuyServiceImpl implements BuyService {
	
	@Autowired
	private BookService bookService;
	
	// RuntimeException 預設會回滾, 可以透過 dontRollbackOn 來改變
	// Exception 預設不會回滾, 可以透過 rollbackOn 來定義
	@Transactional(
			rollbackOn = {InsufficientAmountException.class},
			dontRollbackOn = {RuntimeException.class}
	)
	@Override
	public void buyOneBook(String username, Integer bookId) throws InsufficientAmountException {
		System.out.printf("%s 要買書%n", username);
		// 1. 查詢書本價格
		Integer bookPrice = bookService.getBookPrice(bookId);
		System.out.printf("bookId: %d, bookPrcie: %d%n", bookId, bookPrice);
		// 2. 減去庫存(1本)
		bookService.reduceBookAmount(bookId, 1); // 固定減去 1 本
		// 3. 修改餘額
		bookService.reduceWalletBalance(username, bookPrice);
		// 4. 結帳完成
		System.out.printf("%s 結帳完成%n", username);
	}
	
}
