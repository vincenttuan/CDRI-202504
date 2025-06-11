package com.example.demo.study.encryption;

import java.util.Arrays;

import javax.crypto.spec.SecretKeySpec;

import com.example.demo.study.security.KeyUtil;

// AES 對稱式加密
public class AESSample {
	
	// 建立一個 AES Key(256bits, 32bytes)
	private static final String KEY = "012345678901234567890123456789AB"; // 32 個字
	
	public static void main(String[] args) throws Throwable {
		String originalText = "今天是好天氣"; // 明文
		System.out.printf("明文:%s%n", originalText);
		System.out.println("---------------------------");
		
		// 利用 AES 進行加密
		// 加密流程: 明文 -> 加密(encryptedECB)
		// 解密流程: 解密(decryptedECB) -> 明文
		
		// 1. 建立 AES 密鑰規範
		SecretKeySpec aesKeySpec = new SecretKeySpec(KEY.getBytes(), "AES"); // AES 金鑰
		// 2. 選擇使用 ECB 模式對明文進行加密
		byte[] encryptedECB = KeyUtil.encryptWithAESKey(aesKeySpec, originalText);
		// 3. 印出加密後的資訊
		System.out.printf("加密後:%s%n", Arrays.toString(encryptedECB));
		
		
		
		
	}
}
