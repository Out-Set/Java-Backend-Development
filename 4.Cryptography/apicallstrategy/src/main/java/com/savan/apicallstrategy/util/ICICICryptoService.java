package com.savan.apicallstrategy.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class ICICICryptoService {

    @Autowired
    private ICICICryptoUtils iciciCryptoUtils;

    @Autowired
    private ObjectMapper objectMapper;

    public String encryptPayload(String payload, String publicCertPath) {
        Map<String, Object> finalPayload = new HashMap<>();
        try {
            // Load PublicKey
            PublicKey publicKey = iciciCryptoUtils.loadPublicKey(publicCertPath);

            // 1. Generate 16 digit random number for key
            byte[] aesKey = iciciCryptoUtils.generateRandomBytes(16);

            // 2. Encrypt RANDOM-NO (key) using RSA/ECB/PKCS1Padding and encode using Base64
            String encryptedAesKey = iciciCryptoUtils.rsaEncryptKey(aesKey, publicKey);

            // 3. Generate 16 digit random number for iv
            byte[] iv = iciciCryptoUtils.generateRandomBytes(16);

            // 4. Perform AES/CBC/PKCS5Padding encryption on request payload using key and iv
            String encryptedBase64Payload = iciciCryptoUtils.aesEncryptPayload(payload, aesKey, iv);

            // 5. Base64 Encoded IV
            String base64EncodedIv = Base64.getEncoder().encodeToString(iv);

            // Prepare final payload
            String requestId = "REQ123456";
            finalPayload.put("requestId", requestId);
            finalPayload.put("service", "");
            finalPayload.put("encryptedKey", encryptedAesKey);
            finalPayload.put("oaepHashingAlgorithm", "NONE");
            finalPayload.put("iv", base64EncodedIv);
            finalPayload.put("encryptedData", encryptedBase64Payload);
            finalPayload.put("clientInfo", "");
            finalPayload.put("optionalParam", "");

            return objectMapper.writeValueAsString(finalPayload);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }


    public Object decryptResponse(String response, String privateKeyPath) {
        try {
            // Convert response into map so that we can read
            Map<String, String> responseMap = objectMapper.readValue(response, new TypeReference<>() {});

            // Load PrivateKey
            PrivateKey privateKey = iciciCryptoUtils.loadPrivateKey(privateKeyPath);

            // 1. Get the IV- Base64 decode the encryptedData and get first 16 bytes and rest is encrypted response.
            byte[] encryptedData = Base64.getDecoder().decode(responseMap.get("encryptedData"));

            byte[] iv = new byte[16];
            System.arraycopy(encryptedData, 0, iv, 0, 16);

            byte[] ciphertext = new byte[encryptedData.length - 16];
            System.arraycopy(encryptedData, 16, ciphertext, 0, ciphertext.length);

            // 2. Decrypt encryptedKey using algo (RSA/ECB/PKCS1Padding) and Client’s private key
            byte[] decryptedAesKey = iciciCryptoUtils.rsaDecryptKey(responseMap.get("encryptedKey"), privateKey);

            // 3. Decrypt the response using algo (AES/CBC/PKCS5Padding)
            return iciciCryptoUtils.aesDecryptPayload(decryptedAesKey, iv, ciphertext);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
