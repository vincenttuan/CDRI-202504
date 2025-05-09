package com.example.demo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.response.ApiResponse;

// 利用 @ControllerAdvice 的特性來處理全局錯誤
@ControllerAdvice
public class GlobalExceptionHandler {
	
	// 當系統發生例外錯誤
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Object>> handleException(Exception e) {
		// MethodArgumentTypeMismatchException
		// NoResourceFoundException
		String errorMessage = e.toString();
		switch (e.getClass().getSimpleName()) {
			case "MethodArgumentTypeMismatchException": 
				errorMessage = "參數錯誤(" + e.getClass().getSimpleName() + ")";
				break;
			case "NoResourceFoundException":
				errorMessage = "查無網頁(" + e.getClass().getSimpleName() + ")";
				break;
		}	
		ApiResponse<Object> apiResponse = ApiResponse.error(errorMessage);
		return ResponseEntity.badRequest().body(apiResponse);
	}
}
