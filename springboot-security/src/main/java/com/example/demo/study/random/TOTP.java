package com.example.demo.study.random;

import java.util.Base64;

import com.example.demo.study.security.KeyUtil;

/*
 * 
 * TOTP (Time-based One-Time Password) 是一種一次性密碼（OTP）算法，它的特點是根據當前的時間值生成。
 * 由於TOTP是基於時間生成的密碼，因此當客戶端和伺服器的時鐘保持同步時，它能夠有效運作。
 * TOTP生成的密碼具有固定的短期有效期，過了該時段後，該密碼將不再有效且不被接受。
 * OTP (One-Time Password) 是一種更廣泛的術語，描述了只能使用一次的密碼。
 * TOTP 是 OTP 的一種，但還有其他如 HOTP（基於計數的一次性密碼）。
 * 差別：
 * OTP: 一個泛用術語，描述了只能使用一次的密碼。
 * TOTP: 是 OTP 的一種，其特點是根據當前時間生成密碼。
*/
public class TOTP {
	
	public static void main(String[] args) throws Exception {
		
	}
	
	public static String generateTOTP(String username) throws Exception {
		// 金鑰(以登入者的名稱當作金鑰)
		String secret = Base64.getEncoder().encodeToString(username.getBytes());
		// 現在秒數 30 的倍數就會更新
		long timeInterval = System.currentTimeMillis() / 1000L / 30L;
		// 得到 TOTP 密碼
		String totp = KeyUtil.generateTOTP(secret, timeInterval, "HMACSHA256");
		return totp;
	}
	
}
