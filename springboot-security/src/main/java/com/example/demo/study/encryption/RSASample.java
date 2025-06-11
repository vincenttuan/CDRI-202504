package com.example.demo.study.encryption;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Base64;

import com.example.demo.study.security.KeyUtil;

public class RSASample {

	public static void main(String[] args) throws Exception {
		// 1. 生成 RSA 密鑰對(公/私鑰)
		KeyPair keyPair = KeyUtil.generateRSAKeyPair();
		PublicKey publicKey = keyPair.getPublic(); // 公鑰
		PrivateKey privateKey = keyPair.getPrivate(); // 私鑰
		
		String originalMessage = "小心颱風";
		System.out.printf("1.明文:%s%n", originalMessage);
		
		// 2. 利用公鑰進行加密
		byte[] encryptedBytes = KeyUtil.encryptWithPublicKey(publicKey, originalMessage.getBytes());
		System.out.printf("2.加密:%s%n", Arrays.toString(encryptedBytes));
		
		// 3. 轉 base64
		String encodingEncrypedBytes = Base64.getEncoder().encodeToString(encryptedBytes);
		System.out.printf("3.加密(base64):%s%n", encodingEncrypedBytes);
		
		// -----------------------------------------------------------------------------
		System.out.println("----------------------------------");
		System.out.printf("4.網路上傳遞:%s%n", encodingEncrypedBytes);
		System.out.println("----------------------------------");
		
		// 5. 利用私鑰解密
		// 將 encodingEncrypedBytes 進行 base64 解碼
		byte[] decodingEncryptedBytes = Base64.getDecoder().decode(encodingEncrypedBytes);
		// 將 decodingEncryptedBytes 透過 privateKey 私鑰解密
		byte[] decryptedBytes = KeyUtil.decryptWithPrivateKey(privateKey, decodingEncryptedBytes);
		String message = new String(decryptedBytes);
		System.out.printf("5.解密後:%s%n", message);
		
	}

}
