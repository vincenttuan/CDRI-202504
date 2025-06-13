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
		
		// --------------------------------------------------------------------
		// 5. 在實際應用中, 接收方會收到 message 與 macHexValue
		//    此時 message 要與 macKey(雙方都要有相同的密鑰) 產生出 computedMacHexValue 值
		//    並與 macHexValue 進行比對
		String receivedMessage = message; // 收到訊息(但尚未確定來源)
		byte[] computedMacValue = KeyUtil.generateMac(macKey, receivedMessage);
		String computedMacHexValue = KeyUtil.bytesToHex(computedMacValue);
		
		// 6. 比較 macHexValue 與 computedMacHexValue 是否相同 ?
		if(macHexValue.equals(computedMacHexValue)) {
			System.out.println("MAC 驗證成功, 資料來源正確: " + receivedMessage);
		} else {
			System.out.println("MAC 驗證失敗, 資料來源錯誤");
		}
		
	}
	
}
