package com.example.demo.study.jwt;

import com.example.demo.study.security.KeyUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;

public class SimpleJWT {

	public static void main(String[] args) throws Exception {
		// 1. 生成簽名密鑰
		// JWK: 產生簽名用的密鑰(32 bytes)
		String signingSecret = KeyUtil.generateSecret(32);
		System.out.println("密鑰:" + signingSecret);
		
		// 2. 創建 JWT 聲明(claim)
		// JWT: 建立資料主體(之後要進行簽名的部分)
		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
				.subject("報名 Java") // 主題
				.issuer("https://edu.cdri.org.tw") // 發行單位
				.claim("name", "John") // 額外的資料
				.claim("phone", "0912345678") // 額外的資料
				.claim("email", "John@gmail.com") // 額外的資料
				.build();
		System.out.println("Payload: " + claimsSet);
		
		// 3. 進行簽名
		// 將 claimsSet + 簽名 = token
		String token = KeyUtil.signJWT(claimsSet, signingSecret);
		System.out.println("Token(JWT): " + token);
	}

}
