package com.example.demo.study.security;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class KeyUtil {

    private static final int CHUNK_SIZE = 200; // 定義每段訊息的加密大小

    
    /**
     * 生成一個2048位的RSA鑰匙對。
     * 
     * @return 返回生成的RSA的公鑰和私鑰對。
     * @throws NoSuchAlgorithmException 若當前環境不支援RSA加密算法時拋出。
     */
    public static KeyPair generateRSAKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    /**
     * 生成一個256位的AES密鑰。
     * 
     * @return 返回生成的AES的密鑰。
     * @throws NoSuchAlgorithmException 若當前環境不支援AES加密算法時拋出。
     */
    public static SecretKey generateAESKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        return keyGen.generateKey();
    }

    /**
     * 使用私鑰對訊息生成一個數字簽名。
     * 
     * @param privateKey 用於簽名的私鑰。
     * @param message    待簽名的訊息。
     * @return 返回該訊息的數字簽名。
     * @throws Exception 若簽名過程中發生錯誤時拋出。
     */
    public static byte[] signMessage(PrivateKey privateKey, String message) throws Exception {
        // SHA512withRSA : 提供了更高的安全性，在64位的系統和現代的CPU，使用SHA-512可能實際上比使用SHA-256更快
    	// SHA256withRSA : 是安全的組合，它使用 SHA-256 作為散列演算法，並使用 RSA 作為簽名演算法。
    	// SHA384withRSA : 介於SHA256與SHA512之間
    	Signature sign = Signature.getInstance("SHA512withRSA");
        sign.initSign(privateKey);
        sign.update(message.getBytes());
        return sign.sign();
    }
    
    /**
     * 使用公鑰驗證訊息的數字簽名。
     * 
     * @param publicKey        用於驗證數字簽名的公鑰。
     * @param message          需要驗證其數字簽名的原始訊息。
     * @param digitalSignature 該訊息的數位簽名。
     * @param cypto            使用的數字簽名算法。
     * @return                 返回true表示數字簽名是有效的，false表示數字簽名無效。
     * @throws Exception       若在驗證過程中遇到任何問題，會拋出異常。
     */
    public static boolean verifySignature(PublicKey publicKey, String message, byte[] digitalSignature, String cypto) throws Exception {
        Signature signature = Signature.getInstance(cypto);

        // 初始化Signature對象用於驗證
        signature.initVerify(publicKey);
        
        // 更新需要被驗證的訊息到Signature物件
        signature.update(message.getBytes());
        
        // 驗證訊息的數字簽名是否正確
        return signature.verify(digitalSignature);
    }

    /**
     * 使用公鑰驗證訊息的數字簽名。
     * 
     * @param publicKey 公鑰，用於驗證簽名。
     * @param message   原始的未加密訊息。
     * @param digitalSignature 要驗證的數位簽名。
     * @return 返回true表示數字簽名是有效的，false表示數字簽名無效。
     * @throws Exception 若簽名驗證過程中發生錯誤時拋出。
     */
    public static boolean verifySignatureFromMessage(PublicKey publicKey, String message, byte[] digitalSignature) throws Exception {
        String cryto = "SHA256withRSA";
    	return verifySignature(publicKey, message, digitalSignature, cryto);
    }
    
    /**
     * 使用公鑰驗證訊息的數字簽名。
     * 
     * @param publicKey 公鑰，用於驗證簽名。
     * @param filePath   需要驗證其數字簽名的檔案路徑。。
     * @param digitalSignature 從檔案生成的數位簽名。
     * @return 返回true表示數字簽名是有效的，false表示數字簽名無效。
     * @throws Exception 若簽名驗證過程中發生錯誤時拋出。
     */
    public static boolean verifySignatureFromFile(PublicKey publicKey, String filePath, byte[] digitalSignature) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);

        byte[] data = Files.readAllBytes(Paths.get(filePath));
        // update(data)：將檔案的內容（data）傳遞給 Signature 物件。
        // 這樣 Signature 物件將會計算出該資料的摘要，然後用來與提供的數位簽章做比對。
        signature.update(data);
        
        return signature.verify(digitalSignature);
    }

    /**
     * 使用AES密鑰加密訊息。
     * 
     * @param aesKey  AES密鑰，用於加密。
     * @param message 待加密的訊息。
     * @return 返回加密後的字節數據。
     * @throws Exception 若加密過程中發生錯誤時拋出。
     */
    public static byte[] encryptWithAESKey(SecretKey aesKey, String message) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        return cipher.doFinal(message.getBytes());
    }

    /**
     * 使用AES密鑰解密訊息。
     * 
     * @param aesKey        AES密鑰，用於解密。
     * @param encryptedData 已加密的訊息。
     * @return 返回解密後的字符串。
     * @throws Exception 若解密過程中發生錯誤時拋出。
     */
    public static String decryptWithAESKey(SecretKey aesKey, byte[] encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, aesKey);
        return new String(cipher.doFinal(encryptedData));
    }

    /**
     * 使用公鑰進行RSA分段加密。
     * 
     * @param publicKey 公鑰，用於加密。
     * @param data      待加密的訊息。
     * @return 返回加密後的字節數據。
     * @throws Exception 若加密過程中發生錯誤時拋出。
     */
    public static byte[] encryptWithPublicKey(PublicKey publicKey, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedData = new byte[0];
        for (int i = 0; i < data.length; i += CHUNK_SIZE) {
            byte[] chunk = new byte[Math.min(CHUNK_SIZE, data.length - i)];
            System.arraycopy(data, i, chunk, 0, chunk.length);
            byte[] encryptedChunk = cipher.doFinal(chunk);
            encryptedData = concat(encryptedData, encryptedChunk);
        }
        return encryptedData;
    }

    /**
     * 使用私鑰進行RSA分段解密。
     * 
     * @param privateKey    私鑰，用於解密。
     * @param encryptedData 已加密的訊息。
     * @return 返回解密後的字節數據。
     * @throws Exception 若解密過程中發生錯誤時拋出。
     */
    public static byte[] decryptWithPrivateKey(PrivateKey privateKey, byte[] encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        
        // 除以8是為了將位元數(bit count)轉換成字節數(byte count)。
        int chunkLength = (privateKey instanceof RSAPrivateKey) ? 
            ((RSAPrivateKey) privateKey).getModulus().bitLength() / 8 : CHUNK_SIZE;

        byte[] decryptedData = new byte[0];
        for (int i = 0; i < encryptedData.length; i += chunkLength) {
            byte[] encryptedChunk = new byte[chunkLength];
            System.arraycopy(encryptedData, i, encryptedChunk, 0, chunkLength);
            byte[] decryptedChunk = cipher.doFinal(encryptedChunk);
            decryptedData = concat(decryptedData, decryptedChunk);
        }
        return decryptedData;
    }

    /**
     * 合併兩個字節數組。
     * 
     * @param a 第一個字節數組。
     * @param b 第二個字節數組。
     * @return 返回合併後的字節數組。
     */
    private static byte[] concat(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
    
    /**
     * 使用AES密鑰和初始化向量 (IV) 進行加密。
     * 
     * @param key  用於加密的AES密鑰。
     * @param data 需要加密的訊息。
     * @param iv   用於CBC模式的初始化向量。
     * @return 返回加密後的字節數據。
     * @throws Exception 若在加密過程中出現問題時拋出。
     */
    public static byte[] encryptWithAESKeyAndIV(SecretKeySpec key, String data, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        return cipher.doFinal(data.getBytes());
    }

    /**
     * 使用AES密鑰和初始化向量 (IV) 進行解密。
     * 
     * @param key           用於解密的AES密鑰。
     * @param encryptedData 需要解密的字節數據。
     * @param iv            用於CBC模式的初始化向量。
     * @return 返回解密後的訊息。
     * @throws Exception 若在解密過程中出現問題時拋出。
     */
    public static String decryptWithAESKeyAndIV(SecretKeySpec key, byte[] encryptedData, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        byte[] decryptedBytes = cipher.doFinal(encryptedData);
        return new String(decryptedBytes);
    }

    /**
     * 使用AES密鑰和初始化向量 (IV) 在CTR模式下進行加密。
     * 
     * @param key  用於加密的AES密鑰。
     * @param data 需要加密的訊息。
     * @param iv   用於CTR模式的初始化向量。
     * @return 返回加密後的字節數據。
     * @throws Exception 若在加密過程中出現問題時拋出。
     */
    public static byte[] encryptWithAESKeyAndIVInCTRMode(SecretKeySpec key, String data, byte[] iv) throws Exception {
    	// 當使用AES的CTR模式時，通常使用"NoPadding"作為填充方案，而不是"PKCS5Padding"。
    	// 這是因為CTR模式將密鑰轉化為一個字節流，該字節流與原始數據進行XOR操作，因此不需要任何填充。
    	Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        return cipher.doFinal(data.getBytes());
    }

    /**
     * 使用AES密鑰和初始化向量 (IV) 在CTR模式下進行解密。
     * 
     * @param key           用於解密的AES密鑰。
     * @param encryptedData 需要解密的字節數據。
     * @param iv            用於CTR模式的初始化向量。
     * @return 返回解密後的訊息。
     * @throws Exception 若在解密過程中出現問題時拋出。
     */
    public static String decryptWithAESKeyAndIVInCTRMode(SecretKeySpec key, byte[] encryptedData, byte[] iv) throws Exception {
    	// 當使用AES的CTR模式時，通常使用"NoPadding"作為填充方案，而不是"PKCS5Padding"。
    	// 這是因為CTR模式將密鑰轉化為一個字節流，該字節流與原始數據進行XOR操作，因此不需要任何填充。
    	Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        byte[] decryptedBytes = cipher.doFinal(encryptedData);
        return new String(decryptedBytes);
    }
    
    /**
     * 可將字節陣列轉換為十六進制格式的字符串。
     * 這通常用於方便地顯示二進制數據，如數字簽名、摘要或加密的數據。
     * 
     * @param bytes 要轉換的字節陣列
     * @return 表示給定字節的十六進制格式的字符串
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    
    /**
     * 要從十六進制格式的雜湊字串轉回 byte[]
     * 
     * @return 返回 byte[]。
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
    
    /**
     * 將給定的 long 值轉換成 byte 陣列。
     * 
     * 此方法會將一個 64 位元的 long 值轉換成一個 8 位元組的 byte 陣列，其中每個 byte 代表 long 的一個字節。
     * 轉換是從最低有效位元組開始的，即 result[7] 是 l 的最低有效位元組，result[0] 是最高有效位元組。
     * 
     * @param l 需要轉換的 long 值。
     * @return 表示給定 long 值的 byte 陣列。
     */
    public static byte[] longToBytes(long l) {
        byte[] result = new byte[8];
        for (int i = 7; i >= 0; i--) {
            result[i] = (byte) (l & 0xFF);
            l >>= 8;
        }
        return result;
    }

    
    /**
     * 根據給定的文件路徑生成 SHA-256 雜湊值。
     * 
     * @param filepath 文件的路徑。
     * @return 返回文件內容的 SHA-256 雜湊值，以十六進制字符串格式表示。
     * 如果在讀取文件或生成雜湊時出現錯誤，則返回 null。
     */
    public static String generateFileHash(String filepath) {
        try (FileInputStream fis = new FileInputStream(filepath)) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 創建一個大小為 1024 字節的緩衝區用於讀取文件
            byte[] buffer = new byte[1024];
            int bytesRead = -1;

            // 讀取文件，並更新雜湊
            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }

            // 計算並返回雜湊值
            return bytesToHex(digest.digest());
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    /**
     * 使用給定的算法和金鑰生成訊息認證碼 (MAC)。
     * 
     * @param algorithm 使用的 MAC 算法 (例如: "HmacSHA256")
     * @param key 使用的密鑰來生成 MAC
     * @param message 要生成 MAC 的原始訊息
     * @return 訊息的 MAC 值
     * @throws Exception 當有錯誤發生 (例如: 不支援的算法)
     */
    public static byte[] generateMac(String algorithm, SecretKey key, byte[] message) throws Exception {
        Mac mac = Mac.getInstance(algorithm);
        mac.init(key);
        return mac.doFinal(message);
    }
    
    public static byte[] generateMac(SecretKey key, String message) throws Exception {
        return generateMac("HmacSHA256", key, message.getBytes());
    }
    
    /**
     * 用指定的演算法和鑰匙生成訊息驗證碼 (MAC)。
     *
     * @param algorithm 用於 MAC 的演算法，例如 "HmacSHA256"
     * @param key 用於生成 MAC 的秘密鑰匙
     * @param filepath 要生成 MAC 的文件的路徑
     * @return 返回文件的 MAC 值，格式為十六進制字符串
     * @throws Exception 如果在讀取文件或生成 MAC 時出現任何錯誤
     */
    public static String generateMac(String algorithm, SecretKey key, String filepath) throws Exception {
        Mac mac = Mac.getInstance(algorithm);
        mac.init(key);

        byte[] fileBytes = Files.readAllBytes(Paths.get(filepath));
        byte[] macBytes = mac.doFinal(fileBytes);

        return bytesToHex(macBytes);
    }
    
    // 保存密鑰
    public static void saveSecretKeyToFile(SecretKey key, String path) throws IOException {
        byte[] keyBytes = key.getEncoded();
        Files.write(Paths.get(path), keyBytes);
    }
    
    // 取得密鑰
    public static SecretKey getSecretKeyFromFile(String algorithm, String path) throws IOException {
        byte[] keyBytes = Files.readAllBytes(Paths.get(path));
        return new SecretKeySpec(keyBytes, algorithm);
    }

    
    /**
     * 生成一個適用於 HmacSHA256 的密鑰。
     * 
     * Hmac - "Hash-based Message Authentication Code" 的縮寫。它是一種特定的方式，
     * 用於檢查訊息的完整性並提供身份驗證，特別是在密碼學中。
     * Hmac 需要一個加密哈希函數和一個密鑰作為其兩個主要參數。
     * 
     * SHA256 - 這是一個加密哈希函數，
     * 屬於 SHA-2（Secure Hash Algorithm 2）家族。256 表示輸出的摘要/哈希長度是 256 位。
     * 
     * 除了 HmacSHA256 外，還有其他的 Hmac 設定，這些設定主要根據所使用的加密哈希函數而有所不同。以下是一些常見的選項：
     * HmacSHA1 - 使用 SHA-1 加密哈希函數。
     * HmacSHA384 - 使用 SHA-2 家族中的 SHA-384 函數。
     * HmacSHA512 - 使用 SHA-2 家族中的 SHA-512 函數。
     * 
     * @return 用於 HmacSHA256 的 SecretKey
     * @throws Exception 當生成密鑰時發生錯誤 (例如: 不支援的算法)
     */
    public static SecretKey generateKeyForHmac() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        return keyGenerator.generateKey();
    }
    
    // 數位簽章
    
    /**
     * 使用給定的私鑰簽署指定文件。
     * 
     * @param privateKey 私鑰，用於生成數位簽章。
     * @param filePath   要簽署的文件的路徑。
     * @return 返回生成的數位簽章。
     * 
     * 使用情境：當需要對一份文件（如合約或其他重要文檔）進行簽署以確認其真實性和完整性時，可以使用此方法。
     */
    public static byte[] signWithPrivateKey(PrivateKey privateKey, String filePath) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);

        byte[] data = Files.readAllBytes(Paths.get(filePath));
        signature.update(data);
        
        return signature.sign();
    }
    
    /**
     * 將指定的密鑰保存到文件。
     * 
     * @param key      要保存的密鑰。
     * @param filePath 指定要保存到的文件的路徑。
     * 
     * 使用情境：當生成一個新的公鑰/私鑰對或收到一個新的密鑰時，並且希望將其安全地存儲到文件系統中以便日後使用。
     */
    public static void saveKeyToFile(Key key, String filePath) throws IOException {
        byte[] keyBytes = key.getEncoded();
        Files.write(Paths.get(filePath), keyBytes);
    }
    
    /**
     * 從指定的文件中讀取私鑰。
     * 
     * @param algorithm 使用的算法，例如 "RSA"。
     * @param filePath  包含私鑰的文件的路徑。
     * @return 返回從文件中讀取的私鑰。
     * 
     * 使用情境：當需要對文件進行簽署或解密使用私鑰進行加密的資料時。
     */
    public static PrivateKey getPrivateKeyFromFile(String algorithm, String filePath) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(filePath));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePrivate(spec);
    }
    
    /**
     * 從指定的文件中讀取公鑰。
     * 
     * @param algorithm 使用的算法，例如 "RSA"。
     * @param filePath  包含公鑰的文件的路徑。
     * @return 返回從文件中讀取的公鑰。
     * 
     * 使用情境：當需要驗證由相應私鑰簽署的數位簽章或解密使用公鑰進行加密的資料時。
     */
    public static PublicKey getPublicKeyFromFile(String algorithm, String filePath) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(filePath));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePublic(spec);
    }
    
    /**
     * 將生成的數位簽章保存到文件。
     * 
     * @param signature 生成的數位簽章。
     * @param filePath  指定要保存到的文件的路徑。
     * 
     * 使用情境：在對文件進行簽署後，為了與原始文件一起發送或存儲，您可能希望將簽章保存到一個單獨的文件中。
     */
    public static void saveSignatureToFile(byte[] signature, String filePath) throws IOException {
        Files.write(Paths.get(filePath), signature);
    }
    
    /**
     * 從指定的文件中讀取數位簽章。
     * 
     * @param filePath 包含數位簽章的文件的路徑。
     * @return 返回從文件中讀取的數位簽章。
     * 
     * 使用情境：當收到一份簽署的文件和其相應的數位簽章文件，並希望驗證該簽章時。
     */
    public static byte[] getSignatureFromFile(String filePath) throws IOException {
        return Files.readAllBytes(Paths.get(filePath));
    }
    
    /**
     * 使用給定的字節陣列重建公鑰。
     * 
     * @param algorithm  使用的公鑰演算法，通常是"RSA"
     * @param keyBytes   公鑰的字節陣列
     * @return PublicKey 從給定字節陣列中重建的公鑰
     * @throws Exception 如果公鑰重建失敗
     * 
     * 情境使用：
     * 當我們需要從Base64或其他格式中取得公鑰時，
     * 我們首先需要將其轉換為字節陣列，然後使用這個方法來獲得PublicKey實例。
     */
    public static PublicKey getPublicKeyFromBytes(String algorithm, byte[] keyBytes) throws Exception {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePublic(spec);
    }
    
    /**
     * 生成基於時間的一次性密碼 (TOTP)。
     *
     * @param secret       Base64 編碼的秘密金鑰。
     * @param timeInterval 當前的時間間隔，用於計算 TOTP。
     * @param crypto       指定的加密算法，例如 "HMAC-SHA256"。
     * @return             返回計算出的 6 位 TOTP。
     * @throws NoSuchAlgorithmException   若指定的加密算法不可用或不存在，則拋出此異常。
     * @throws InvalidKeyException       若初始化 Mac 物件時使用的密鑰是無效的，則拋出此異常。
     */
    public static String generateTOTP(String secret, long timeInterval, String crypto) 
                throws NoSuchAlgorithmException, InvalidKeyException {
            
        // 將 Base64 編碼的秘密金鑰解碼
        byte[] decodedKey = Base64.getDecoder().decode(secret);
            
        // 創建一個加密演算法實例，例如：HMAC-SHA256 
        Mac mac = Mac.getInstance(crypto);
            
        // 用解碼後的鑰匙和原始(RAW)加密演算法初始化 Mac
        SecretKeySpec spec = new SecretKeySpec(decodedKey, "HmacSHA256");
        mac.init(spec);
            
        // 根據當前時間和給定的時間間隔計算 TOTP
        byte[] hmac = mac.doFinal(KeyUtil.longToBytes(timeInterval));
        int offset = hmac[hmac.length - 1] & 0xF;
        long otp = (hmac[offset] & 0x7F) << 24 |
                   (hmac[offset + 1] & 0xFF) << 16 |
                   (hmac[offset + 2] & 0xFF) << 8 |
                   (hmac[offset + 3] & 0xFF);
            
        // 將其縮小為 6 位數字
        otp = otp % 1000000;

        return String.format("%06d", otp);
    }
    
    // JWT
    /**
     * 生成指定長度的隨機密鑰，並將其 Base64 編碼。
     *
     * @param byteLength 欲生成的密鑰的字節長度。例如，若要生成 256 位的密鑰，字節長度應為 32。
     * @return 返回 Base64 編碼的密鑰。
     *
     * 使用方法:
     * - SecureRandom 用於生成加密安全的隨機數。
     * - 這個方法首先使用 SecureRandom 生成指定長度的隨機密鑰。
     * - 然後將生成的密鑰進行 Base64 編碼，這樣可以將它安全地存儲或轉輸，同時避免任何不打印或特殊的字符。
     */
    public static String generateSecret(int byteLength) {
        SecureRandom secureRandom = new SecureRandom();   // 創建一個加密安全的隨機數生成器
        byte[] key = new byte[byteLength];                // 分配用於保存密鑰的空間
        secureRandom.nextBytes(key);                      // 生成隨機密鑰
        return Base64.getEncoder().encodeToString(key);   // 將密鑰 Base64 編碼並返回
    }
    
    /**
     * 創建 JWT 聲明集。
     *
     * @param subject  主題。
     * @param issuer   發行者。
     * @param name     名稱。
     * @return JWTClaimsSet 物件。
     */
    public static JWTClaimsSet createJWTClaims(String subject, String issuer, String name) {
        return new JWTClaimsSet.Builder()
            .subject(subject)
            .issuer(issuer)
            .claim("name", name)
            .build();
    }

    /**
     * 簽名 JWT。
     *
     * @param claimsSet   JWT 聲明集。
     * @param secret      簽名密鑰。
     * @return 簽名的 JWT 字串。
     * @throws JOSEException 如果簽名過程中發生錯誤。
     */
    public static String signJWT(JWTClaimsSet claimsSet, String secret) throws JOSEException {
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        JWSSigner signer = new MACSigner(secret);
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }

    /**
     * 驗證 JWT 的簽名。
     *
     * @param token  JWT 字串。
     * @param secret 驗證密鑰。
     * @return 若簽名有效返回 true，否則返回 false。
     * @throws ParseException 如果解析 JWT 字串時出錯。
     * @throws JOSEException  如果驗證過程中發生錯誤。
     */
    public static boolean verifyJWTSignature(String token, String secret) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);

            // 檢查簽名
            JWSVerifier verifier = new MACVerifier(secret);
            if (!signedJWT.verify(verifier)) {
                return false;
            }

            // 檢查過期時間
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            if (expirationTime != null && new Date().after(expirationTime)) {
                return false; // Token 已經過期
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 從 JWT 字串中獲取聲明集。
     *
     * @param token JWT 字串。
     * @return JWTClaimsSet 物件。
     * @throws ParseException 如果解析 JWT 字串時出錯。
     */
    public static JWTClaimsSet getClaimsFromToken(String token) throws ParseException {
        return SignedJWT.parse(token).getJWTClaimsSet();
    }
    
    /**
     * 使用指定的密鑰和算法對 JWT 進行簽名。
     *
     * @param claimsSet 要簽名的聲明集合
     * @param signingSecret 簽名用的密鑰
     * @return 已簽名的 JWT
     */
    public static SignedJWT signWithSecret(JWTClaimsSet claimsSet, String signingSecret) throws JOSEException {
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        JWSSigner signer = new MACSigner(signingSecret);
        signedJWT.sign(signer);
        return signedJWT;
    }

    /**
     * 使用指定的密鑰對 JWT 進行加密。
     *
     * @param signedJWT 已簽名的 JWT
     * @param encryptionSecret 加密用的密鑰
     * @return 加密的 JWT 字符串
     */
    public static String encryptJWT(SignedJWT signedJWT, String encryptionSecret) throws JOSEException {
        JWEObject jweObject = new JWEObject(
                new JWEHeader.Builder(JWEAlgorithm.DIR, EncryptionMethod.A192GCM)
                        .contentType("JWT")
                        .build(),
                new Payload(signedJWT)
        );
        jweObject.encrypt(new DirectEncrypter(encryptionSecret.getBytes()));
        return jweObject.serialize();
    }

    /**
     * 使用指定的密鑰對加密的 JWT 進行解密。
     *
     * @param encryptedJWT 加密的 JWT 字符串
     * @param decryptionSecret 解密用的密鑰
     * @return 解密後的 SignedJWT 物件
     */
    public static SignedJWT decryptJWT(String encryptedJWT, String decryptionSecret) throws ParseException, JOSEException {
        JWEObject jweObject = JWEObject.parse(encryptedJWT);
        jweObject.decrypt(new DirectDecrypter(decryptionSecret.getBytes()));
        return jweObject.getPayload().toSignedJWT();
    }
    

}

