package com.example.demo.service;

import com.example.demo.exception.InsufficientAmountException;

// 買書服務
public interface BuyService {
	
	void buyOneBook(String username, Integer bookId) throws InsufficientAmountException;
	
}
