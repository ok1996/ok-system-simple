package cn.iosd.starter.encode.rsa.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author ok1996
 */
public class RsaUtils {

    private static final String RSA = "RSA";

    private static final int KEY_SIZE = 4096;

    /**
     * 生成 RSA 密钥对
     *
     * @return RSA 密钥对
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
        SecureRandom random = new SecureRandom();
        keyPairGenerator.initialize(KEY_SIZE, random);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 使用公钥加密数据
     *
     * @param data      待加密数据
     * @param publicKey 公钥字符串
     * @return 加密后的数据字符串
     * @throws Exception
     */
    public static String encrypt(String data, String publicKey) throws Exception {
        PublicKey key = getPublicKey(publicKey);
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] bytes = cipher.doFinal(data.getBytes());
        return Base64.encodeBase64String(bytes);
    }

    /**
     * 使用私钥解密数据
     *
     * @param data       待解密数据字符串
     * @param privateKey 私钥字符串
     * @return 解密后的数据
     * @throws Exception
     */
    public static String decrypt(String data, String privateKey) throws Exception {
        PrivateKey key = getPrivateKey(privateKey);
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] bytes = Base64.decodeBase64(data);
        return new String(cipher.doFinal(bytes));
    }

    /**
     * 从字符串中获取公钥
     *
     * @param publicKeyStr 公钥字符串
     * @return 公钥
     * @throws InvalidKeySpecException
     */
    private static PublicKey getPublicKey(String publicKeyStr) throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] publicKeyBytes = Base64.decodeBase64(publicKeyStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        return KeyFactory.getInstance(RSA).generatePublic(keySpec);
    }

    /**
     * 从字符串中获取私钥
     *
     * @param privateKeyStr 私钥字符串
     * @return 私钥
     * @throws InvalidKeySpecException
     */
    private static PrivateKey getPrivateKey(String privateKeyStr) throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] privateKeyBytes = Base64.decodeBase64(privateKeyStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        return KeyFactory.getInstance(RSA).generatePrivate(keySpec);
    }
}
