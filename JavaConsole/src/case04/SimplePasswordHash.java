package case04;

import java.security.MessageDigest;
import java.security.SecureRandom;

public class SimplePasswordHash {
	// 產生隨機鹽
	public static String generateSalt() throws Exception {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16]; // 16 bytes = 128 bits
		random.nextBytes(salt);
		return bytesToHex(salt);
	}
	
	// 產生雜湊
	public static String hashPassword(String password, String salt) throws Exception {
		// 使用 SHA-256 雜湊演算法
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] hashBytes = md.digest((password+salt).getBytes()); // **密碼與鹽混合加密
		return bytesToHex(hashBytes);
	}
	
	// 把 byte[] 轉十六進位字串
	private static String bytesToHex(byte[] bytes) {
		// 將 byte[] 轉成16進位字串
		StringBuilder sb = new StringBuilder();
		for(byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}
	
	public static void main(String[] args) throws Exception {
		String password = "1234";
		String salt = generateSalt();
		String hash = hashPassword(password, salt);
		System.out.printf("salt: %s length: %d%n", salt, salt.length());
		System.out.printf("password: %s hash: %s length: %d%n", password, hash, hash.length());

	}

}
