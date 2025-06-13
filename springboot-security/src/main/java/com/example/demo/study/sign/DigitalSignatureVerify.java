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
 * 延續「DigitalSignatureCreator」的情境，小王的合作夥伴，小李，收到了小王用數位簽章簽署的「my_contract.txt」文件。
 * 小李想確認這份文件的完整性，以及確定這份合同確實是由小王簽署的。因此，他決定使用數位簽章驗證工具來進行檢查。
 *
 * 本程式「DigitalSignatureVerifier」的目的是驗證「my_contract.txt」文件的數位簽章。
 * 小李透過使用小王的公鑰（publicKey.key）和數位簽章（signature.sig），這個程式可以確認文件的完整性和簽署者的身份。
 * 如果驗證成功，這意味著文件在傳輸過程中沒有被修改，且確實是由小王簽署的。
 */
public class DigitalSignatureVerify {
	public static void main(String[] args) throws Exception {
		// 小李
		// 小王合約檔位置
		String contractPath = "src/main/java/com/example/demo/study/sign/my_contract.txt";
		// 小王公鑰檔位置
		String publicKeyPath = "src/main/java/com/example/demo/study/sign/publicKey.key";
		// 小王數位簽章檔位置
		String signaturePath = "src/main/java/com/example/demo/study/sign/signature.sig";
		
		PublicKey publicKey = KeyUtil.getPublicKeyFromFile("RSA", publicKeyPath);
		byte[] savedSignature = KeyUtil.getSignatureFromFile(signaturePath);
		
		// 驗證合約是否是小王所簽的
		boolean isValid = KeyUtil.verifySignatureFromFile(publicKey, contractPath, savedSignature);
		
		if(isValid) {
			System.out.println("數位簽章驗證成功");
			System.out.println(Files.readString(Paths.get(contractPath)));
		} else {
			System.out.println("數位簽章驗證失敗");
		}
	}
}

