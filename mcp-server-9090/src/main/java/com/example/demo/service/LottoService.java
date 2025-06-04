package com.example.demo.service;

import java.util.Random;

public class LottoService {
	
	/** 取得樂透號碼
	 * @param amount 購買數量
	 */
	public String lotto(Integer amount) {
		Random random = new Random();
		return "LottoService 產生樂透號碼:" + random.nextInt(10); // 0~9
	}
	
	/** 樂透結帳
	 * @param amount 購買數量
	 */
	public String checkout(Integer amount) {
		return "LottoService 樂透結帳:$" + (amount * 50);
	}
}
