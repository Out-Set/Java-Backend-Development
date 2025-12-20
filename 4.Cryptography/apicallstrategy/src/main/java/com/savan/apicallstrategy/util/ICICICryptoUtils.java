package com.savan.apicallstrategy.util;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Component
public class ICICICryptoUtils {

    public static final String ICICI_SYMMETRIC_KEY_ENCRYPT_ALGO = "AES/CBC/PKCS5Padding";
    public static final String ICICI_ASYMMETRIC_KEY_ENCRYPT_ALGO = "RSA/ECB/PKCS1Padding";

    // --------------------------------- Encryption Utils --------------------------------- //
    public PublicKey loadPublicKey(String publicCertPath) throws Exception {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        try (InputStream in = Files.newInputStream(Paths.get(publicCertPath))) {
            Certificate cert = cf.generateCertificate(in);
            return cert.getPublicKey();
        }
    }

    public byte[] generateRandomBytes(int length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return bytes;
    }

    public String rsaEncryptKey(byte[] aesKey, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ICICI_ASYMMETRIC_KEY_ENCRYPT_ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedKey = cipher.doFinal(aesKey);
        return Base64.getEncoder().encodeToString(encryptedKey);
    }

    public String aesEncryptPayload(String payload, byte[] aesKey, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(ICICI_SYMMETRIC_KEY_ENCRYPT_ALGO);
        SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encrypted = cipher.doFinal(payload.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }


    // --------------------------------- Decryption Utils --------------------------------- //
    public PrivateKey loadPrivateKey(String privateKeyPath) throws Exception {
        String key = Files.readString(Paths.get(privateKeyPath));
        key = key.replaceAll("-----BEGIN (.*)-----", "")
                .replaceAll("-----END (.*)----", "")
                .replaceAll("\r\n", "")
                .replaceAll("\n", "")
                .trim();

        byte[] keyBytes = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public byte[] rsaDecryptKey(String encryptedKeyBase64, PrivateKey privateKey) throws Exception {
        byte[] encryptedKey = Base64.getDecoder().decode(encryptedKeyBase64);
        Cipher cipher = Cipher.getInstance(ICICI_ASYMMETRIC_KEY_ENCRYPT_ALGO);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(encryptedKey);
    }

    public String aesDecryptPayload(byte[] aesKey, byte[] iv, byte[] ciphertext) throws Exception {
        Cipher cipher = Cipher.getInstance(ICICI_SYMMETRIC_KEY_ENCRYPT_ALGO);
        SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] decrypted = cipher.doFinal(ciphertext);
        return new String(decrypted, StandardCharsets.UTF_8);
    }
}
