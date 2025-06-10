package com.example.demo.aop;

// 驗證商品 ID 不可以 null
// 商品數量介於 1~100 間
// 商品價格 > 0
public interface CartService {
	
	void addToCart(String productId, Integer quantity, Double price);
	
}
