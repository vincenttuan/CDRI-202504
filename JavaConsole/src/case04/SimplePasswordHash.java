package case04;

import java.security.MessageDigest;

public class SimplePasswordHash {
	
	// 產生雜湊
	public static String hashPassword(String password) throws Exception {
		// 使用 SHA-256 雜湊演算法
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] hashBytes = md.digest(password.getBytes());
		// 將 byte[] 轉成16進位字串
		StringBuilder sb = new StringBuilder();
		for(byte b : hashBytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}
	
	public static void main(String[] args) throws Exception {
		String password = "1234";
		String hash = hashPassword(password);
		System.out.printf("password: %s hash: %s length: %d%n", password, hash, hash.length());

	}

}
