package com.example.demo.aop;

import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

	@Override
	public void addToCart(String productId, Integer quantity, Double price) {
		// 商業邏輯
		// ...
		System.out.printf("已將商品: %s 數量: %s 價格: %.1f 加入到購物車%n", productId, quantity, price);
	}

}
