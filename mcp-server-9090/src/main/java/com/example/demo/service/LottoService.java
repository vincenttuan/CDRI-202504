package com.example.demo.service;

import java.util.Random;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class LottoService {
	
	/** 得到樂透電腦選號
	 * @param amount 購買數量
	 */
	@Tool(name = "lotto", description = "得到樂透電腦選號")
	public String lotto(@ToolParam(description = "樂透電腦選號購買的數量") Integer amount) {
		Random random = new Random();
		System.out.println("呼叫 lotto()");
		return "LottoService 產生樂透號碼:" + random.nextInt(10); // 0~9
	}
	
	/** 樂透結帳
	 * @param amount 購買數量
	 */
	@Tool(name = "checkout", description = "樂透結帳/付款")
	public String checkout(@ToolParam(description = "要結帳的數量") Integer amount) {
		System.out.println("呼叫 checkout()");
		return "LottoService 樂透結帳:$" + (amount * 50);
	}
}
