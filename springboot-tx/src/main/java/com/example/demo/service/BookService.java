package com.example.demo.service;

import com.example.demo.exception.InsufficientAmountException;

public interface BookService {
	// 書本價格
	Integer getBookPrice(Integer bookId);
	
	// 書本庫存
	Integer getBookAmount(Integer bookId);
	
	// 帳戶餘額
	Integer getWalletBalance(String username);
	
	// 更新庫存(減少庫存量)
	void reduceBookAmount(Integer bookId, Integer amountToReduce) throws InsufficientAmountException;
	
	// 更新餘額(減少餘額)
	void reduceWalletBalance(String username, Integer bookPrice) throws InsufficientAmountException;
}
