package com.example.demo.study.sign;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import com.example.demo.study.security.KeyUtil;

/*
 * 情境說明：
 * 小王是一家公司的經理，為了提高工作效率和保證文件的安全性，他決定使用數位簽章技術對公司的合同進行簽署。
 * 數位簽章不僅可以確保文件的完整性（即文件在傳輸過程中沒有被修改），還可以確認文件是由特定的人簽署的，
 * 在這種情況下，即由小王簽署。
 *
 * 本程式「DigitalSignatureCreator」的目的是生成一個數位簽章，該簽章對「my_contract.txt」文件進行簽署。
 * 這個數位簽章可以被存儲（在signature.sig文件中）和分享，供其他人或系統進行驗證。
 *
 * 當其他接收者接收到這個簽署的文件和數位簽章時，他們可以使用小王的公鑰來驗證文件的完整性和簽署者的身份。
 */
public class DigitalSignatureCreator {
	public static void main(String[] args) throws Exception {
		// 小王
		// 合約檔位置
		String contractPath = "src/main/java/com/example/demo/study/sign/my_contract.txt";
		// 公鑰檔位置
		String publicKeyPath = "src/main/java/com/example/demo/study/sign/publicKey.key";
		// 私鑰檔位置
		String privateKeyPath = "src/main/java/com/example/demo/study/sign/privateKey.key";
		// 數位簽章檔位置
		String signaturePath = "src/main/java/com/example/demo/study/sign/signature.sig";
		
		// 公鑰與私鑰
		PublicKey publicKey;
		PrivateKey privateKey;
		
		// 取得公私鑰/建立私公鑰
		if(Files.exists(Paths.get(publicKeyPath)) && Files.exists(Paths.get(privateKeyPath))) {
			// 取得公私鑰
			publicKey = KeyUtil.getPublicKeyFromFile("RSA", publicKeyPath);
			privateKey = KeyUtil.getPrivateKeyFromFile("RSA", privateKeyPath);
		} else {
			// 建立公私鑰
			KeyPair keyPair = KeyUtil.generateRSAKeyPair();
			publicKey = keyPair.getPublic();
			privateKey = keyPair.getPrivate();
			// 保存公私鑰檔案
			KeyUtil.saveKeyToFile(publicKey, publicKeyPath);
			KeyUtil.saveKeyToFile(privateKey, privateKeyPath);
		}
		
		// 數位簽章: 將私鑰(私人印章) 蓋在合約(my_contract.txt)上
		byte[] digitalSignature = KeyUtil.signWithPrivateKey(privateKey, contractPath);
		
		// 保存數位簽章
		KeyUtil.saveSignatureToFile(digitalSignature,  signaturePath);
		
		System.out.println("小王的公鑰(驗證數位簽章用):" + Base64.getEncoder().encodeToString(publicKey.getEncoded()));
		System.out.println("小王的數位簽章:" + Base64.getEncoder().encodeToString(digitalSignature));
		System.out.println("小王的數位簽章位置:" + signaturePath);
		
	}
}
