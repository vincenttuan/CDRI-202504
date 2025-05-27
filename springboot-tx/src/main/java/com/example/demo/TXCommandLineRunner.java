package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.exception.InsufficientAmountException;
import com.example.demo.service.BuyService;

@Component
public class TXCommandLineRunner implements CommandLineRunner {
	
	@Autowired
	private BuyService buyService;
	
	// 買書
	private void buyBook(String username, Integer bookId) {
		try {
			buyService.buyOneBook(username, bookId);
		} catch (InsufficientAmountException e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Override
	public void run(String... args) throws Exception {
		buyBook("john", 1);
	}

}
