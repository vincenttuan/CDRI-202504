package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.BookRepository;
import com.example.demo.service.BuyService;

@Service // 交易服務
public class BuyServiceImpl implements BuyService {
	
	@Override
	public void buyOneBook(String username, Integer bookId) {
		System.out.printf("%s 要買書%n", username);
		// 1. 查詢書本價格
		
		// 2. 減去庫存(1本)
		
		// 3. 修改餘額
		
		// 4. 結帳完成
		System.out.printf("%s 結帳完成%n", username);
	}
	
}
