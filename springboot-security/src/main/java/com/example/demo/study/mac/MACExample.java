package com.example.demo.study.mac;

import java.util.Arrays;

import javax.crypto.SecretKey;

import com.example.demo.study.security.KeyUtil;

// MAC 訊息驗證碼
public class MACExample {
	
	public static void main(String[] args) throws Throwable {
		// 1. 定義訊息
		String message = "上半年獎金每人加發3個月";
		
		// 2. 產生一把專用於 Hmac 的密鑰
		SecretKey macKey = KeyUtil.generateKeyForHmac(); // 預設使用 HmacSHA256
		
		// 3. 利用此密鑰(macKey) + 訊息(message) 生成 MAC 值
		byte[] macValue = KeyUtil.generateMac(macKey, message);
		System.out.printf("MAC(原始): %s%n", Arrays.toString(macValue));
		
		// 4. 將 macValue 轉 16 進為字串印出
		String macHexValue = KeyUtil.bytesToHex(macValue);
		System.out.printf("MAC(Hex): %s%n", macHexValue);
		
	}
	
}
