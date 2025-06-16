package com.example.demo.study.jwt;

import java.util.Date;

import com.example.demo.study.security.KeyUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;

// 建立一個有時效性的 JWT
public class SimpleLimitedJWT {

	public static void main(String[] args) throws Exception {
		// 有效期間(10秒)
		Date expirationTime = new Date(new Date().getTime() + 10_000); // 現在時刻 + 10 秒
		
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
				.expirationTime(expirationTime) // 設定有效時間
				.build();
		System.out.println("Payload: " + claimsSet);
		
		// 3. 進行簽名
		// 將 claimsSet + 簽名 = token
		String token = KeyUtil.signJWT(claimsSet, signingSecret);
		System.out.println("Token(JWT): " + token);
		
		Thread.sleep(11_000); // 模擬 11 秒後進行驗證
		
		// 4. 驗證 Token(JWT)
		if(KeyUtil.verifyJWTSignature(token, signingSecret)) {
			System.out.println("驗證成功");
			// 讀取 Token(JWT) 中 Claims(Payload) 的資料
			JWTClaimsSet claims = KeyUtil.getClaimsFromToken(token);
			System.out.println("讀取 subject: " + claims.getSubject());
			System.out.println("讀取 issure: " + claims.getIssuer());
			System.out.println("讀取 name: " + claims.getStringClaim("name"));
			System.out.println("讀取 phone: " + claims.getStringClaim("phone"));
			System.out.println("讀取 email: " + claims.getStringClaim("email"));
		} else {
			System.out.println("驗證失敗");
		}
	}

}
