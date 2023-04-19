package cn.iosd.starter.encode.rsa.utils;

import cn.iosd.starter.encode.rsa.properties.RsaProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
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

    private static final int KEY_SIZE = 2048;

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
        byte[] dataBytes = data.getBytes();
        //每一段的长度
        int blockSize = KEY_SIZE / 8 - 11;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (int i = 0; i < dataBytes.length; i += blockSize) {
            int length = Math.min(blockSize, dataBytes.length - i);
            byte[] blockBytes = new byte[length];
            System.arraycopy(dataBytes, i, blockBytes, 0, length);
            byte[] encryptedBytes = cipher.doFinal(blockBytes);
            outputStream.write(encryptedBytes);
        }
        byte[] encryptedBytes = outputStream.toByteArray();
        return Base64.encodeBase64String(encryptedBytes);
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
        byte[] dataBytes = Base64.decodeBase64(data);
        int inputLength = dataBytes.length;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLength - offset > 0) {
            if (inputLength - offset > KEY_SIZE / 8) {
                cache = cipher.doFinal(dataBytes, offset, KEY_SIZE / 8);
            } else {
                cache = cipher.doFinal(dataBytes, offset, inputLength - offset);
            }
            outputStream.write(cache, 0, cache.length);
            i++;
            offset = i * KEY_SIZE / 8;
        }
        byte[] decryptedBytes = outputStream.toByteArray();
        outputStream.close();
        return new String(decryptedBytes, StandardCharsets.UTF_8);
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

    /**
     * 时间戳校验方法
     *
     * @param timestampValidation 校验配置参数
     * @param contentsDecrypt     加密文本
     * @throws JsonProcessingException 解析异常抛出
     */
    public static void timestampValidation(RsaProperties.TimestampValidation timestampValidation, String contentsDecrypt) throws JsonProcessingException {
        if (timestampValidation.getEnabled()) {
            JsonNode contentsDecryptedJsonNode = JsonMapper.getObjectMapper().readTree(contentsDecrypt);
            JsonNode timestampJsonNode = contentsDecryptedJsonNode.get(RsaProperties.TimestampValidation.TIMESTAMP);
            if (timestampJsonNode == null) {
                throw new RuntimeException("The request timestamp is missing");
            }
            long timestamp = timestampJsonNode.asLong();
            long requestExpirationTime = timestamp + timestampValidation.getExpiryMillis();
            if (System.currentTimeMillis() > requestExpirationTime) {
                throw new RuntimeException("The request timestamp has expired");
            }
        }
    }

}
