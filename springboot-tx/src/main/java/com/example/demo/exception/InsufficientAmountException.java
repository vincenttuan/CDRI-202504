package com.example.demo.exception;

// 餘額/庫存不足
public class InsufficientAmountException extends Exception {
	public InsufficientAmountException(String message) {
		super(message);
	}
}
